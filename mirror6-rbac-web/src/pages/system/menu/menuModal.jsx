import React from 'react';
import { Form, Input, Modal, TreeSelect } from 'antd'
import { reqMenuTree } from '../../../api/api.js'


const FormItem = Form.Item;
const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 14 }
};

class MenuModal extends React.Component {

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
        const result = await reqMenuTree();
        if (result.code === 200) {
            const data = result.data;
            this.treeFormat(data);
        }
    }

    treeFormat = (data) => {
        data.map((item, index) => {
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

        const { visible, modalStatus, currentItem } = this.props
        const { treeData } = this.state


        return (

            <Modal
                visible={visible}
                title={`${modalStatus === 'add' ? '添加' : '编辑'}菜单`}
                okText='确定'
                cancelText='取消'
                onOk={this.onOk.bind(this)}
                onCancel={this.props.onCancel}
            >
                <Form ref={this.formRef} initialValues={currentItem} {...formItemLayout} >

                    <FormItem name="id" label="主键" hidden>
                        <Input />
                    </FormItem>

                    <FormItem name="parentId" label="父级菜单"
                        rules={[{ required: true, message: '请选择父级菜单' }]}>
                        <TreeSelect
                            treeData={treeData}
                        />
                    </FormItem>

                    <FormItem name="title" label="菜单名称" rules={[
                        {
                            required: true,
                            message: '请输入菜单名称',
                        }
                    ]} >
                        <Input placeholder="请输入菜单名称" />
                    </FormItem>

                    <FormItem name="description" label="描述信息" >
                        <Input placeholder="请输入描述信息" />
                    </FormItem>

                    <FormItem name="url" label="url" rules={[
                        {
                            required: true,
                            message: '请输入url',
                        }
                    ]} >
                        <Input placeholder="请输入url" />
                    </FormItem>

                    <FormItem name="icon" label="图标信息" >
                        <Input placeholder="请输入图标信息" />
                    </FormItem>

                </Form>
            </Modal>

        )
    }
}

export default MenuModal;