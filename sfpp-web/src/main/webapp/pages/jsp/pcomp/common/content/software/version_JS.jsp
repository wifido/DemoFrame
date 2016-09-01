<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function remove<%=PcompConstants.PCOMP_VERSION%>(pcompVersionId, pcompVersionNumber) {
        if (confirm("你确定要删除" + '<%=PcompConstants.PCOMP_VERSION%>' +"-" + pcompVersionNumber + "吗？")) {
            window.location.href =' <%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_VERSION_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR, PcompConstants.PCOMP_VERSION,
                                Constants.PARAMETER_EQUALS)%>' +pcompVersionId;
        }
    }
</script>