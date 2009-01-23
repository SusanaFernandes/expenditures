<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<h2><bean:message
	key="title.refundProcess.create"
	bundle="ACQUISITION_RESOURCES" /></h2>

<p class="mtop1 mbottom05"><strong><bean:message key="label.refund" bundle="EXPENDITURE_RESOURCES"/></strong></p>

<bean:define id="selection" value="internalPerson"/>

<logic:equal name="bean" property="externalPerson" value="true"> 
	<bean:define id="selection" value="externalPerson"/>
</logic:equal>

<fr:form action="/acquisitionRefundProcess.do?method=createRefundProcess">
	<fr:edit id="createRefundProcess" name="bean" schema='<%= "createRefundProcess." + selection %>'>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
		<fr:destination name="postBack" path="/acquisitionRefundProcess.do?method=createRefundProcessPostBack"/>
		<fr:destination name="invalid" path="/acquisitionRefundProcess.do?method=prepareCreateRefundProcess"/>
	</fr:edit>
	<html:submit styleClass="inputbutton"><bean:message key="renderers.form.submit.name" bundle="RENDERER_RESOURCES"/></html:submit>
</fr:form>