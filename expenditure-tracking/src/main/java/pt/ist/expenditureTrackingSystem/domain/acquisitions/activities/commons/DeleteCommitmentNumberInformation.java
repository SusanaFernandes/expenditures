/*
 * @(#)DeleteCommitmentNumberInformation.java
 *
 * Copyright 2012 Instituto Superior Tecnico
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
import module.workflow.domain.WorkflowProcess;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.Financer;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.RegularAcquisitionProcess;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class DeleteCommitmentNumberInformation extends ActivityInformation<RegularAcquisitionProcess> {

	private Financer financer;

	public DeleteCommitmentNumberInformation(final RegularAcquisitionProcess process,
			final WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation> activity) {
		super(process, activity);
	}

	@Override
	public boolean hasAllneededInfo() {
		return super.hasAllneededInfo() && financer != null;
	}

	public Financer getFinancer() {
		return financer;
	}

	public void setFinancer(Financer financer) {
		this.financer = financer;
	}

}
