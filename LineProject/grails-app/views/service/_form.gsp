<%@ page import="lineproject.Service" %>



<div class="fieldcontain ${hasErrors(bean: serviceInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="service.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${serviceInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: serviceInstance, field: 'averageWaitingTime', 'error')} required">
	<label for="averageWaitingTime">
		<g:message code="service.averageWaitingTime.label" default="Average Waiting Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="averageWaitingTime" type="number" value="${serviceInstance.averageWaitingTime}" required=""/>

</div>

