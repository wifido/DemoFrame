var matrix_item_1 = "<div class=\"col-xs-6 col-sm-4 col-md-4 col-lg-4\"><div class=\"item\"><div class=\"pos-rlt\">";
var matrix_item_2 = "<a href=\"";
var matrix_item_3 = "\"><img src=\"";
var matrix_item_4 = "\" alt=\"\" class=\"r r-2x img-full\"></a></div><div class=\"padder-v\"><a href=\"";
var matrix_item_5 = "\" class=\"text-ellipsis\" style=\"text-align:center\">";
var matrix_item_6 = "</a></div></div></div>";

var matrix_item_7 = "<div class=\"col-xs-6 col-sm-4 col-md-3 col-lg-2\"><div class=\"item\"><div class=\"pos-rlt\">";


var list_item_1 = "<a href=\"";
var list_item_2 = "\" class=\"list-group-item clearfix\"><span class=\"pull-right h2 text-muted m-l\">";
var list_item_3 = "</span><span class=\"pull-left thumb-sm avatar m-r\"><img src=\"";
var list_item_4 = "\" alt=\"...\"></span><span class=\"clear\"><span>";
var list_item_5 = "</span><small class=\"text-muted clear text-ellipsis\">";
var list_item_6 = "</small></span></a>"

$.pcomp = {};
$.pcomp.software = {
    getRecommended: function () {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    for (i = 0; i < response.data.length; i++) {
                        a += matrix_item_1 + matrix_item_2 + matrix_item_3 + response.data[i].avatar + matrix_item_4 + matrix_item_5 + response.data[i].name + matrix_item_6;
                    }

                }
            }
        });
        return a;
    },
    getHot: function () {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    for (i = 0; i < response.data.length; i++) {
                        a += list_item_1 + list_item_2 + i + list_item_3 + response.data[i].avatar + list_item_4 +response.data[i].name +  list_item_5 + response.data[i].createdBy +  list_item_6;
                    }

                }
            }
        });
        return a;
    }
};
$.pcomp.kind = {
    getRecommended: function () {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/kind/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    for (i = 0; i < response.data.length; i++) {
                        a += matrix_item_7 + matrix_item_2 + matrix_item_3 + response.data[i].bannerImage + matrix_item_4 + matrix_item_5 + response.data[i].name + matrix_item_6;
                    }

                }
            }
        });
        return a;
    }
};


