import React from 'react';
import {Form, Input, Modal, TreeSelect, Select} from 'antd'
import {reqAuthTree} from '../../../api/api.js'


const FormItem = Form.Item;
const formItemLayout = {
    labelCol: {span: 6},
    wrapperCol: {span: 14}
};

class AuthModal extends React.Component {

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
        const result = await reqAuthTree();
        if (result.code === 200) {
            const data = result.data;
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

        if (modalStatus === 'add') {
            this.currentItem = null
        } else {
            this.currentItem = currentItem
            // this.currentItem.roleIds=[]
        }

        return (

            <Modal
                visible={visible}
                title={`${modalStatus === 'add' ? '添加' : '编辑'}权限`}
                okText='确定'
                cancelText='取消'
                onOk={this.onOk.bind(this)}
                onCancel={this.props.onCancel}
            >
                <Form ref={this.formRef} initialValues={this.currentItem} {...formItemLayout} >

                    <FormItem name="id" label="主键" hidden>
                        <Input/>
                    </FormItem>

                    <FormItem name="parentId" label="上级权限"
                              rules={[{required: true, message: '请选择上级权限'}]}>
                        <TreeSelect
                            treeData={treeData}
                        />
                    </FormItem>

                    <FormItem name="name" label="权限名称" rules={[
                        {
                            required: true,
                            message: '请输入权限名称',
                        }
                    ]}>
                        <Input placeholder="请输入权限名称"/>
                    </FormItem>

                    <FormItem name="mark" label="权限标识" rules={[
                        {
                            required: true,
                            message: '请输入权限标识',
                        }
                    ]}>
                        <Input placeholder="请输入权限标识"/>
                    </FormItem>

                    <FormItem name="type" label="类型" rules={[
                        {
                            required: true,
                            message: '请输入类型',
                        }
                    ]}>
                        <Select
                            // onChange={handleChange}
                            options={[
                                {
                                    value: '0',
                                    label: 'Jack',
                                },
                                {
                                    value: '1',
                                    label: 'Lucy',
                                },
                                {
                                    value: '2',
                                    label: 'Disabled',
                                }
                            ]}
                        />
                    </FormItem>

                    <FormItem name="description" label="描述信息">
                        <Input placeholder="请输入描述信息"/>
                    </FormItem>

                    <FormItem name="sort" label="排序码">
                        <Input placeholder="请输入排序码"/>
                    </FormItem>

                </Form>
            </Modal>

        )
    }
}

export default AuthModal;