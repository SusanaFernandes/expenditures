<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<%@page import="module.workingCapital.domain.WorkingCapitalSystem"%>
<%@page import="myorg.domain.VirtualHost"%>
<%@page import="myorg.domain.MyOrg"%>

<h2>
	<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration"/>
</h2>

<h3><bean:message key="link.topBar.configuration.all.virtual.hosts" bundle="WORKING_CAPITAL_RESOURCES"/></h3>

<%
	final WorkingCapitalSystem currentWorkingCapitalSystem = WorkingCapitalSystem.getInstanceForCurrentHost();
%>

<table class="tstyle2">
	<tr>
		<th>
			<bean:message key="link.topBar.configuration.virtual.hosts.title" bundle="WORKING_CAPITAL_RESOURCES"/>
		</th>
		<th>
			<bean:message key="link.topBar.configuration.virtual.hosts.system" bundle="WORKING_CAPITAL_RESOURCES"/>
		</th>
		<th>
			<bean:message key="link.topBar.configuration.virtual.hosts.unit" bundle="WORKING_CAPITAL_RESOURCES"/>
		</th>
		<th>
		</th>
	</tr>
	<bean:define id="currentVirtualHost" name="currentVirtualHost"/>
	<%
		for (final VirtualHost virtualHost : MyOrg.getInstance().getVirtualHostsSet()) {
	%>
			<tr>
				<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
					<%= virtualHost.getApplicationTitle() %>
					<br/>
					<%= virtualHost.getHostname() %>
				</td>
				<% 	final WorkingCapitalSystem workingCapitalSystem = virtualHost.getWorkingCapitalSystem();
					if (workingCapitalSystem != null) {
				%>
						<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
							<%= workingCapitalSystem.getExternalId() %>
						</td>
						<% if (workingCapitalSystem.hasManagementUnit()) { %>
							<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
								<%= workingCapitalSystem.getManagementUnit().getPresentationName() %>
							</td>
						<% }  else { %>
							<td style="background-color: #FF5555;">
								--
							</td>
						<% } %>
				<%
					} else {
				%>
						<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
							--
						</td>
						<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
							--
						</td>
				<%
					}
				%>
				<td <% if (currentVirtualHost == virtualHost) { %>style="background-color: #99FF66;"<% } %>>
					<%
						if (workingCapitalSystem != null && workingCapitalSystem != currentWorkingCapitalSystem) {
					%>
							<html:link action="<%= "/workingCapital.do?method=useSystem&amp;systemId=" + workingCapitalSystem.getExternalId() %>">
								<bean:message key="link.topBar.configuration.virtual.hosts.use.system" bundle="WORKING_CAPITAL_RESOURCES"/>
							</html:link>
					<%
						}
						if (virtualHost == currentVirtualHost) {
					%>
							<html:link action="/workingCapital.do?method=createNewSystem">
								<bean:message key="link.topBar.configuration.virtual.hosts.create.new.system" bundle="WORKING_CAPITAL_RESOURCES"/>
							</html:link>
					<%
						}
					%>
				</td>
			</tr>
	<%
		}
	%>
</table>

<p>
<h3><bean:message key="link.topBar.configuration.this.virtual.host" bundle="WORKING_CAPITAL_RESOURCES"/></h3>

<logic:present name="currentWorkingCapitalSystem">
	<table class="plist mtop05">
		<tr>
			<td>
			</td>
			<th>
				<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.unit"/>
			</th>
			<th>
				<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.accountabilityType"/>
			</th>
			<td>
			</td>
		</tr>
		<tr>
			<th>
				<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.management"/>
			</th>
			<td>
				<logic:present name="currentWorkingCapitalSystem" property="managementUnit">
					<bean:write name="currentWorkingCapitalSystem" property="managementUnit.presentationName"/>
				</logic:present>
			</td>
			<td>
				<logic:present name="currentWorkingCapitalSystem" property="managingAccountabilityType">
					<fr:view name="currentWorkingCapitalSystem" property="managingAccountabilityType.name"/>
				</logic:present>
			</td>
			<td>
				<html:link action="/workingCapital.do?method=configureManagementUnit">
					<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.management.configure"/>
				</html:link>
			</td>
		</tr>
	</table>
	
	<logic:present name="currentWorkingCapitalSystem" property="managementUnit">
		<br/>
		<h3>
			<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.management.members"/>
		</h3>
		<fr:view name="currentWorkingCapitalSystem" property="managementMembers" schema="module.organization.domain.Accountability.with.child.info">
			<fr:schema type="module.organization.domain.Accountability" bundle="ORGANIZATION_RESOURCES">
				<fr:slot name="child.partyName" key="label.name"/>
				<fr:slot name="child.user.username" key="label.username"/>
				<fr:slot name="beginDate" key="label.begin"/>
				<fr:slot name="endDate" key="label.end" />
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2 tdleft thleft"/>
			</fr:layout>
		</fr:view>
	</logic:present>
	
	<br/>
	
	<h3>
		<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.acquisition.classifications"/>
	</h3>
	<p>
		<html:link action="/workingCapital.do?method=prepareAddAcquisitionClassification">
			<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.acquisition.classifications.add"/>
		</html:link>
	</p>
	<logic:notPresent name="currentWorkingCapitalSystem" property="acquisitionClassifications">
		<bean:message bundle="WORKING_CAPITAL_RESOURCES" key="label.module.workingCapital.configuration.acquisition.classifications.none"/>
	</logic:notPresent>
	<logic:present name="currentWorkingCapitalSystem" property="acquisitionClassifications">
		<fr:view name="currentWorkingCapitalSystem" property="acquisitionClassifications">
			<fr:schema type="module.workingCapital.domain.AcquisitionClassification" bundle="WORKING_CAPITAL_RESOURCES">
				<fr:slot name="description" key="label.module.workingCapital.configuration.acquisition.classifications.description"/>
				<fr:slot name="economicClassification" key="label.module.workingCapital.configuration.acquisition.classifications.economicClassification"/>
				<fr:slot name="pocCode" key="label.module.workingCapital.configuration.acquisition.classifications.pocCode" />
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2 tdleft thleft"/>
	
				<fr:property name="link(delete)" value="/workingCapital.do?method=deleteAcquisitionClassification"/>
				<fr:property name="bundle(delete)" value="WORKING_CAPITAL_RESOURCES"/>
				<fr:property name="key(delete)" value="link.delete"/>
				<fr:property name="param(delete)" value="externalId/acquisitionClassificationOid"/>
				<fr:property name="order(delete)" value="1"/>
			</fr:layout>
		</fr:view>
	</logic:present>
</logic:present>
