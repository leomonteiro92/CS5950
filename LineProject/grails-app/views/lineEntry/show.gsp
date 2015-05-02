
<%@ page import="lineproject.LineEntry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'lineEntry.label', default: 'LineEntry')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-lineEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-lineEntry" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list lineEntry">
			
				<g:if test="${lineEntryInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="lineEntry.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${lineEntryInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineEntryInstance?.isFinished}">
				<li class="fieldcontain">
					<span id="isFinished-label" class="property-label"><g:message code="lineEntry.isFinished.label" default="Is Finished" /></span>
					
						<span class="property-value" aria-labelledby="isFinished-label"><g:formatBoolean boolean="${lineEntryInstance?.isFinished}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineEntryInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="lineEntry.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${lineEntryInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineEntryInstance?.service}">
				<li class="fieldcontain">
					<span id="service-label" class="property-label"><g:message code="lineEntry.service.label" default="Service" /></span>
					
						<span class="property-value" aria-labelledby="service-label"><g:link controller="service" action="show" id="${lineEntryInstance?.service?.id}">${lineEntryInstance?.service?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${lineEntryInstance?.uid}">
				<li class="fieldcontain">
					<span id="uid-label" class="property-label"><g:message code="lineEntry.uid.label" default="Uid" /></span>
					
						<span class="property-value" aria-labelledby="uid-label"><g:fieldValue bean="${lineEntryInstance}" field="uid"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:lineEntryInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${lineEntryInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
