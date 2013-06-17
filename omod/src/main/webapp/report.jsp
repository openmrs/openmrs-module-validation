<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>
	<spring:message code="validation.report" />
</h2>

<p><a href="list.form">Back to list</a></p>

<%--<c:if test="${validationThread.alive}"><p><b>Still running...</b></p></c:if>
 
<p>Type: ${validationThread.type}<br/>
Total: ${validationThread.totalObjects}<br/>
Total processed: ${validationThread.totalObjects - validationThread.objectsLeftToProcess}<br/>
Errors: ${fn:length(validationThread.errors)}</p>

<table border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Error</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${validationThread.errors}" var="error">
			<tr>
				<td>${error.key.id}</td>
				<td>${error.value}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>--%>

<%@ include file="/WEB-INF/template/footer.jsp"%>