import React from 'react';
import {Form, Input, Modal, Radio, TreeSelect} from 'antd'
import {reqRoleTree} from '../../../api/api.js'


const FormItem = Form.Item;
const formItemLayout = {
    labelCol: {span: 6},
    wrapperCol: {span: 14}
};

class RoleModal extends React.Component {

    formRef = React.createRef();

    state = {
        treeData: [],
        roleTreeData: []
    }

    componentDidMount() {
        this.getTreeData();
        this.getRoleTreeData()
    }

    onOk = () => {
        let role = this.formRef.current.getFieldsValue();
        this.props.handleOk(role);
    }

    getTreeData = async () => {
        // const result = await reqMenuTree();
        // if (result.code === 200) {
        //     const data = result.data;
        //     console.log(JSON.stringify(data));
        //                 this.treeFormat(data,"auth");
        // }
    }

    getRoleTreeData = async () => {
        const result = await reqRoleTree();
        if (result.code === 200) {
            const data = result.data;
            console.log(JSON.stringify(data));
            this.treeFormat(data, "role");
        }
    }

    treeFormat = (data, type) => {
        data.map((item, index) => {
            item['title'] = item.name;
            item['value'] = item.id;
            item['key'] = item.id;
            item['children'] = item.childList
            delete item['id'];
            delete item['childList'];
            this.treeFormat(item.children);
        });

        if ("auth" === type) {
            this.setState({
                treeData: data
            });
        } else {
            this.setState({
                roleTreeData: data
            });
        }
    }


    render() {

        const {visible, modalStatus, currentItem} = this.props
        const {treeData, roleTreeData} = this.state

        console.log("7:" + JSON.stringify(currentItem))
        if (modalStatus === 'add') {
            this.currentItem = null
        } else {
            this.currentItem = currentItem
            // this.currentItem.roleIds=[]
        }
        return (

            <Modal
                visible={visible}
                title={`${modalStatus === 'add' ? '添加' : '编辑'}角色`}
                okText='确定'
                cancelText='取消'
                onOk={this.onOk.bind(this)}
                onCancel={this.props.onCancel}
            >
                <Form ref={this.formRef} initialValues={this.currentItem} {...formItemLayout} >

                    <FormItem name="id" label="主键" hidden>
                        <Input/>
                    </FormItem>

                    <FormItem name="name" label="角色名称" rules={[
                        {
                            required: true,
                            message: '请输入角色名称',
                        },
                    ]}>
                        <Input placeholder="请输入名称"/>
                    </FormItem>

                    <FormItem name="description" label="描述信息" rules={[
                        {
                            required: true,
                            message: '请输入描述信息',
                        },
                    ]}>
                        <Input placeholder="请输入描述信息"/>
                    </FormItem>

                    <FormItem name="parentId" label="上级角色">
                        <TreeSelect
                            allowClear="true"
                            // treeCheckable="true"
                            treeData={roleTreeData}
                            placeholder="请选择角色"
                        />

                    </FormItem>

                    <FormItem name="sort" label="排序码">
                        <Input placeholder="请输入排序码"/>
                    </FormItem>

                    <FormItem name="authIds" label="权限" rules={[
                        {
                            required: true,
                        },
                    ]}>
                        <TreeSelect
                            allowClear="true"
                            treeCheckable="true"
                            treeData={treeData}
                            placeholder="请选择菜单"
                            defaultValue={1}
                        />

                    </FormItem>

                </Form>
            </Modal>

        )
    }
}

export default RoleModal;