<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.FormUtils" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function remove<%=PcompConstants.PCOMP_SOFTWARE%>(pcompSoftwareId, pcompSoftwareName) {
        if (confirm("你确定要删除" + '<%=PcompConstants.PCOMP_SOFTWARE%>' + "-" + pcompSoftwareName + "吗？")) {
            window.location.href = '<%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR, PcompConstants.PCOMP_SOFTWARE,
                                Constants.PARAMETER_EQUALS)%>' + pcompSoftwareId;
        }
    }

    $(function () {
        $("#<%=PcompConstants.PCOMP_SOFTWARE%>dialog").dialog({
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
                    var n = $("#<%=FormUtils.mkTextInputId(PathConstants.PCOMP_SOFTWARE_NAME)%>");
                    if (isNull(n[0].value)) {
                        n.parent().attr("class", "form-group has-warning");
                        n.next().html("不能为空!");
                        return false;
                    }else if(n[0].value.length>32){
                        n.parent().attr("class", "form-group has-warning");
                        $("#text-input").next().html("不能超过32字符!");
                        return false;
                    }
                    $("#<%=PcompConstants.PCOMP_SOFTWARE%>Form").submit();
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            }
        });

        $("#edit<%=PcompConstants.PCOMP_SOFTWARE%>").click(function () {
            $("#<%=PcompConstants.PCOMP_SOFTWARE%>dialog").dialog("open");
        });

    });
</script>
<jsp:include page="../../common/form/util.jsp"></jsp:include>
