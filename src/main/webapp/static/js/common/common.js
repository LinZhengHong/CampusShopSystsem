/*
*
* */
function changeVerifyCode(img) {
    img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}

/*通过正则表达式来处理url的shooId值的获取，在最新的Restful可以解决这种情况，以后可进行重构*/
function getQueryString(name) {
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if(r!=null){
        return decodeURIComponent(r[2]);
    }
    return '';
}

