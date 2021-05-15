import React, { Component } from 'react';
import { Form, Input, Radio, TreeSelect } from 'antd'
import { reqRoleList } from '../../../api/api.js'

const FormItem = Form.Item;

class UserAdd extends Component {

    addRef = React.createRef();

    state = {
        treeData: []
    }

    getTreeData = async () => {

        const result = await reqRoleList();
        console.log("1:"+result);
        if (result.code === 200) {
            const data = result.data;
            // if (null != data && undefined != data) {
                this.setState({
                    treeData: data,
                })
            // }

        }
    }

    componentDidMount() {
        this.getTreeData();
    }

    render() {

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };

        const { treeData } = this.state;


        return (

            <Form {...formItemLayout} ref={this.addRef}  >

                <FormItem name="account" label="账号" rules={[
                    {
                        required: true,
                        message: '请输入账号',
                    },
                    {
                        pattern: new RegExp(/^[0-9]\d*$/, "g"),
                        message: '账号由数字组成',
                    },
                ]} >
                    <Input placeholder="请输入账号" />
                </FormItem>

                <FormItem name="name" label="姓名" rules={[
                    {
                        required: true,
                        message: '请输入姓名',
                    },
                ]} >
                    <Input placeholder="请输入姓名" />
                </FormItem>

                <FormItem name="gender" label="性别">
                    <Radio.Group>
                        <Radio value="1">男</Radio>
                        <Radio value="0">女</Radio>
                    </Radio.Group>
                </FormItem>

                <FormItem name="tel" label="手机号码" >
                    <Input placeholder="请输入手机号码" />
                </FormItem>

                <FormItem name="email" label="电子邮箱" rules={[
                    {
                        type: 'email',
                        message: '请输入正确的邮箱',
                    },
                ]} >
                    <Input placeholder="请输入电子邮箱" />
                </FormItem>

                <Form.Item name="roleIds" label="角色" rules={[
                    {
                        required: true,
                    },
                ]} >
                    <TreeSelect
                        allowClear="true"
                        treeCheckable="true"
                        treeData={treeData}
                        placeholder="请选择角色"
                        treeDefaultExpandAll
                        showCheckedStrategy="SHOW_PARENT"
                    />

                </Form.Item>
            </Form>
        );
    }
}

export default UserAdd;