import React, { Component } from 'react';
import { Table, Button, Space, Modal, notification } from 'antd';
import { QuestionCircleOutlined } from '@ant-design/icons';
import axios from 'axios';

import UserDetail from './userDetail'
import UserAdd from './userAdd'
import { formatDate } from '../../../utils/dateUtil'



class User extends Component {

    state = {
        users: [],
        usersLoading: false,
        selectedRowKeys: [],
        //添加模态框
        isShowCreateModal: false,
        //详情模态框
        isShowInfoModal: false,
        userInfo: {},
        updateInfo: {}
    };

    componentDidMount() {
        this.getUserList();
    }

    getUserList = () => {
        console.log('getUserList');
        const url = "/system/user/getUserList";
        const _this = this;
        axios.get(url)
            .then(function (response) {
                _this.setState({
                    users: response.data.data.records,
                    usersLoading: true
                });
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    onDelete(index) {
        console.log("deleteUser:" + index)
        const url = "/system/user/deleteUser/" + index;
        const _this = this;

        Modal.confirm({
            title: '提示',
            okText: '确定',
            cancelText: '取消',
            icon: <QuestionCircleOutlined />,
            content: '您确定删除此条内容吗？',
            onOk() {
                axios.delete(url)
                    .then((response) => {
                        console.log(response)
                        if (response.status === 200) {
                            notification.success({
                                duration: 1,
                                message: '提示',
                                description: response.data.msg
                            })

                            _this.getUserList()
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        })
    }

    batchDeletion = () => {

        console.log("批量删除" + this.state.selectedRowKeys)
    }

    toggleShowCreateModal = (visible, record) => {
        // console.log("addOrUpdate" + record);
        this.setState({
            isShowCreateModal: visible,
            updateInfo: record
        })
    }


    showInfoModal = (record) => {
        console.log("info" + record);
        this.setState({
            isShowInfoModal: true,
            userInfo: record
        })
    }

    closeInfoModal = () => {
        this.setState({
            isShowCreateModal: false,
            isShowInfoModal: false,
            userInfo: {}
        })
    }


    onSelectChange = selectedRowKeys => {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.setState({ selectedRowKeys });
    };

    render() {

        const { userInfo, updateInfo, isShowInfoModal, selectedRowKeys, isShowCreateModal } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };

        const columns = [
            { title: '主键', dataIndex: 'id', key: 'id' },
            { title: '账号', dataIndex: 'account', key: 'account' },
            { title: '姓名', dataIndex: 'name', key: 'name' },
            // { title: '性别', dataIndex: 'gender', key: 'gender', render: (text) => text == '1' ? '男' : '女', width: '10%' },
            { title: '部门', dataIndex: 'college', key: 'college' },
            { title: '手机号码', dataIndex: 'tel', key: 'tel' },
            // { title: '电子邮箱', dataIndex: 'email', key: 'email' },
            { title: '是否启用', dataIndex: 'enabled', key: 'enabled' },
            {
                title: '创建时间', dataIndex: 'gmtCreate', key: 'gmtCreate', render: (value) => {
                    return formatDate(new Date(value));
                }
            },
            {
                title: '操作', dataIndex: '', key: 'operation', width: '32%', render: (record) => (
                    <div>
                        <Space>
                            <Button onClick={() => this.showInfoModal(record)}>详情</Button>
                            <Button onClick={() => this.toggleShowCreateModal(true, record)}>编辑</Button>
                            <Button type='danger' onClick={() => this.onDelete(record.id)}>删除</Button>
                        </Space>
                    </div>
                )
            },
        ];

        return (

            <div >
                <div style={{ marginBottom: 16 }}>

                    <Button type='primary' onClick={() => this.toggleShowCreateModal(true, "add")}>新增</Button>&emsp;
                    <Button type="danger" onClick={this.batchDeletion} disabled={!selectedRowKeys.length}  >批量删除</Button>
                </div>
                <Table
                    rowSelection={rowSelection}
                    columns={columns}
                    dataSource={this.state.users}
                />
                <UserAdd visible={isShowCreateModal} updateInfo={updateInfo} onRegister={this.getUserList} toggleVisible={this.toggleShowCreateModal} onCancel={this.closeInfoModal} />
                <UserDetail visible={isShowInfoModal} userInfo={userInfo} onCancel={this.closeInfoModal} />
            </div>
            // onRegister={this.getUsers}

        );
    }
}

export default User;