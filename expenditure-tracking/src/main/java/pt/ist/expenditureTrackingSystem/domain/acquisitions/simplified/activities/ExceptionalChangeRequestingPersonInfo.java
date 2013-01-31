/*
 * @(#)ExceptionalChangeRequestingPersonInfo.java
 *
 * Copyright 2011 Instituto Superior Tecnico
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

import java.io.Serializable;

import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.expenditureTrackingSystem.domain.acquisitions.RegularAcquisitionProcess;

/**
 * 
 * @author João Neves
 * 
 */
public class ExceptionalChangeRequestingPersonInfo extends ActivityInformation<RegularAcquisitionProcess> implements Serializable {

	private Person requester;
	private String comment;

	public ExceptionalChangeRequestingPersonInfo(final RegularAcquisitionProcess process,
			final WorkflowActivity<RegularAcquisitionProcess, ? extends ActivityInformation<RegularAcquisitionProcess>> activity) {
		super(process, activity);
	}

	@Override
	public boolean hasAllneededInfo() {
		return isForwardedFromInput() && getRequester() != null && getComment() != null;
	}

	public void setRequester(Person requester) {
		this.requester = requester;
	}

	public Person getRequester() {
		return requester;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

}
