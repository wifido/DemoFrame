<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function remove<%=PcompConstants.PCOMP_VERSION%>Extend(versionId, id, name, kind) {

        if (confirm("你确定要删除" + '<%=PcompConstants.PCOMP_VERSION%>' + "-" + name + "吗？")) {
            switch(kind){
                case 1:
                    window.location.href = '<%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR,
                                PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD, Constants.PARAMETER_EQUALS)%>' + id
                    +'<%=StrUtils.makeString(Constants.PARAMETER_SEPARATOR,
                        PcompConstants.PCOMP_VERSION, Constants.PARAMETER_EQUALS)%>' + versionId;
                    break;
                case 2:
                    window.location.href = '<%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR,
                                PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT, Constants.PARAMETER_EQUALS)%>' + id
                            +'<%=StrUtils.makeString(Constants.PARAMETER_SEPARATOR,
                        PcompConstants.PCOMP_VERSION, Constants.PARAMETER_EQUALS)%>' + versionId;
                    break;
            }
        }
    }

    $(function () {
        $("#downloadModificationDialog").dialog({
            autoOpen: false,
            minWidth: 400,
            minHeight: 400,
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
                    var n = $("#text-input");
                    if (isNull(n[0].value)) {
                        n.parent().attr("class", "form-group has-warning");
                        $("#text-input").next().html("不能为空!");
                        return false;
                    } else if(n[0].value.length>32){
                        n.parent().attr("class", "form-group has-warning");
                        $("#text-input").next().html("不能超过32字符!");
                        return false;
                    }
                    if($("#downloadModificationDialog").attr("hide")=="增加"){
                        n = $("#file-input");
                        if(isNull(n[0].value)){
                            n.parent().parent().parent().parent().parent().attr("class", "form-group has-warning");
                            n.parent().parent().parent().parent().next().html("不能为空!");
                            return false;
                        }
                    }
                    $("#downloadModificationForm").submit();
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            }
        });


    });
    function edit<%=PcompConstants.PCOMP_VERSION%>(id, name, kind) {
        switch (kind) {
            case 1:
                $("#downloadModificationDialog").attr("hide", "修改");
                $("#downloadModificationForm").attr("action", "<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_MODIFICATION_PATH)%>");
                $("#id-input").attr("value", id).attr("name", "<%=PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD%>");
                $("#text-input").attr("name", "<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM%>").attr("value", name);
                $("#text-input").parent().attr("class", "form-group has-success");
                $("#text-input").next().html("");
                $("#file-input").attr("name", "<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD%>");
                $("#file-input").parent().parent().parent().parent().parent().attr("class", "form-group has-success");
                $("#file-input").parent().parent().parent().parent().next().html("");
                break;
            case 2:
                $("#downloadModificationDialog").attr("hide", "修改");
                $("#downloadModificationForm").attr("action", "<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_MODIFICATION_PATH)%>");
                $("#id-input").attr("value", id).attr("name", "<%=PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT%>");
                $("#text-input").attr("name", "<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION%>").attr("value", name);
                $("#text-input").parent().attr("class", "form-group has-success");
                $("#text-input").next().html("");
                $("#file-input").attr("name", "<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD%>");
                $("#file-input").parent().parent().parent().parent().parent().attr("class", "form-group has-success");
                $("#file-input").parent().parent().parent().parent().next().html("");
                break;
            case 3:
                $("#downloadModificationDialog").attr("hide", "增加");
                $("#downloadModificationForm").attr("action", "<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_CREATE_PATH)%>");
                $("#text-input").attr("name", "<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_PLATFORM%>").attr("value", "");
                $("#text-input").parent().attr("class", "form-group has-success");
                $("#text-input").next().html("");
                $("#file-input").attr("name", "<%=PathConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD_DOWNLOAD%>");
                $("#file-input").parent().parent().parent().parent().parent().attr("class", "form-group has-success");
                $("#file-input").parent().parent().parent().parent().next().html("");
                break;
            case 4:
                $("#downloadModificationDialog").attr("hide", "增加");
                $("#downloadModificationForm").attr("action", "<%=PathUtils.makePath(PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_CREATE_PATH)%>");
                $("#text-input").attr("name", "<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DESCRIPTION%>").attr("value", "");
                $("#text-input").parent().attr("class", "form-group has-success");
                $("#text-input").next().html("");
                $("#file-input").attr("name", "<%=PathConstants.PCOMP_VERSION_DOCUMENT_DOWNLOAD_DOWNLOAD%>");
                $("#file-input").parent().parent().parent().parent().parent().attr("class", "form-group has-success");
                $("#file-input").parent().parent().parent().parent().next().html("");
        }
        $("#downloadModificationDialog").dialog("open");
    }
</script>