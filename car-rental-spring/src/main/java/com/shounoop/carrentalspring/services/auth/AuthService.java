package com.shounoop.carrentalspring.services.auth;

import com.shounoop.carrentalspring.dto.SignupRequest;
import com.shounoop.carrentalspring.dto.UserDto;

public interface AuthService {
    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);
     UserDto createAgentImmobilier(SignupRequest signupRequest);

    void initiatePasswordReset(String email);

    boolean resetPassword(String token, String newPassword);
}
