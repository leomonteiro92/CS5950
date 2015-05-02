
<%@ page import="lineproject.LineEntry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'lineEntry.label', default: 'LineEntry')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-lineEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-lineEntry" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'lineEntry.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="isFinished" title="${message(code: 'lineEntry.isFinished.label', default: 'Is Finished')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'lineEntry.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="lineEntry.service.label" default="Service" /></th>
					
						<g:sortableColumn property="uid" title="${message(code: 'lineEntry.uid.label', default: 'Uid')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${lineEntryInstanceList}" status="i" var="lineEntryInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${lineEntryInstance.id}">${fieldValue(bean: lineEntryInstance, field: "dateCreated")}</g:link></td>
					
						<td><g:formatBoolean boolean="${lineEntryInstance.isFinished}" /></td>
					
						<td><g:formatDate date="${lineEntryInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: lineEntryInstance, field: "service")}</td>
					
						<td>${fieldValue(bean: lineEntryInstance, field: "uid")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${lineEntryInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
