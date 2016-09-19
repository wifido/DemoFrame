function getContextPath() {
    var pathName = document.location.pathname;

    var curWwwPath = window.document.location.href;
    var pos = curWwwPath.indexOf(pathName);

    var localhostPath = curWwwPath.substring(0, pos);

    var length = pathName.split("/");

    var result = '';
    if (length.length > 3) {
        if (length[1] != 'index') {
            result = '/' + length[1];
        }
    }
    return (localhostPath + result);
}
function getHtmlPath() {
    return getContextPath() + '/html';
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

var editorMdEditing = {
    width: "100%",
    emoji: true,
    tex: true,  // 默认不解析
    flowChart: true,  // 默认不解析
    sequenceDiagram: true,  // 默认不解析
    height: 640,
    syncScrolling: "single",
    imageUpload: true,
    imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
    imageUploadURL: getContextPath() + "/pcomp/software/content_image/upload",
    path: "lib/"
};
var editorMdViewing = {
    htmlDecode: "style,script,iframe",  // you can filter tags decode
    emoji: true,
    taskList: true,
    tex: true,  // 默认不解析
    flowChart: true,  // 默认不解析
    sequenceDiagram: true,  // 默认不解析
};

function isNull(str) {
    if (str == null || str == undefined || str == '') {
        return true;
    } else {
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    }
}

$.fn.extend({
    arrayVal: function () {
        var self = $(this);
        var result = [];

        for (i = 0; i < self.length; i++) {
            result.push(self[i].value);
        }
        return result;

    }

});

function imgHover(){
    $("img").hover(function(){
        $(this).addClass('animated tada');
    },function(){
        $(this).removeClass('animated tada');
    });
}
