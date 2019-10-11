/*
 * @(#)SubmitForFundAllocation.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Nuno Ochoa, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Expenditure Tracking Module.
 *
 *   The Expenditure Tracking Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Expenditure Tracking Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Expenditure Tracking Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package pt.ist.expenditureTrackingSystem.domain.acquisitions.simplified.activities;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.fenixedu.bennu.core.security.Authenticate;

import com.google.common.base.Strings;

import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.expenditureTrackingSystem.domain.ExpenditureTrackingSystem;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.AcquisitionApprovalTerm;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.RegularAcquisitionProcess;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.RequestItem;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.UnitItem;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.simplified.SimplifiedProcedureProcess;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.simplified.SimplifiedProcedureProcess.ProcessClassification;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.expenditureTrackingSystem.domain.organization.Unit;

/**
 * 
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
public class SubmitForFundAllocation
        extends WorkflowActivity<RegularAcquisitionProcess, SubmitForFundAllocationActivityInformation> {

    @Override
    public boolean isActive(RegularAcquisitionProcess process, User user) {
        Person person = user.getExpenditurePerson();
        return isUserProcessOwner(process, user) && process.isPendingApproval() && process.isResponsibleForUnit(person)
                && !process.getAcquisitionRequest().hasBeenApprovedBy(person);
    }

    @Override
    public ActivityInformation<RegularAcquisitionProcess> getActivityInformation(RegularAcquisitionProcess process) {
        return new SubmitForFundAllocationActivityInformation(process, this);
    }

    @Override
    protected void process(SubmitForFundAllocationActivityInformation activityInformation) {
        RegularAcquisitionProcess process = activityInformation.getProcess();
        if (process instanceof SimplifiedProcedureProcess
                && (ExpenditureTrackingSystem.getInstance().getApprovalTextForRapidAcquisitions() != null
                        && !ExpenditureTrackingSystem.getInstance().getApprovalTextForRapidAcquisitions().isEmpty())) {
            new AcquisitionApprovalTerm(process, Authenticate.getUser().getExpenditurePerson());
        }
        process.getAcquisitionRequest().approve(Authenticate.getUser().getExpenditurePerson());
    }

    @Override
    public String getLocalizedName() {
        return BundleUtil.getString(getUsedBundle(), "label." + getClass().getName());
    }

    @Override
    public String getUsedBundle() {
        return "resources/AcquisitionResources";
    }

    @Override
    public boolean isUserAwarenessNeeded(final RegularAcquisitionProcess process, final User user) {
        final Person person = user.getExpenditurePerson();
        if (process.isPendingApproval() && person.hasAnyValidAuthorization()) {
            for (final RequestItem requestItem : process.getRequest().getRequestItemsSet()) {
                for (final UnitItem unitItem : requestItem.getUnitItemsSet()) {
                    final Unit unit = unitItem.getUnit();
                    if (!unitItem.isApproved() && unit.isDirectResponsible(person)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
        return false;
    }

}
