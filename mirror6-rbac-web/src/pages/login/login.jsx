import React, { Component } from 'react';
// import { Redirect } from 'react-router-dom'
import { Form, Input, Button, Checkbox, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
// import Login from './login'
import './login.css';
import logo from '../../assets/images/log.jpg';
import { reqLogin } from '../../api/api'
import memoryUtil from '../../utils/memoryUtil'
import storageUtil from '../../utils/storageUtil'


class login extends Component {

    formRef = React.createRef();

    onFinish = async values => {
        console.log(values);

        const result = await reqLogin(values);
        if (result.code === 200) {
            console.log(JSON.stringify(result.data))
            message.success("登录成功");
            // const user = result.data;
            // memoryUtil.user = user;
            // storageUtil.setUser(user);
            this.props.history.replace('/');
        } else {
            message.error("登录失败");
        }
        // message.success("登录成功");
        // this.props.history.replace('/');

    }

    onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };
    render() {
        // const user = memoryUtil.user;
        // if (user && user.id) {
        //     return <Redirect to='./loi' />
        // }

        return (
            <div className="login">
                <header className="login-header">
                    <h1>CityMis</h1>
                    <img src={logo} alt="logo"></img>
                </header>
                <section className="login-content">
                    <h2>用户登录</h2>
                    <Form
                        ref={this.formRef}
                        name="normal_login"
                        className="login-form"
                        initialValues={{
                            remember: true,
                        }}
                        onFinish={this.onFinish}
                        onFinishFailed={this.onFinishFailed}
                    >
                        <Form.Item
                            name="username"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入账号!'
                                },
                            ]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="账号" />
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入密码!'
                                },
                            ]}
                        >
                            <Input
                                prefix={<LockOutlined className="site-form-item-icon" />}
                                type="password"
                                placeholder="密码"
                            />
                        </Form.Item>
                        <Form.Item>
                            <Form.Item name="remember" valuePropName="checked" noStyle>
                                <Checkbox>记住我</Checkbox>
                            </Form.Item>

                            <a className="login-form-forgot" href="">忘记密码</a>
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="login-form-button">登录</Button>
                        </Form.Item>
                    </Form>
                </section>
            </div>
        );
    }
}

export default login;