import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom'
import { Menu } from 'antd';
import './nav.css';
import logo from '../../assets/images/log.jpg';
import menuList from '../../config/menuConfig'


const { SubMenu } = Menu;
class Nav extends Component {

    state = {
        collapsed: false,
    };

    //map+递归
    getMenuNodes_map = (menuList) => {
        return menuList.map(item => {
            if (!item.children) {
                return (
                    <Menu.Item key={item.key} >
                        {item.icon}
                        <Link to={item.url}>{item.title}</Link>
                    </Menu.Item>
                )
            } else {
                return (
                    <SubMenu key={item.key} icon={item.icon} title={item.title}>
                        {this.getMenuNodes_map(item.children)}
                    </SubMenu>
                )
            }

        })
    }

    //reduce+递归
    getMenuNodes_reduce = (menuList) => {

        const path = this.props.location.pathname;

        return menuList.reduce((pre, item) => {
            //向pre添加<Menu.Item>
            //向pre添加<SubMenu>
            if (!item.children) {
                pre.push(
                    (
                        <Menu.Item key={item.key} >
                            {item.icon}
                            <Link to={item.url}>{item.title}</Link>
                        </Menu.Item>
                    )
                )
            } else {
                //查找一个与当前路径匹配的子item
                const cItem = item.children.find(cItem => cItem.key === path)
                //如果存在，说明当前item的子列表需要打开
                if (cItem) {
                    this.openKey = item.key;
                }
                pre.push(
                    (
                        <SubMenu key={item.key} icon={item.icon} title={item.title}>
                            {this.getMenuNodes_map(item.children)}
                        </SubMenu>
                    )
                )
            }
            return pre;
        }, [])
    }

    componentWillMount() {
        this.menuNodes = this.getMenuNodes_reduce(menuList);
    }

    render() {
        const path = this.props.location.pathname;
        const openKey = this.openKey;
         
        return (
            <div>
                <div className="nav">
                    <Link to='/home' className="nav-header">
                        <h1>RBAC</h1>
                    </Link>

                    <Menu
                        selectedKeys={[path]}
                        defaultOpenKeys={[openKey]}
                        mode="inline"
                        theme="dark"
                    >
                        {
                            this.menuNodes
                        }

                    </Menu>
                </div>
            </div>

        );
    }
}

//withRouter高阶组件：包装非路由组件，返回一个新的组件。
//新的组件向非路由组件传递3个属性：history、location、match
export default withRouter(Nav)
// export default Nav