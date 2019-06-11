function setCookie(name, value, hour) {
    if (!name || !value)
        return;
    var date = new Date();
    date.setTime(date.getTime() + hour * 60 * 60 * 1000);

    document.cookie = name + '=' + value + ';expires=' + date.toGMTString();
}

function getCookie(name) {
    if (!name)
        return null;

    var arr = new Array();
    var reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return arr[2];
    else
        return null;
}

function delCookie(name) {
    if (!name)
        return null;

    var date = new Date();
    date.setTime(date.getTime() - 1);
    var cookieVal = getCookie(name);
    if (cookieVal)
        document.cookie = name + "=" + cookieVal + ";expires=" + date.toGMTString();
}

