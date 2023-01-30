import React, {Component} from 'react';
import {Card, Button, Table, Modal, notification, Space, Switch, Form, Row, Col, Select, Input, DatePicker} from 'antd'
import {QuestionCircleOutlined, ExclamationCircleOutlined} from '@ant-design/icons';

import {formatDate, parseDate} from '../../../utils/dateUtil'
// import LinkButton from '../../../components/button'
import {reqUserList, reqUserAdd, reqUserDelete, reqUserEdit, reqUserGet} from '../../../api/api.js'
import UserModal from './userModal'


const FormItem = Form.Item;
const Option = Select.Option;
const {RangePicker} = DatePicker;

class User extends Component {

    formRef = React.createRef();

    constructor() {
        super()
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
                title: '账号',
                dataIndex: 'account',
            },
            {
                title: '姓名',
                dataIndex: 'name',
            },
            // {
            //     title: '部门',
            //     dataIndex: 'college',
            // },
            {
                title: '手机号码',
                dataIndex: 'tel'
            },
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
                            <Button type='danger' onClick={() => this.deleteUser(obj.id)}>删除</Button>
                        </Space>
                    </span>
                )
            }
        ]
    }

    //列表函数
    getUserList = async (current = 1, pageSize = 20) => {
        const {pagination} = this.state;

        const data = this.formRef.current.getFieldsValue();
        data.pageNum = current;
        data.pageSize = pageSize;

        const result = await reqUserList(data);
        if (result.code === 200) {
            // const data = result.data.records;
            const data = result.data;
            console.log(JSON.stringify(data))
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
            data.startTime = Date.parse((data.gmtCreate[0]._d))
            data.endTime = Date.parse((data.gmtCreate[1]._d))
            data.gmtCreate = null
        }
        const result = await reqUserList(data);
        if (result.code === 200) {
            // const data = result.data.records;
            const data = result.data;
            this.setState({list: data})
        }
    }


    //table分页
    onTableChange = async (page) => {
        await this.setState({
            pagination: page
        })
        this.getUserList(page.current, page.pageSize);
    }

    //修改用户状态
    onChange = async (text, record) => {

        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <ExclamationCircleOutlined/>,
            content: '您确定修改启用状态吗？',
            onOk: async () => {
                record.enabled = !record.enabled
                const result = await reqUserEdit(record);
                if (result.code === 200) {

                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getUserList();

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


    //确定回调
    handleOk = (params) => {
        var status = this.state.modalStatus;
        console.log(params, status)
        if ("add" === status) {
            this.addUser(params);
            // this.formRef.current.resetFields()
        } else {
            this.editUser(params);
        }
    }

    //添加方法
    addUser = async (data) => {
        const result = await reqUserAdd(data);
        if (result.code === 200) {
            this.setState({
                modalIsVisible: false
            })
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getUserList();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }


    //编辑信息
    editUser = async (data) => {
        const result = await reqUserEdit(data);
        if (result.code === 200) {
            this.setState({
                modalIsVisible: false
            })
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.getUserList();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }

    //删除信息
    deleteUser = (index) => {

        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <QuestionCircleOutlined/>,
            content: '您确定删除此条内容吗？',
            onOk: async () => {
                var ids = new Array();
                ids[0] = index;
                const result = await reqUserDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.getUserList();
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

                const result = await reqUserDelete(ids);
                if (result.code === 200) {
                    notification.success({
                        duration: 2,
                        message: '提示',
                        description: result.msg
                    });
                    this.setState({
                        selectedRowKeys: []
                    });
                    this.getUserList();

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
        this.getUserList();
    }

    resetSearch = async () => {
        this.formRef.current.resetFields()
        const result = await reqUserList({pageNum: 1, pageSize: 10});
        if (result.code === 200) {
            const data = result.data;
            // const data = result.data.records;
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
                                <Input placeholder="用户姓名" allowClear onChange={this.search}/>
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="tel">
                                <Input placeholder="手机号码" allowClear onChange={this.search}/>
                            </FormItem>
                        </Col>
                        <Col span={5}>
                            <FormItem name="enabled">
                                <Select placeholder="启用状态" allowClear onChange={this.search}>
                                    <Option value="1">开启</Option>
                                    <Option value="0">关闭</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span={6}>
                            <FormItem name="gmtCreate">
                                <RangePicker allowClear onChange={this.search}/>
                            </FormItem>
                        </Col>
                    </Row>
                </Form>
                <span>
                    <Space>
                        <Button type="primary" onClick={this.showAdd}>添加用户</Button>
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
                    <UserModal visible={modalIsVisible} modalStatus={modalStatus} currentItem={editData}
                               onCancel={this.handleCancel} handleOk={this.handleOk}/>
                }
            </Card>
        );
    }
}

export default User;