package com.datamanagerapi.datamanagerapi.services;

import com.datamanagerapi.datamanagerapi.DataAccessObjects.DataAccess;
import com.datamanagerapi.datamanagerapi.DataAccessObjects.SystemUserDetailsRepository;
import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Models.SystemUserDetails;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Responses.LoginResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class DataManagerService
{
    @Value("${secretKey}")
    public String secretKey;
    @Autowired
    DataAccess dataAccess;
    @Autowired
    SystemUserDetailsRepository systemUserDetailsRepository;
    public LoginResponse verifyLoginCredentials(LoginRequest loginRequest)
    {
        LoginResponse response = new LoginResponse();
        try
        {
            if(loginRequest==null)
            {
                response.setStatus("FAILED");
                response.setMessage("Request parameters can not be empty");
                return response;
            }
            if(loginRequest.getUserName()== null || loginRequest.getUserName().length()<1)
            {
                response.setStatus("FAILED");
                response.setMessage("The user name field can not be empty");
                return response;
            }
            if(loginRequest.getPassword()== null || loginRequest.getPassword().length()<1)
            {
                response.setStatus("FAILED");
                response.setMessage("The password field can not be empty");
                return response;
            }

            String hashPassword =HashUserPassword(loginRequest.getPassword(),secretKey );
            if(hashPassword!=null || hashPassword.length()>0)
            {
                //request is now valid,check if account with supplied username exists in the accounts table
                loginRequest.setPassword(hashPassword);
                Account accountDetails = dataAccess.getAccountDetails(loginRequest);
                if(accountDetails== null)
                {
                    response.setStatus("FAILED");
                    response.setMessage("Invalid username or password");
                    return response;
                }

                log.info("Reached here");

                // fetch other particulars attached to the account

                SystemUserDetails vendorDetails = systemUserDetailsRepository.GetSystemUserDetails(loginRequest.getUserName(), loginRequest.getPassword());
                if(vendorDetails != null)
                {
                    response.setAccountId(vendorDetails.getAccount_id()+"");
                    response.setEmailAddress(vendorDetails.getEmail_address());
                    response.setFirstName(vendorDetails.getFirst_name());
                    response.setLastName(vendorDetails.getLast_name());
                    response.setInstitutionName(vendorDetails.getInstitution_name());
                    response.setRoleName(vendorDetails.getRole_name());
                    response.setInstitutionType(vendorDetails.getInstitution_type());
                    response.setRoleCode(vendorDetails.getRole_code());
                    response.setUserName(vendorDetails.getUser_name());
                    response.setStatus("SUCCESS");
                    response.setMessage("Account exists");
                    return response;
                }

                response.setStatus("FAILED");
                response.setMessage("Invalid username or password");
                return response;
            }
            else
            {
                response.setStatus("FAILED");
                response.setMessage("Failure encountered on logon,try again");
                return response;

            }
        }
        catch(Exception exception)
        {
           log.info("Error encountered on logon ".concat(exception.getMessage()));
           response.setStatus("FAILED");
           response.setMessage("Failure encountered on logon,try again");
           return response;
        }
    }

    public String HashUserPassword(String stringToHash,String secretKey)
    {
        String result = "";
        try
        {
            final byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(stringToHash.getBytes(StandardCharsets.UTF_8));

            // Base64 encode it and return the string equivalent
            result = Base64.getEncoder().encodeToString(macData);
            log.info("Hashed password: "+result);
        }
        catch (Exception exception)
        {
            log.info("Error on hashing password: ".concat(exception.getMessage()));
        }
        return result;
    }
}
