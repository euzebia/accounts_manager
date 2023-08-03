package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Models.Institution;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Requests.RegistrationRequest;

import java.util.List;

public interface DataAccess {
    Account getAccountDetails(LoginRequest loginRequest);
    String saveBankStaffInformation(RegistrationRequest registrationRequest);
    String saveBankCustomer(RegistrationRequest registrationRequest);
    List<Institution> getAllInstitutions();

}
