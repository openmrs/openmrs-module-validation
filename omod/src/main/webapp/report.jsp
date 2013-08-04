<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>
	<spring:message code="validation.report" />
</h2>
<script type="text/javascript" src=../../scripts/jquery/jquery.min.js></script>
<script type="text/javascript" src=../../scripts/jquery-ui/js/jquery-ui.custom.min.js></script>

<p><a href="list.form">Back to list</a></p>

<body>

<div>
    <table>
        <tr>
            <td colspan="3">
                <input type="checkbox" id="all_data_cbs" value=""><spring:message code="validation.all.data"/>
            </td>
        </tr>
        <tr>
            <c:forEach items="${allErrors}" var="error">
               <td>${error.key}</td>
               <td>${error.value}</td>
            </c:forEach>
        </tr>
    </table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>