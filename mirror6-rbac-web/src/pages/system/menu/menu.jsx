import React, { Component } from 'react';
import { Card, Button, Table, Modal, notification, Space, Switch, Form, Row, Col, Select, Input, DatePicker } from 'antd'
import { QuestionCircleOutlined, ExclamationCircleOutlined } from '@ant-design/icons';

import { formatDate, parseDate } from '../../../utils/dateUtil'
// import LinkButton from '../../../components/button'
import { reqMenuList, reqMenuAdd, reqMenuDelete, reqMenuEdit } from '../../../api/api.js'
import MenuModal from './menuModal'

const FormItem = Form.Item;
const Option = Select.Option;
const { RangePicker } = DatePicker;
class Menu extends Component {

    // addChild = React.createRef();
    // editChild = React.createRef();
    formRef = React.createRef();

    constructor() {
        super()
        this.state = {
            list: [],
            selectedRowKeys: [],
            // 0 不显示添加和修改组件 1 显示添加 2显示修改
            pagination: {
                total: 0,
                current: 1,
                pageSize: 15,
                pageSizeOptions: [15, 30, 50, 100],
                showQuickJumper: true,
                showSizeChanger: true
            },
            modalStatus: 'add',
            modalIsVisible: false
        }
    }

    initColumn = () => {
        this.columns = [
            {
                title: '序号',
                render: (text, record, index) => {
                    return (
                        <span>{(this.state.pagination.current - 1) * this.state.pagination.pageSize + (index + 1)}</span>
                    )
                }
            },
            {
                title: '菜单名称',
                dataIndex: 'title',
            },
            {
                title: '描述信息',
                dataIndex: 'description',
            },
            {
                title: 'url',
                dataIndex: 'url',
            },
            {
                title: '图标',
                dataIndex: 'iocnPath'
            },
            {
                title: '是否启用',
                dataIndex: 'enabled',
                render: (text, record, index) => (
                    <Switch checkedChildren="开启" unCheckedChildren="关闭" checked={text === 0 ? 0 : 1} defaultChecked onChange={() => this.onChange(text, record)} />
                )
            },
            {
                title: '创建时间',
                dataIndex: 'gmtCreate',
                render: (value) => {
                    return formatDate(value);
                }
            },
            {
                title: '操作',
                render: (obj) => (
                    <span>
                        <Space>
                            <Button onClick={() => this.showEdit(obj)}>编辑</Button>
                            <Button type='danger' onClick={() => this.deleteMenu(obj.id)}>删除</Button>
                        </Space>
                    </span>
                )
            }
        ]
    }

