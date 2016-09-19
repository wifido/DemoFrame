$(document).ready(function () {
    $("form[role=search]").submit(function () {
        var value = $("input[class*=form-control][class*=input-sm][class*=no-border][class*=rounded]")[0].value;
        var content = $("#bjax-target");
        if (isNull(value)) {
            return false;
        }
        var result = $.search.searchPcomp(value, "", "");
        var resultDiv = $("<section class='panel-body'></section>");
        var html = "";
        for (i = 0; i < result.length; i++) {
            html += "<article class='media' style='display:none'><div class='pull-left'><span class='fa-stack fa-lg'><img src='"
                + result[i].avatar + "' class='fa fa-bold fa-stack-1x text-white'></span></div><div class='media-body'><a href='" + result[i].link + "' class='h4'>" + result[i].title + "</a> <small class='block m-t-xs'>" + result[i].introduction +
                "</small><em class='text-xs'>最后修改于 <span class='text-danger'>" + result[i].modifiedTime + "</span></em></div></article><div class='line pull-in'></div>";
        }
        resultDiv.html(html);
        content.empty();
        content.append(resultDiv);
        var articles = $("article");
        for (i = 0; i < articles.length; i++) {
            $(articles[i]).delay(i*200).queue(function(){
                $(this).attr("style","");
                $(this).addClass('animated bounce');
            });
        }
        return false;
    });
});
$.search = {
    searchPcomp: function (keyword, category, sortedBy) {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/search?keyword=" + keyword + "&category=" + category + "&sorted_by" + sortedBy,
            async: false,
            success: function (response) {
                if (response.data != null) {
                    result = response.data;
                } else {
                    alert(response.message);
                }
            }
        });
        return result;
    }
};