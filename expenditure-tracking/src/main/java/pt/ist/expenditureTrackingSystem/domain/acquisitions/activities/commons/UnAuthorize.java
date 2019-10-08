/*
 * @(#)UnAuthorize.java
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
package pt.ist.expenditureTrackingSystem.domain.acquisitions.activities.commons;

import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.i18n.BundleUtil;

import pt.ist.expenditureTrackingSystem.domain.acquisitions.PaymentProcess;
import pt.ist.expenditureTrackingSystem.domain.organization.Person;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class UnAuthorize<P extends PaymentProcess> extends WorkflowActivity<P, ActivityInformation<P>> {

    @Override
    public boolean isActive(P process, User user) {
        Person person = user.getExpenditurePerson();

        return isUserProcessOwner(process, user)
                && (process.isResponsibleForUnit(person, process.getRequest().getTotalValueWithoutVat()) || process
                        .isAccountingEmployee(person)) && process.getRequest().hasBeenAuthorizedBy(person)
                && (process.isInAllocatedToUnitState() || process.isAuthorized());
    }

    @Override
    protected void process(ActivityInformation<P> activityInformation) {
        P process = activityInformation.getProcess();
        process.getRequest().unathorize();
        process.allocateFundsToUnit();
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
    public boolean isUserAwarenessNeeded(P process, User user) {
        return false;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean isVisible(final P process, final User user) {
        return super.isVisible(process, user) && user != null && process != null
                && (process.isRefundProcess() || process.isAccountingEmployee(user.getExpenditurePerson()));
    }

}
