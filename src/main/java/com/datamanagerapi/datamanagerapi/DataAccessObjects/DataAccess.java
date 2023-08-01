package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Requests.RegistrationRequest;

public interface DataAccess {
    Account getAccountDetails(LoginRequest loginRequest);
    String saveBankStaffInformation(RegistrationRequest registrationRequest);

}
