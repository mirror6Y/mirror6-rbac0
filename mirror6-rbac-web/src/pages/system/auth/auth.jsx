import React, {Component} from 'react';
import {Card, Button, Table, Modal, notification, Space, Switch, Form, Row, Col, Select, Input, DatePicker} from 'antd'
import {QuestionCircleOutlined, ExclamationCircleOutlined} from '@ant-design/icons';

import {formatDate, parseDate} from '../../../utils/dateUtil'
// import LinkButton from '../../../components/button'
import {reqAuthList, reqAuthAdd, reqAuthDelete, reqAuthEdit} from '../../../api/api.js'
import AuthModal from './authModal'

const FormItem = Form.Item;
const Option = Select.Option;
const {RangePicker} = DatePicker;

class Auth extends Component {

    formRef = React.createRef();

    constructor(props) {
        super(props)
        this.state = {
            list: [],
            selectedRowKeys: [],
            pagination: {
                total: 0,
                current: 1,
                pageSize: 20,
                pageSizeOptions: [20, 30, 50, 100],
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
                title: '权限名称',
                dataIndex: 'name',
            },
            {
                title: '权限标识',
                dataIndex: 'mark',
            },
            {
                title: '类型',
                dataIndex: 'type',
            },
            {
                title: '描述信息',
                dataIndex: 'description',
            },
            // {
            //     title: 'url',
            //     dataIndex: 'url',
            // },
            // {
            //     title: '图标',
            //     dataIndex: 'iocnPath'
            // },
            {
                title: '启用状态',
                dataIndex: 'enabled',
                render: (text, record, index) => (
                    <Switch checkedChildren="开启" unCheckedChildren="关闭" checked={text === false ? 0 : 1} defaultChecked
                            onChange={() => this.onChange(text, record)}/>
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
                            <Button type='danger' onClick={() => this.deleteAuth(obj.id)}>删除</Button>
                        </Space>
                    </span>
                )
            }
        ]
    }

    //列表函数
    getAuthList = async (current = 1, pageSize = 10) => {
        const {pagination} = this.state;

        const data = this.formRef.current.getFieldsValue();
        data.pageNum = current;
        data.pageSize = pageSize;

        const result = await reqAuthList(data);
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
        const result = await reqAuthList(data);
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({list: data})
        }
    }


    //table分页
    onTableChange = async (page) => {
        await this.setState({
            pagination: page
        })
        this.getAuthList(page.current, page.pageSize);
    }

    //修改启用状态
    onChange = async (text, record) => {

        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <ExclamationCircleOutlined/>,
            content: '您确定修改启用状态吗？',
            onOk: async () => {
                record.enabled = !record.enabled

                const result = await reqAuthEdit(record);
                if (result.code === 200) {

                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getAuthList();

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

        this.editData = data;
    }

    //添加验证
    handleOk = (params) => {
        var status = this.state.modalStatus;
        console.log(params, status)
        if ("add" === status) {
            this.addAuth(params);
        } else {
            this.editAuth(params);
        }
    }

    //添加方法
    addAuth = async (data) => {
        const result = await reqAuthAdd(data);
        if (result.code === 200) {
            this.setState({
                modalIsVisible: false
            })
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getAuthList();
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
                this.editAuth(values);
                this.setState({modalIsVisible: false});
            })
            .catch(errorInfo => {
                console.log(errorInfo)
            });
    }

    //编辑信息
    editAuth = async (data) => {
        const result = await reqAuthEdit(data);
        if (result.code === 200) {
            this.setState({
                modalIsVisible: false
            })
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getAuthList();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }

    //删除信息
    deleteAuth = (index) => {
        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <QuestionCircleOutlined/>,
            content: '您确定删除此条内容吗？',
            onOk: async () => {
                var ids = new Array();
                ids[0] = index;

                const result = await reqAuthDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getAuthList();

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
            icon: <QuestionCircleOutlined/>,
            content: `您确定删除${this.state.selectedRowKeys.length}项吗？`,
            onOk: async () => {

                const ids = this.state.selectedRowKeys;
                console.log("123" + ids)
                const result = await reqAuthDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.setState({
                        selectedRowKeys: []
                    });
                    this.getAuthList();

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
        this.getAuthList();
    }

    resetSearch = async () => {
        this.formRef.current.resetFields()
        const result = await reqAuthList({pageNum: 1, pageSize: 10});
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({list: data})
        }
    }

    render() {

        const editData = this.editData;
        const {list, selectedRowKeys, pagination, modalIsVisible, modalStatus} = this.state;

        const rowSelection = {
            selectedRowKeys: selectedRowKeys,
            onChange: (selectedRowKeys) => this.setState({selectedRowKeys})
        }

        return (
            <Card>
                <Form
                    ref={this.formRef}
                    style={{marginBottom: 16}}
                >
                    <Row gutter={24}>
                        <Col span={5}>
                            <FormItem name="name">
                                <Input placeholder="权限名称" allowClear/>
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="mark">
                                <Input placeholder="权限标识" allowClear/>
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="enabled">
                                <Select placeholder="启用状态" allowClear>
                                    <Option value="1">开启</Option>
                                    <Option value="0">关闭</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span={6}>
                            <FormItem name="gmtCreate" label="创建时间">
                                <RangePicker/>
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
                <span>
                    <Space>
                        <Button type="primary" onClick={this.showAdd}>添加权限</Button>
                        <Button type="danger" disabled={!selectedRowKeys.length}
                                onClick={this.deleteBatch}>批量删除</Button>
                    </Space>
                </span>
                <Table
                    rowSelection={rowSelection}
                    columns={this.columns}
                    rowKey="id"
                    dataSource={list}
                    pagination={pagination}
                    onChange={this.onTableChange}
                    style={{marginTop: 10}}/>
                {
                    modalIsVisible &&
                    <AuthModal visible={modalIsVisible} modalStatus={modalStatus} currentItem={editData}
                               onCancel={this.handleCancel} handleOk={this.handleOk}/>
                }

            </Card>
        );
    }
}

export default Auth;