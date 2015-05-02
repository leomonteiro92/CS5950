<%@ page import="lineproject.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'uid', 'error')} required">
	<label for="uid">
		<g:message code="user.uid.label" default="Uid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="uid" type="number" value="${userInstance.uid}" required=""/>

</div>

