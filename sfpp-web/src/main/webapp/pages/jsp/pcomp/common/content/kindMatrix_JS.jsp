<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function remove<%=PcompConstants.PCOMP_KIND%>(pcompKindId, pcompKindName) {
        if (confirm("你确定要删除" + '<%=PcompConstants.PCOMP_KIND%>' +"-" + pcompKindName + "吗？")) {
            window.location.href = '<%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_KIND_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR, PcompConstants.PCOMP_KIND,
                                Constants.PARAMETER_EQUALS)%>' +pcompKindId;
        }
    }
</script>