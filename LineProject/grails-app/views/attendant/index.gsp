
<%@ page import="lineproject.Attendant" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'attendant.label', default: 'Attendant')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-attendant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-attendant" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'attendant.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="window" title="${message(code: 'attendant.window.label', default: 'Window')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${attendantInstanceList}" status="i" var="attendantInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${attendantInstance.id}">${fieldValue(bean: attendantInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: attendantInstance, field: "window")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${attendantInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
