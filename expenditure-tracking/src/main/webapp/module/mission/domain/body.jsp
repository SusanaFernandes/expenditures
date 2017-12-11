<%@page import="pt.ist.expenditureTrackingSystem.ExpenditureExtensions"%>
<%@page import="module.workflow.domain.WorkflowProcess"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<jsp:include page="summary.jsp"/>

<jsp:include page="viewAssociatedMissionProcesses.jsp"/>

<jsp:include page="viewPaymentProcesses.jsp"/>

<jsp:include page="processMessages.jsp"/>

<jsp:include page="processStateView.jsp"/>

<jsp:include page="listFinancers.jsp"/>

<%
    for (final String jspFile : WorkflowProcess.getHooksFor(ExpenditureExtensions.VIEW_MISSION_PROCESS_HOOK)) {
%>
        <jsp:include page="<%= jspFile %>"/>
<%
    }
%>

<jsp:include page="listParticipants.jsp"/>

<jsp:include page="listMissionItems.jsp"/>
