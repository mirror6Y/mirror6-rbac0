import React from 'react';
import { Form, Input, Select, Modal, Radio, notification } from 'antd'
import axios from 'axios';
const FormItem = Form.Item;
const Option = Select.Option;


class UserAdd extends React.Component {

    formRef = React.createRef();


    state = {}


    componentDidMount() {
        var data = this.props.updateInfo;
        console.log("add:" + data)
        if (data != "add") {
            // const [form] = Form.useForm();
            // React.useEffect(() => {
            //     this.formRef.setFieldsValue({
            //         data
            //     });
            // }, []);
console.log(this.formRef)
            
        }
    }

    handleOk = () => {

        let user = this.formRef.current.getFieldsValue();
        console.log(user)
        const url = "/system/user/addUser";
        const _this = this;
        axios.post(url,
            {
                data: user
            })
            .then(function (response) {

                if (response.status === 200) {
                    notification.success({
                        duration: 1,
                        message: '提示',
                        description: response.data.msg
                    })
                    _this.props.toggleVisible(false);
                    _this.props.onRegister()
                }
            })
            .catch(function (error) {
                console.log(error);
            })

    };

    handleCancel = () => {
        // this.props.form.resetFields()
        this.props.toggleVisible(false)
    }

    render() {

        const { visible } = this.props

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };

        return (

            <Modal visible={visible} title="添加用户" okText='确定'
                cancelText='取消' onOk={this.handleOk} onCancel={this.props.onCancel} >

                <Form ref={this.formRef} {...formItemLayout} >

                    <FormItem name="account" label="账号" rules={[
                        {
                            required: true,
                            message: '请输入账号',
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

                    <FormItem name="email" label="电子邮箱" >
                        <Input placeholder="请输入电子邮箱" />
                    </FormItem>

                    <FormItem name="select" label="角色" hasFeedback
                        rules={[{ required: true, message: '请选择角色!' }]}
                    >
                        <Select placeholder="请选择角色">
                            <Option value="1">普通用户</Option>
                            <Option value="2">管理员</Option>
                        </Select>
                    </FormItem>

                </Form>
            </Modal>

        )
    }
}

export default UserAdd;