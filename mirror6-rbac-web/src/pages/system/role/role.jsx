import React, { Component } from 'react';
import { Card, Button, Table } from 'antd'

import { reqRoleList } from '../../../api/api.js'

import RoleAdd from '../role/roleAdd'

class Role extends Component {

    state = {
        roleList: [],
        // 0 不显示添加和修改组件 1 显示添加 2显示修改
        showStatus: 0,
    }

    initColumn = () => {
        this.columns = [
            {
                title: '角色名称',
                dataIndex: 'name',
            },
            {
                title: '描述信息',
                dataIndex: 'description',
            },
            {
                title: '排序码',
                dataIndex: 'sort',
            },
            {
                title: '启用状态',
                dataIndex: 'enabled',
            },
            {
                title: '创建时间',
                dataIndex: 'gmtCreate',
            }
        ]
    }

    getRoleList = async () => {
        const result = await reqRoleList();
        if (result.code === 200) {
            const data = result.data.records;
            this.setState({ roleList: data })
        }
    }

    //显示添加组件
    toggleVisible = (status) => {
        this.setState({
            showStatus: status
        })
    }

    componentWillMount() {
        this.initColumn();
    }

    componentDidMount() {
        this.getRoleList();
    }

    render() {

        const { roleList, showStatus } = this.state;
        const title = (
            <span>
                <Button type="primary" onClick={() =>this.toggleVisible(1)}>新增</Button>
            </span>
        )

        return (
            <Card title={title}>
                <Table
                    rowKey="id"
                    rowSelection={{ type: "checkout" }}
                    columns={this.columns}
                    dataSource={roleList} />
                <RoleAdd visible={showStatus} toggleVisible={this.toggleVisible} onRefresh={this.getRoleList} />
            </Card>
        );
    }
}

export default Role;