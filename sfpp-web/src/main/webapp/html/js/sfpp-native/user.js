$.user = {
    get: function () {
        var result;
        $.ajax({
            url: getContextPath() + "/user/get",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    },
    getUserInfo: function (userId) {
        var result;
        $.ajax({
            url: getContextPath() + "/user/getUserInfo?userId=" + userId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    },
    getUserHistory: function (userId, pageNum) {
        var result;
        $.ajax({
            url: getContextPath() + "/user/getHistory?userId=" + userId + "&pageNum=" + pageNum,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    },
    getUserHistory:function(userId,pageNum){
        var result;
        $.ajax({
            url: getContextPath() + "/user/getUserHistory?userId=" + userId + "&pageNum" + pageNum,
            async:false,
            success: function (response) {
                if(response.message!=""){
                    alert(response.message);
                    return;
                }else{
                    result = response.data;
                }
            }
        });
        return result;
    }
}