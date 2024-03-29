import React, { Component } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom'
import { Layout } from 'antd'
// import memoryUtil from '../../utils/memoryUtil'
import Nav from '../../components/nav/nav'
import Header from '../../components/header/header'
import Home from '../home/home'
import User from '../system/user/user'
import Role from '../system/role/role'
import Auth from '../system/auth/auth'
import PrivateRoute from '../../config/privateRoute'


const { Footer, Sider, Content } = Layout;

class index extends Component {
    render() {
        // const user = memoryUtil.user;
        // if (!user || !user.id) {
        //     return <Redirect to='/login' />
        // }
        return (
            <Layout style={{ height: '100%' }}>
                <Sider>
                    <Nav />
                </Sider>
                <Layout>
                    <Header >Header</Header>
                    <Content style={{ margin:20,backgroundColor: '#fff' }}>
                        <Switch>
                            <Route path="/home" component={Home}/>
                            <Route path="/system/user" component={User}/>
                            <PrivateRoute path="/system/role" component={Role}/>
                            <Route path="/system/auth" component={Auth}/>
                            <Redirect to='/home'/>
                        </Switch>
                    </Content>
                    <Footer style={{ textAlign: "center" }}>Footer</Footer>
                </Layout>
            </Layout>
            // <div>
            //     hello {user.username}
            // </div>
        );
    }
}

export default index;