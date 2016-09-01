<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    $(function () {
        $("#<%=PcompConstants.PCOMP_VERSION%>dialog").dialog({
            autoOpen: false,
            minWidth:400,
            minHeight:400,
            show: {
                effect: "blind",
                duration: 1000
            },
            hide: {
                effect: "explode",
                duration: 1000
            },
            modal: true,
            buttons: {
                "提交修改": function () {
                    var n = $("#<%=FormUtils.mkTextInputId(PathConstants.PCOMP_VERSION_NUMBER)%>");
                    if (isNull(n[0].value)) {
                        n.parent().attr("class", "form-group has-warning");
                        n.next().html("不能为空!");
                        return false;
                    }else if(n[0].value.length>32){
                        n.parent().attr("class", "form-group has-warning");
                        $("#text-input").next().html("不能超过32字符!");
                        return false;
                    }
                    $("#<%=PcompConstants.PCOMP_VERSION%>Form").submit();
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            }
        });

        $("#edit<%=PcompConstants.PCOMP_VERSION%>").click(function () {
            $("#<%=PcompConstants.PCOMP_VERSION%>dialog").dialog("open");
        });

    });
</script>