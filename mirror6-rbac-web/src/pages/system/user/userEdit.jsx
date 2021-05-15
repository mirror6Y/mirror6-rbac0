import React, { Component } from 'react';
import { Form, Input, Radio, TreeSelect,Modal } from 'antd'
import { reqRoleList } from '../../../api/api.js'

const FormItem = Form.Item;

class UserEdit extends Component {

    editRef = React.createRef();

    state = {
        treeData: [],
        // treeData1: [{ title: "普通角色", value: "1305056278851444738", key: "1305056278851444738" },
        // { title: "学生管理员", value: "1305130522406924290", key: "1305130522406924290" }, { title: "教师管理员", value: "1306882513755795458", key: "1306882513755795458" }],
        value: undefined,
        // value1: ["1305056278851444738"]
    }

    getTreeData = async () => {
        const result = await reqRoleList();
        if (result.code === 200) {
            const data = result.data;
            console.log(data)
            this.setState({
                treeData: data,
            })
        }
    }

    componentDidMount() {
        this.getTreeData();
    }

    componentDidUpdate() {
        const { userData } = this.props;
        this.editRef.current.setFieldsValue({ ...userData });
    }

    UNSAFE_componentWillReceiveProps(){
        const { userData } = this.props;

        this.setState({
            value: userData.roleIds
        })
    }


    render() {

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };

        const { treeData, value, treeData1, value1 } = this.state;

        return (
            <Form {...formItemLayout} ref={this.editRef}  >
                <FormItem name="id" label="主键" hidden>
                    <Input />
                </FormItem>

                <FormItem name="account" label="账号" >
                    <Input placeholder="请输入账号" disabled />
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
                <FormItem name="roleIds" label="角色">
                    <TreeSelect
                        allowClear="true"
                        treeCheckable="true"
                        treeData={treeData}
                        value={value}
                        showCheckedStrategy="SHOW_PARENT"
                    />
                </FormItem>
            </Form>
        );
    }
}

export default UserEdit;