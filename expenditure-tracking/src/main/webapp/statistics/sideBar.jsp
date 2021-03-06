<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<ul>
	<li>
		<html:link action="/statistics.do?method=showSimplifiedProcessStatistics">
			<span><bean:message key="label.statistics.process.simplified" bundle="STATISTICS_RESOURCES"/></span>
		</html:link>
		<span class="bar">|</span>
	</li>
	<li>
		<html:link action="/statistics.do?method=showRefundProcessStatistics">
			<span><bean:message key="label.statistics.process.refund" bundle="STATISTICS_RESOURCES"/></span>
		</html:link>
	</li>
</ul>
