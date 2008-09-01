package pt.ist.expenditureTrackingSystem.domain.requests.activities;

import pt.ist.expenditureTrackingSystem.applicationTier.Authenticate.User;
import pt.ist.expenditureTrackingSystem.domain.dto.CreateRequestForProposalProcessBean;
import pt.ist.expenditureTrackingSystem.domain.requests.RequestForProposalProcess;
import pt.ist.expenditureTrackingSystem.domain.requests.RequestForProposalProcessState;
import pt.ist.expenditureTrackingSystem.domain.requests.RequestForProposalProcessStateType;

public class SubmitRequestForApproval extends GenericRequestForProposalProcessActivity {

    @Override
    protected boolean isAccessible(RequestForProposalProcess process) {
	User user = getUser();
	return user != null && process.isRequester(user.getPerson());
    }

    @Override
    protected boolean isAvailable(RequestForProposalProcess process) {
	return process.isProcessInState(RequestForProposalProcessStateType.IN_GENESIS);
    }

    @Override
    protected void process(RequestForProposalProcess process, Object... objects) {
	new RequestForProposalProcessState(process, RequestForProposalProcessStateType.SUBMITTED_FOR_APPROVAL);
    }

}
