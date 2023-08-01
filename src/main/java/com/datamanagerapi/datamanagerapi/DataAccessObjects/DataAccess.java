package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;

public interface DataAccess {
    Account getAccountDetails(LoginRequest loginRequest);


}