    getMenuList = async (current = 1, pageSize = 10) => {
        const { pagination } = this.state;

        const data = this.formRef.current.getFieldsValue();
        data.pageNum = current;
        data.pageSize = pageSize;

        const result = await reqMenuList(data);
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({
                list: data,
                pagination: {
                    ...pagination,
                    total: result.data.total,
                    current: current,
                    pageSize: pageSize
                }
            })
        }
    }

    //列表搜索
    search = async () => {
        const data = this.formRef.current.getFieldsValue();
        if (data.gmtCreate) {
            data.gmtCreate = parseDate(data.gmtCreate[0]._d) + "&" + parseDate(data.gmtCreate[1]._d)
        }
        const result = await reqMenuList(data);
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({ list: data })
        }
    }


    //table分页
    onTableChange = async (page) => {
        await this.setState({
            pagination: page
        })
        this.getMenuList(page.current, page.pageSize);
    }

    //修改启用状态
    onChange = async (text, record) => {

        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <ExclamationCircleOutlined />,
            content: '您确定修改启用状态吗？',
            onOk: async () => {
                if (record.enabled === 0) {
                    record.enabled = 1
                } else {
                    record.enabled = 0
                }
                const result = await reqMenuEdit(record);
                if (result.code === 200) {

                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getMenuList();

                } else {
                    notification.error({
                        duration: null,
                        message: '提示',
                        description: result.msg
                    })
                }

            }
        })
    }

    handleCancel = () => {
        this.setState({
            modalIsVisible: false
        })
    }

    //显示添加组件
    showAdd = () => {
        console.log(this.state);
        // const current = this.addChild.current;
        // if (null != current && undefined != current) {
        //     current.addRef.current.resetFields();
        // }

        this.setState({
            modalStatus: 'add',
            modalIsVisible: true
        })
    }

    //显示编辑组件
    showEdit = async (data) => {

        this.setState({
            modalStatus: 'edit',
            modalIsVisible: true
        })
        // const result = await reqUserGet(index);
        // if (result.code === 200) {
        //     //保存对象
        this.editData = data;
        console.log(this.editData + "123")
        // this.setState({
        //     data
        // })
        // } else {
        //     notification.error({
        //         duration: null,
        //         message: '提示',
        //         description: result.msg
        //     })
        // }
    }

    //添加验证
    handleOk = (params) => {
        var status = this.state.modalStatus;
        console.log(params, status)
        if ("add" == status) {
            this.addMenu(params);
        } else {
            this.editMenu(params);
        }
    }

    //添加方法
    addMenu = async (data) => {
        const result = await reqMenuAdd(data);
        if (result.code === 200) {
            this.setState({
                modalIsVisible: false
            })
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getMenuList();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }

    //编辑验证
    handleEdit = () => {
        const ref = this.editChild.current.editRef.current;
        ref.validateFields()
            .then(values => {
                this.editMenu(values);
                this.setState({ modalIsVisible: false });
            })
            .catch(errorInfo => {
                console.log(errorInfo)
            });
    }

    //编辑信息
    editMenu = async (data) => {
        const result = await reqMenuEdit(data);
        if (result.code === 200) {
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getMenuList();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }

    //删除信息
    deleteMenu = (index) => {
        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <QuestionCircleOutlined />,
            content: '您确定删除此条内容吗？',
            onOk: async () => {
                var ids = new Array();
                ids[0] = index;

                const result = await reqMenuDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getMenuList();

                } else {
                    notification.error({
                        duration: null,
                        message: '提示',
                        description: result.msg
                    })
                }

            }
        })
    }

    deleteBatch = () => {
        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <QuestionCircleOutlined />,
            content: `您确定删除${this.state.selectedRowKeys.length}项吗？`,
            onOk: async () => {

                const ids = this.state.selectedRowKeys;
                console.log("123" + ids)
                const result = await reqMenuDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.setState({
                        selectedRowKeys: []
                    });
                    this.getMenuList();

                } else {
                    notification.error({
                        duration: null,
                        message: '提示',
                        description: result.msg
                    })
                }

            }
        })
    }

    componentWillMount() {
        this.initColumn();
    }

    componentDidMount() {
        this.getMenuList();
    }
    resetSearch = async () => {
        this.formRef.current.resetFields()
        const result = await reqMenuList({ pageNum: 1, pageSize: 10 });
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({ list: data })
        }
    }
    render() {

        const editData = this.editData;
        const { list, selectedRowKeys, pagination, modalIsVisible, modalStatus } = this.state;

        const rowSelection = {
            selectedRowKeys: selectedRowKeys,
            onChange: (selectedRowKeys) => this.setState({ selectedRowKeys })
        }

        return (
            <Card>
                <Form
                    ref={this.formRef}
                    style={{ marginBottom: 16 }}
                >
                    <Row gutter={24}>
                        <Col span={5}>
                            <FormItem name="title" label="标题">
                                <Input placeholder="请输入标题" />
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="description" label="描述信息" >
                                <Input placeholder="请输入描述信息" />
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="enabled" label="启用状态">
                                <Select placeholder="请选择">
                                    <Option value="1">开启</Option>
                                    <Option value="0">关闭</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span={6}>
                            <FormItem name="gmtCreate" label="创建时间" >
                                <RangePicker />
                            </FormItem>
                        </Col>
                        <Col span={24}>
                            <Space>
                                <Button type="primary" onClick={this.search}>搜索</Button>
                                <Button onClick={this.resetSearch}>重置 </Button>
                            </Space>
                        </Col>
                    </Row>
                </Form>
                <span >
                    <Space>
                        <Button type="primary" onClick={this.showAdd}>新增</Button>
                        <Button type="danger" disabled={!selectedRowKeys.length} onClick={this.deleteBatch}>批量删除</Button>
                    </Space>
                </span>
                <Table
                    rowSelection={rowSelection}
                    columns={this.columns}
                    rowKey="id"
                    dataSource={list}
                    pagination={pagination}
                    onChange={this.onTableChange}
                    style={{ marginTop: 10 }} />
                {
                    modalIsVisible && <MenuModal visible={modalIsVisible} modalStatus={modalStatus} currentItem={editData} onCancel={this.handleCancel} handleOk={this.handleOk} />
                }

            </Card >
        );
    }
}

export default Menu;