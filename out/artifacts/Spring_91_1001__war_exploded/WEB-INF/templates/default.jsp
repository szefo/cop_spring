<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/script/jquery.js"></script>
    <title><tiles:insertAttribute name="title"/></title>
    <tiles:insertAttribute name="include"/>
</head>
<body>
<div class="header">
    <tiles:insertAttribute name="header"/>
</div>
<div class="content">
    <tiles:insertAttribute name="content"/>
</div>
<hr/>
<div class="footer">
    <tiles:insertAttribute name="footer"/>
</div>

</body>
</html>
