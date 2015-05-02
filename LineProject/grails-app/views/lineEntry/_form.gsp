<%@ page import="lineproject.LineEntry" %>



<div class="fieldcontain ${hasErrors(bean: lineEntryInstance, field: 'isFinished', 'error')} ">
	<label for="isFinished">
		<g:message code="lineEntry.isFinished.label" default="Is Finished" />
		
	</label>
	<g:checkBox name="isFinished" value="${lineEntryInstance?.isFinished}" />

</div>

<div class="fieldcontain ${hasErrors(bean: lineEntryInstance, field: 'service', 'error')} required">
	<label for="service">
		<g:message code="lineEntry.service.label" default="Service" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="service" name="service.id" from="${lineproject.Service.list()}" optionKey="id" required="" value="${lineEntryInstance?.service?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: lineEntryInstance, field: 'uid', 'error')} required">
	<label for="uid">
		<g:message code="lineEntry.uid.label" default="Uid" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="uid" required="" value="${lineEntryInstance?.uid}"/>

</div>

