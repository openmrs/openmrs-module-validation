<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>
	<spring:message code="validation.title" />
</h2>

<ol>
	<c:forEach items="${validationThreads}" var="thread" varStatus="status">
		<li>${thread.type} - <c:if test="${thread.alive}">
				<b>(still running)</b>
			</c:if>${thread.totalObjects -
			thread.objectsLeftToProcess}/${thread.totalObjects}, errors:
			${fn:length(thread.errors)} - <a
			href="report.form?thread=${status.count-1}">show report</a> - <a
			href="remove.form?thread=${status.count-1}">remove report</a>
		</li>
	</c:forEach>
</ol>

<p><a href="remove_all.form">Remove all reports</a></p>

<form method="post" action="validate.form">
	<p>
		Enter type to validate: <input type="text" name="type"
			value="org.openmrs.Concept" /> [first: <input type="text" name="first" /> last: <input type="text" name="last" />] <input type="submit" />
	</p>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>