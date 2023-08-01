package com.datamanagerapi.datamanagerapi.services;

import com.datamanagerapi.datamanagerapi.CommonLogic.CommonApiLogic;
import com.datamanagerapi.datamanagerapi.DataAccessObjects.DataAccess;
import com.datamanagerapi.datamanagerapi.DataAccessObjects.SystemUserDetailsRepository;
import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Models.SystemUserDetails;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Requests.RegistrationRequest;
import com.datamanagerapi.datamanagerapi.Responses.LoginResponse;
import com.datamanagerapi.datamanagerapi.Responses.RegistrationResponse;
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
    CommonApiLogic commonApiLogic = new CommonApiLogic();
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

    public RegistrationResponse registerBankStaff(RegistrationRequest registrationRequest)
    {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        try
        {
           RegistrationResponse response = isValidReqistrationRequest(registrationRequest);
           if(response.getStatus().equals("SUCCESS"))
           {
               String hashedPass = HashUserPassword(registrationRequest.getPassword(),secretKey);

               registrationRequest.setPassword(hashedPass);
               String status = dataAccess.saveBankStaffInformation(registrationRequest);
               if(status==null || status.length()<1)
               {
                   registrationResponse.setStatus("FAILED");
                   registrationResponse.setMessage("Unable to save user details,please try again");
                   return registrationResponse;
               }
               if(status != null && status.equals("DUPLICATE EMAIL"))
               {
                   registrationResponse.setStatus("FAILED");
                   registrationResponse.setMessage("Duplicate,There is an account already attached to supplied email");
                   return registrationResponse;
               }
               if(status != null && status.equals("DUPLICATE USERNAME"))
               {
                   registrationResponse.setStatus("FAILED");
                   registrationResponse.setMessage("Duplicate,Username already taken,choose a different one");
                   return registrationResponse;
               }
               if(status != null && status.equals("SUCCESS"))
               {
                   registrationResponse.setStatus("SUCCESS");
                   registrationResponse.setMessage("User details have been saved successfully");
                   return registrationResponse;
               }
               return registrationResponse;
           }
           else
           {
               return response;
           }
        }
        catch(Exception exception)
        {
            log.info("Error on creating bank staff :".concat(exception.getMessage()));
            registrationResponse.setStatus("FAILED");
            registrationResponse.setMessage("An error ocurred on creating user,please try again");
            return registrationResponse;
        }
    }

    public RegistrationResponse isValidReqistrationRequest(RegistrationRequest registrationRequest)
    {
        RegistrationResponse registrationResponse = new RegistrationResponse();
        try
        {
            if(registrationRequest == null)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Registration request can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getEmail()==null || registrationRequest.getEmail().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Email field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getEmail()!=null )
            {
                if(commonApiLogic.validateEmail(registrationRequest.getEmail())==false)
                {

                    registrationResponse.setStatus("FAILED");
                    registrationResponse.setMessage("Invalid email supplied");
                    return registrationResponse;
                }
            }
            if(registrationRequest.getPassword()==null || registrationRequest.getPassword().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Password field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getUserName()==null || registrationRequest.getUserName().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("UserName field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getFirstName()==null || registrationRequest.getFirstName().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("First Name field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getLastName()==null || registrationRequest.getLastName().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Last Name field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getRoleCode()==null || registrationRequest.getRoleCode().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Role field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getCreatedBy()==null || registrationRequest.getCreatedBy().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("CreatedBy field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getBranchId()==null || registrationRequest.getBranchId().length()<1)
            {
                registrationResponse.setStatus("FAILED");
                registrationResponse.setMessage("Branch field can not be empty");
                return registrationResponse;
            }
            if(registrationRequest.getBranchId().length()>0)
            {
                boolean isValidInteger = commonApiLogic.isValidNumber(registrationRequest.getBranchId());
                if(isValidInteger== false)
                {
                    registrationResponse.setStatus("FAILED");
                    registrationResponse.setMessage("Invalid Branch Id supplied,should be integer");
                    return registrationResponse;
                }
            }
            if(registrationRequest.getRoleCode().length()>0)
            {
                boolean isValidInteger = commonApiLogic.isValidNumber(registrationRequest.getRoleCode());
                if(isValidInteger== false)
                {
                    registrationResponse.setStatus("FAILED");
                    registrationResponse.setMessage("Invalid Role Id supplied,should be integer");
                    return registrationResponse;
                }
            }
            if(registrationRequest.getCreatedBy().length()>0)
            {
                boolean isValidInteger = commonApiLogic.isValidNumber(registrationRequest.getCreatedBy());
                if(isValidInteger== false)
                {
                    registrationResponse.setStatus("FAILED");
                    registrationResponse.setMessage("Invalid CreatedBy Id supplied,should be integer");
                    return registrationResponse;
                }
            }
            registrationResponse.setStatus("SUCCESS");
            registrationResponse.setMessage("Request is valid");
            return registrationResponse;
        }
        catch (Exception exception)
        {
           log.info("Error on validating request Method isValidReqistrationRequest ".concat(exception.getMessage()));
            registrationResponse.setStatus("FAILED");
            registrationResponse.setMessage("An error ocurred on verifying request,please try again");
            return registrationResponse;
        }
    }
}
