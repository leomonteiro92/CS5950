<%@ page import="lineproject.Attendant" %>



<div class="fieldcontain ${hasErrors(bean: attendantInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="attendant.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${attendantInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: attendantInstance, field: 'window', 'error')} required">
	<label for="window">
		<g:message code="attendant.window.label" default="Window" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="window" required="" value="${attendantInstance?.window}"/>

</div>

