/*
 * @(#)DashBoard.java
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
package pt.ist.expenditureTrackingSystem.domain;

import java.util.ArrayList;
import java.util.List;

import pt.ist.expenditureTrackingSystem.domain.organization.Person;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.Strings;

/**
 * 
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class DashBoard extends DashBoard_Base {

    public DashBoard(Person person) {
        super();
        setExpenditureTrackingSystem(ExpenditureTrackingSystem.getInstance());
        setPerson(person);
        reset();
    }

    @Service
    public void reset() {
        List<String> column1 = new ArrayList<String>();
        List<String> column2 = new ArrayList<String>();
        List<String> column3 = new ArrayList<String>();
        column1.add("widgetMyProcesses");
        column1.add("widgetMySearches");
        column1.add("widgetUnreadComments");
        column2.add("widgetPendingSimplified");
        column2.add("widgetPendingRefund");
        column3.add("widgetTakenProcesses");
        column3.add("widgetQuickView");
        column3.add("widgetActivateEmailNotification");
        setColumn1(new Strings(column1));
        setColumn2(new Strings(column2));
        setColumn3(new Strings(column3));
    }

    @Service
    public void edit(Strings column1, Strings column2, Strings column3) {
        setColumn1(column1);
        setColumn2(column2);
        setColumn3(column3);
    }

    @Service
    public static DashBoard newDashBoard(Person person) {
        return new DashBoard(person);
    }

    public void delete() {
        removePerson();
        removeExpenditureTrackingSystem();
        deleteDomainObject();
    }

    @Override
    public boolean isConnectedToCurrentHost() {
        return getExpenditureTrackingSystem() == ExpenditureTrackingSystem.getInstance();
    }

}