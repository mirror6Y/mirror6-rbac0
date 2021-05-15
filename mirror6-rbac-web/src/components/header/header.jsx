import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import { Modal } from 'antd';
import { ExclamationCircleOutlined } from '@ant-design/icons';

import LinkButton from '../button/button'
import { reqWeather } from '../../api/api'
import { formatDate } from '../../utils/dateUtil'
import memoryUtil from '../../utils/memoryUtil'
import storageUtil from '../../utils/storageUtil'
import menuList from '../../config/menuConfig'
import './header.css'

class Header extends Component {

    state = {
        currentTime: formatDate(Date.now()),
        weather: ""
    }

    getTime = () => {
        this.interval = setInterval(() => {
            const currentTime = formatDate(Date.now());
            this.setState({ currentTime })
        }, 1000)
    }

    getWeather = async () => {
        const { weather } = await reqWeather(330100);
        this.setState({ weather })
    }

    getTitle = () => {
        const path = this.props.location.pathname;
        let title;
        menuList.forEach(item => {
            if (item.url === path) {
                title = item.title
            } else if (item.children) {
                const cItem = item.children.find(cItem => path.indexOf(cItem.url) === 0)
                if (cItem) {
                    title = cItem.title
                }
            }
        })
        return title;
    }

    logout = () => {
        Modal.confirm({
            // title: 'Do you Want to delete these items?',
            icon: <ExclamationCircleOutlined />,
            content: '确认退出吗？',
            onOk: () => {
                console.log('OK');
                storageUtil.removeUser();
                memoryUtil.user = {}
                this.props.history.replace("/login");
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    }

    //第一次render调用
    componentDidMount() {
        this.getTime();
        this.getWeather();
    }

    //当前组件卸载前调用
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {

        const { currentTime, weather } = this.state;
        // const { username } = memoryUtil.user.username;
        const title = this.getTitle();

        return (
            <div className="header">
                <div className="header-top">
                    <span>欢迎，超级管理员</span>
                    <LinkButton onClick={this.logout} >退出</LinkButton>
                </div>
                <div className="header-bottom">
                    <div className="header-bottom-left">{title}</div>
                    <div className="header-bottom-right">
                        <span>{currentTime}</span>
                        <span>{weather}</span>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(Header);