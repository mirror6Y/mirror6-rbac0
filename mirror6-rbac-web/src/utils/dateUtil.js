//时间戳-> 2019-03-07 12:00:00
// export function formatDate(time) {
//     if (!time) { return "" }
//     let now = new Date(time);
//     var year = now.getFullYear();
//     var month = now.getMonth() + 1;
//     var date = now.getDate();
//     var hour = now.getHours();
//     var minute = now.getMinutes();
//     var second = now.getSeconds();
//     return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
// }

//将时间戳转换成日期格式
export function formatDate(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + h + m + s;
}

//Thu Mar 07 2019 12:00:00 GMT+0800 (中国标准时间)-> 2019-03-07 12:00:00
export function parseDate(now) {
    if (!now) {
        return ""
    }
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
