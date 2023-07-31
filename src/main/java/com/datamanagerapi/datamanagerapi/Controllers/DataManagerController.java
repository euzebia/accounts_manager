package com.datamanagerapi.datamanagerapi.Controllers;

import com.datamanagerapi.datamanagerapi.CommonLogic.CommonApiLogic;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Responses.LoginResponse;
import com.datamanagerapi.datamanagerapi.services.DataManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value="/api/v1/accountsManagement")
public class DataManagerController {
    @Value("${secretKey}")
    public String secretKey;
    @Autowired
    DataManagerService dataManagerService;
    @PostMapping(value="/login")
    public LoginResponse loginUser (@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse = new LoginResponse();
        try
        {
          return  dataManagerService.verifyLoginCredentials(loginRequest);
        }
        catch (Exception exception)
        {
            log.info("Error encountered on logon ".concat(exception.getMessage()));
            loginResponse.setStatus("FAILED");
            loginResponse.setMessage("Failure ocurred on account logon");
            return loginResponse;
        }
    }
}
