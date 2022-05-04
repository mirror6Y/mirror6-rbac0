import axios from 'axios'
import { message } from 'antd';
import storageUtil from '../utils/storageUtil'

export default function ajax(url, data, type) {
    let promise;

    return new Promise((resolve, reject) => {
        if (type === "GET") {
            promise = axios({
                method: 'get',
                url: url,
                params: data,
                headers: { 'authorization': storageUtil.getToken('token') }
            })
        } else if (type === "POST") {
            promise = axios({
                method: 'post',
                url: url,
                data: data,
                headers: { 'authorization': storageUtil.getToken('token') }
            })
        } else if (type === "DELETE") {
            promise = axios({
                method: 'delete',
                url: url,
                data: data,
                headers: { 'authorization': storageUtil.getToken('token') }
            })
        } else if (type === "PUT") {
            promise = axios({
                method: 'put',
                url: url,
                data: data,
                headers: { 'authorization': storageUtil.getToken('token') }
            })
        } else {
            promise = axios.post(url, data)
        }

        promise.then(response => {
            resolve(response.data)
        }).catch(error => {
            reject(error);
            message.error("请求出错：" + error.msg);
            console.log(error.msg)
        })
    })

}