package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Models.VendorDetails;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DataAccess {
    Account getAccountDetails(LoginRequest loginRequest);


}
