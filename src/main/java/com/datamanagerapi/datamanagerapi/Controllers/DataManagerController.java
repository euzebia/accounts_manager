package com.datamanagerapi.datamanagerapi.Controllers;

import com.datamanagerapi.datamanagerapi.DataAccessObjects.InstitutionRepository;
import com.datamanagerapi.datamanagerapi.Models.Institution;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Requests.RegistrationRequest;
import com.datamanagerapi.datamanagerapi.Responses.LoginResponse;
import com.datamanagerapi.datamanagerapi.Responses.RegistrationResponse;
import com.datamanagerapi.datamanagerapi.services.DataManagerService;
import com.datamanagerapi.datamanagerapi.services.OnlineEmailValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.Cacheable;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value="/api/v1/accountsManagement")
public class DataManagerController {

    @Value("${secretKey}")
    public String secretKey;
    @Autowired
    DataManagerService dataManagerService;
    @Autowired
    OnlineEmailValidationService onlineEmailValidationService;
    @Autowired
    InstitutionRepository institutionRepository;
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
            loginResponse.setMessage("Failure occurred on account logon");
            return loginResponse;
        }
    }

    @PostMapping(value="/saveBankStaffDetails")
    public RegistrationResponse createBankStaff(@RequestBody RegistrationRequest registrationRequest)
    {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        try
        {
            return  dataManagerService.registerBankStaff(registrationRequest);
        }
        catch (Exception exception)
        {
            log.info("Error encountered on saving bank user info ".concat(exception.getMessage()));
            registrationResponse.setStatus("FAILED");
            registrationResponse.setMessage("Failure occurred on saving bank staff details");
            return registrationResponse;
        }
    }
    @PostMapping(value="/saveBankCustomer")
    public RegistrationResponse createBankCustomer(@RequestBody RegistrationRequest registrationRequest)
    {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        try
        {
            return  dataManagerService.saveBankCustomer(registrationRequest);
        }
        catch (Exception exception)
        {
            log.info("Error encountered on saveBankCustomer method ".concat(exception.getMessage()));
            registrationResponse.setStatus("FAILED");
            registrationResponse.setMessage("Failure occurred on saving bank customer details");
            return registrationResponse;
        }
    }

    @GetMapping(value = "/allInstitutions")
    @Cacheable(value="institution-cache")
    public List<Institution> retrieveAllInstitutions()
    {
        return institutionRepository.findAll();
    }

    @GetMapping(value = "/getInstitutionById/{id}")
    @Cacheable(value="institution-cache",key="#id")
    public Optional<Institution> getInstitutionById(@PathVariable(value="id") int id)
    {
        return institutionRepository.findById(id);
    }

}
