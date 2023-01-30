import React from 'react';
import {Form, Input, Modal, Radio, TreeSelect} from 'antd'
import {reqRoleTree} from '../../../api/api.js'


const FormItem = Form.Item;
const formItemLayout = {
    labelCol: {span: 6},
    wrapperCol: {span: 14}
};

class UserModal extends React.Component {

    formRef = React.createRef();

    state = {
        treeData: []
    }

    componentDidMount() {
        this.getTreeData();
    }

    onOk = () => {
        let user = this.formRef.current.getFieldsValue();
        this.props.handleOk(user);
    }

    getTreeData = async () => {
        const result = await reqRoleTree();
        if (result.code === 200) {
            const data = result.data;
            console.log(JSON.stringify(data));
            this.treeFormat(data);
        }
    }

    treeFormat = (data) => {
        data.map((item, index) => {
            item['title'] = item.name;
            item['value'] = item.id;
            item['key'] = item.id;
            item['children'] = item.childList
            delete item['id'];
            delete item['childList'];
            this.treeFormat(item.children);
        });
        this.setState({
            treeData: data
        });
    }


    render() {

        const {visible, modalStatus, currentItem} = this.props
        const {treeData} = this.state

        // console.log("6:" + modalStatus)
        console.log("7:" + JSON.stringify(currentItem))
        if (modalStatus === 'add') {
            this.currentItem = null

            // this.formRef.current.setFieldsValue({
            //     gender: currentItem.gender,
            // })

        }else{
            this.currentItem=currentItem
            // this.currentItem.roleIds=[]
        }
        return (

            <Modal
                visible={visible}
                title={`${modalStatus === 'add' ? '添加' : '编辑'}用户`}
                okText='确定'
                cancelText='取消'
                onOk={this.onOk.bind(this)}
                onCancel={this.props.onCancel}
            >
                <Form ref={this.formRef} initialValues={this.currentItem} {...formItemLayout} >

                    <FormItem name="id" label="主键" hidden>
                        <Input/>
                    </FormItem>

                    <FormItem name="account" label="账号" rules={[
                        {
                            required: true,
                            message: '请输入账号',
                        },
                        {
                            pattern: new RegExp(/^[0-9]\d*$/, "g"),
                            message: '账号由数字组成',
                        },
                    ]}>
                        <Input placeholder="请输入账号"/>
                    </FormItem>

                    <FormItem name="name" label="姓名" rules={[
                        {
                            required: true,
                            message: '请输入姓名',
                        },
                    ]}>
                        <Input placeholder="请输入姓名"/>
                    </FormItem>

                    <FormItem name="gender" label="性别">
                        <Radio.Group >
                            <Radio value={1}>男</Radio>
                            <Radio value={0}>女</Radio>
                        </Radio.Group>
                    </FormItem>

                    <FormItem name="tel" label="手机号码">
                        <Input placeholder="请输入手机号码"/>
                    </FormItem>

                    <FormItem name="email" label="电子邮箱" rules={[
                        {
                            type: 'email',
                            message: '请输入正确的邮箱',
                        },
                    ]}>
                        <Input placeholder="请输入电子邮箱"/>
                    </FormItem>

                    <FormItem name="roleIds" label="角色" rules={[
                        {
                            required: true,
                        },
                    ]}>
                        <TreeSelect
                            allowClear="true"
                            treeCheckable="true"
                            treeData={treeData}
                            placeholder="请选择角色"
                            // value={this.currentItem.roleIds}
                        />

                    </FormItem>

                </Form>
            </Modal>

        )
    }
}

export default UserModal;