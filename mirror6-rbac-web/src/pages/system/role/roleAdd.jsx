import React from 'react';
import { Form, Input, Modal, notification } from 'antd'
import { reqRoleAdd } from '../../../api/api'
const FormItem = Form.Item;

class RoleAdd extends React.Component {

    formRef = React.createRef();

    state = {}

    //添加验证
    handleOk = () => {
        const ref = this.formRef.current;
        this.props.toggleVisible(0);
        ref.validateFields()
            .then(values => {
                console.log(values)
                ref.resetFields();
                this.addRole(values);
            })
            .catch(errorInfo => {
                console.log(errorInfo)
            });
    }

    //添加方法
    addRole = async (data) => {
        const result = await reqRoleAdd(data);
        if (result.code === 200) {
            notification.success({
                duration: 2,
                message: '提示',
                description: result.msg
            });
            this.props.onRefresh();
        } else {
            notification.error({
                duration: null,
                message: '提示',
                description: result.msg
            })
        }
    }

    //取消
    onCancel = () => {
        this.formRef.current.resetFields()
        this.props.toggleVisible(0);
    }

    render() {

        const { visible } = this.props

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };

        return (
            <Modal visible={visible === 1} title="添加角色" okText='确定'
                cancelText='取消' onOk={this.handleOk} onCancel={this.onCancel} >

                <Form ref={this.formRef} {...formItemLayout} >

                    <FormItem name="name" label="角色名称" rules={[
                        {
                            required: true,
                            message: '请输入角色名称',
                        },
                    ]} >
                        <Input placeholder="请输入角色名称" />
                    </FormItem>

                    <FormItem name="description" label="描述信息" rules={[
                        {
                            required: true,
                            message: '请输入描述信息',
                        },
                    ]} >
                        <Input placeholder="请输入描述信息" />
                    </FormItem>

                    <FormItem name="sort" label="排序码" >
                        <Input placeholder="请输入排序码" />
                    </FormItem>

                </Form>
            </Modal>
        )
    }
}

export default RoleAdd;