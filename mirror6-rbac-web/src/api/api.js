import ajax from './ajax'
import jsonp from 'jsonp'

//登录 注意：加token会拦截
export const reqLogin = (data) => ajax('/api/login', data, null);

//天气
export const reqWeather = (cityCode) => {
    return new Promise((resolve, reject) => {
        const url = `https://restapi.amap.com/v3/weather/weatherInfo?city=${cityCode}&key=5b7a9d03558f93310350bc0849f3488f`;
        jsonp(url, {}, (error, data) => {
            if (!error && data.status === "1" && data.lives.length > 0) {
                const {weather, reporttime} = data.lives[0];
                resolve({weather, reporttime})
            } else {
                console.log("获取天气信息失败")
            }
        })
    })


}

// 用户列表
export const reqUserList = (data) => ajax('/api/rbac/systemUser', data, 'GET')

//添加用户
export const reqUserAdd = (data) => ajax('/api/rbac/systemUser', data, 'POST')

//删除用户
export const reqUserDelete = (data) => ajax('/api/rbac/systemUser', data, 'DELETE')

//编辑用户
export const reqUserEdit = (data) => ajax('/api/rbac/systemUser', data, 'PUT')

//编辑用户状态
export const reqUserStatusEdit = (data) => ajax('/api/rbac/user/updateStatus', data, 'PUT')

//查询用户信息
export const reqUserGet = (data) => ajax('/api/rbac/user/getUser/' + data, null, 'GET')


//--------------------------角色模块--------------------------------------------

// 角色列表(添加用户时使用)
export const reqRoleList = (data) => ajax('/api/rbac/systemRole', data, 'GET')

//树
export const reqRoleTree = () => ajax('/api/rbac/systemRole/getTree', null, 'GET')

//添加
export const reqRoleAdd = (data) => ajax('/api/rbac/systemRole', data, 'POST')

//删除
export const reqRoleDelete = (data) => ajax('/api/rbac/systemRole', data, 'DELETE')

//编辑
export const reqRoleEdit = (data) => ajax('/api/rbac/systemRole', data, 'PUT')


//--------------------------权限模块--------------------------------------------

// 菜单列表
export const reqAuthList = (data) => ajax('/api/rbac/systemAuthority', data, 'GET')

// 菜单树
export const reqAuthTree = () => ajax('/api/rbac/systemAuthority/getTree', null, 'GET')

//添加
export const reqAuthAdd = (data) => ajax('/api/rbac/systemAuthority', data, 'POST')

//删除
export const reqAuthDelete = (data) => ajax('/api/rbac/systemAuthority', data, 'DELETE')

//编辑
export const reqAuthEdit = (data) => ajax('/api/rbac/systemAuthority', data, 'PUT')

//--------------------------菜单模块--------------------------------------------


// 菜单列表
export const reqMenuList = (data) => ajax('/api/rbac/systemMenu', data, 'GET')

// 菜单树
export const reqMenuTree = () => ajax('/api/rbac/systemMenu/getTree', null, 'GET')

//添加
export const reqMenuAdd = (data) => ajax('/api/rbac/systemMenu', data, 'POST')

//删除
export const reqMenuDelete = (data) => ajax('/api/rbac/systemMenu', data, 'DELETE')

//编辑
export const reqMenuEdit = (data) => ajax('/api/rbac/systemMenu', data, 'PUT')