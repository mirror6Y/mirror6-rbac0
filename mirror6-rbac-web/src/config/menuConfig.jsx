import {
    DashboardOutlined,
    SettingOutlined,
    UserOutlined,
    TeamOutlined,
    TableOutlined,
    // SolutionOutlined,
    Loading3QuartersOutlined
} from '@ant-design/icons';
import React from 'react';

const menuList = [
    {
        title: "首页",
        key: "/home",
        url: "/home",
        icon: <DashboardOutlined />
    },
    {
        title: "系统信息",
        key: "system",
        icon: <SettingOutlined />,
        children: [
            {
                title: "用户管理",
                key: "/system/user",
                url: "/system/user",
                icon: <UserOutlined />
            },
            {
                title: "角色管理",
                key: "/system/role",
                url: "/system/role",
                icon: <TeamOutlined />
            },
            {
                title: "菜单管理",
                key: "/system/menu",
                url: "/system/menu",
                icon: <TeamOutlined />
            }
        ]
    },
    {
        title: "基础信息",
        key: "basic",
        icon: <TableOutlined />,
        children: [
            {
                title: "教师信息",
                key: "teacher",
                url: "/basic/teacher",
                icon: <Loading3QuartersOutlined />
            },
            {
                title: "学生信息",
                key: "student",
                url: "/basic/student",
                icon: <Loading3QuartersOutlined />
            }
        ]
    }
]

export default menuList