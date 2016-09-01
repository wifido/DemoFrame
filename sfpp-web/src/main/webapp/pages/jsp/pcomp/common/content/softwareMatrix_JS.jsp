<%@ page import="com.sf.sfpp.common.Constants" %>
<%@ page import="com.sf.sfpp.common.utils.StrUtils" %>
<%@ page import="com.sf.sfpp.pcomp.common.PcompConstants" %>
<%@ page import="com.sf.sfpp.web.common.PathConstants" %>
<%@ page import="com.sf.sfpp.web.common.utils.PathUtils" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script>
    function remove<%=PcompConstants.PCOMP_SOFTWARE%>(pcompSoftwareId, pcompSoftwareName) {
        if (confirm("你确定要删除" + '<%=PcompConstants.PCOMP_SOFTWARE%>' +"-" + pcompSoftwareName + "吗？")) {
            window.location.href = '<%=StrUtils.makeString(PathUtils.makePath(PathConstants.PCOMP_SOFTWARE_REMOVE_PATH),
                                Constants.PARAMETER_START_SEPARATOR, PcompConstants.PCOMP_SOFTWARE,
                                Constants.PARAMETER_EQUALS)%>' +pcompSoftwareId;
        }
    }
</script>