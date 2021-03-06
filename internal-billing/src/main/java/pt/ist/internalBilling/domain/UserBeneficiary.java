package pt.ist.internalBilling.domain;

import org.fenixedu.bennu.core.domain.User;

public class UserBeneficiary extends UserBeneficiary_Base {
    
    private UserBeneficiary(final User user) {
        super();
        setUser(user);
    }

    public static UserBeneficiary beneficiaryFor(final User user) {
        final UserBeneficiary beneficiary = user.getUserBeneficiary();
        return beneficiary == null ? new UserBeneficiary(user) : beneficiary;
    }

    @Override
    public String getPresentationName() {
        final User user = getUser();
        return user.getDisplayName() + "(" + user.getUsername() + ")";
    }

}
