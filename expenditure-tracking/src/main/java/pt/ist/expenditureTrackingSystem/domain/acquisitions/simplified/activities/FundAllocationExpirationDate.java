/*
 * @(#)FundAllocationExpirationDate.java
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

import org.fenixedu.bennu.WorkflowConfiguration;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.i18n.BundleUtil;
import org.joda.time.LocalDate;

import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.SigningState;
import pt.ist.expenditureTrackingSystem._development.Bundle;
import pt.ist.expenditureTrackingSystem.domain.ExpenditureTrackingSystem;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.AdvancePaymentDocument;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.RegularAcquisitionProcess;
import pt.ist.expenditureTrackingSystem.domain.util.DomainException;

/**
 * 
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
public class FundAllocationExpirationDate
        extends WorkflowActivity<RegularAcquisitionProcess, CreateAcquisitionPurchaseOrderDocumentInformation> {

    public static class FundAllocationNotAllowedException extends DomainException {

        public FundAllocationNotAllowedException() {
            super(Bundle.ACQUISITION, "acquisitionRequestItem.message.exception.fundAllocationNotAllowed");
        }
    }

    private void checkSupplierLimit(final RegularAcquisitionProcess process) {
        process.checkSupplierLimit();
    }

    @Override
    public boolean isActive(RegularAcquisitionProcess process, User user) {
        return isUserProcessOwner(process, user) && ExpenditureTrackingSystem.isAcquisitionCentralGroupMember(user)
                && process.getAcquisitionProcessState().isActive() && !process.isPendingFundAllocation()
                && !process.getAcquisitionRequest().hasAnyFundAllocationId()
                && process.getAcquisitionRequest().isSubmittedForFundsAllocationByAllResponsibles();
    }

    @Override
    protected void process(CreateAcquisitionPurchaseOrderDocumentInformation activityInformation) {
        RegularAcquisitionProcess process = activityInformation.getProcess();
        if (process.getAcquisitionRequest().isSubmittedForFundsAllocationByAllResponsibles()) {
            if (!process.getShouldSkipSupplierFundAllocation()) {
                checkSupplierLimit(process);
                LocalDate now = new LocalDate();
                process.setFundAllocationExpirationDate(now.plusDays(90));
            } else {
                process.skipFundAllocation();
            }
        }

        process.allocateFundsToSupplier();

        process.createFundAllocationRequest(false);

        if (ExpenditureTrackingSystem.getInstance().processesNeedToBeReverified()) {
            process.setProcessNeedsReverification(Boolean.TRUE);
        }

        AdvancePaymentDocument advancePaymentDocument = process.getAdvancePaymentDocument();
        if (advancePaymentDocument != null && WorkflowConfiguration.getConfiguration().smartsignerIntegration()
                && !advancePaymentDocument.isSigned()) {
            advancePaymentDocument.sendFileForSigning();
        }

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
    public ActivityInformation<RegularAcquisitionProcess> getActivityInformation(RegularAcquisitionProcess process) {
        return new CreateAcquisitionPurchaseOrderDocumentInformation(process, this);
    }

}
