//时间戳-> 2019-03-07 12:00:00
export function formatDate(time) {
    if (!time) { return "" }
    let now = new Date(time);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
//Thu Mar 07 2019 12:00:00 GMT+0800 (中国标准时间)-> 2019-03-07 12:00:00
export function parseDate(now) {
    if (!now) { return "" }
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}
