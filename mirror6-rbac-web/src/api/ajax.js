import axios from 'axios'
import { message } from 'antd';
export default function ajax(url, data, type) {
    let promise;

    return new Promise((resolve, reject) => {
        if (type === "GET") {
            promise = axios.get(url, {
                params: data
            })
        } else if (type === "POST"){
            promise = axios.post(url, data)
        }else if (type === "DELETE") {
            promise = axios.delete(url,{data})
        }else if (type === "PUT") {
            promise = axios.put(url,data)
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