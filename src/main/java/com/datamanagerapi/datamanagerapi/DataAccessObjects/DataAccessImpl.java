package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Models.Institution;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import com.datamanagerapi.datamanagerapi.Requests.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DataAccessImpl implements  DataAccess {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Account getAccountDetails(LoginRequest loginRequest)
    {
        try
        {
            return jdbcTemplate.queryForObject("SELECT * FROM account WHERE user_name = ? and password=? LIMIT 1",
                    new BeanPropertyRowMapper<Account>(Account.class),loginRequest.getUserName(),loginRequest.getPassword());
        }
        catch(EmptyResultDataAccessException exception)
        {
            log.info("Error on account details retrieval: "+ exception.getMessage());
            return null;
        }
    }

    public List<Institution> getAllInstitutions()
    {
        List<Institution> institutionList = new ArrayList<>();
        try
        {
            String sql ="SELECT * from institution";
            institutionList= jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Institution.class));
            return institutionList;
        }
        catch (Exception exception)
        {
            log.info("Failed to fetch institutions: ".concat(exception.getMessage()));
           return null;
        }
    }
    public String saveBankStaffInformation(RegistrationRequest registrationRequest)
    {

        String status="";
            try(Connection connection =jdbcTemplate.getDataSource().getConnection())
            {
                try(CallableStatement saveBankUserDetails=connection.prepareCall("{ ? = call saveBankStaffDetails(?,?,?,?,?,?,?,?)}")) {

                    saveBankUserDetails.registerOutParameter(1, Types.VARCHAR);
                    saveBankUserDetails.setString(2, registrationRequest.getFirstName());
                    saveBankUserDetails.setString(3, registrationRequest.getLastName());
                    saveBankUserDetails.setString(4, registrationRequest.getEmail());
                    saveBankUserDetails.setString(5, registrationRequest.getUserName());
                    saveBankUserDetails.setString(6, registrationRequest.getPassword());
                    saveBankUserDetails.setInt(7, Integer.parseInt(registrationRequest.getRoleCode()));
                    saveBankUserDetails.setInt(8, Integer.parseInt(registrationRequest.getBranchId()));
                    saveBankUserDetails.setInt(9, Integer.parseInt(registrationRequest.getCreatedBy()));

                    saveBankUserDetails.execute();
                    status = saveBankUserDetails.getString(1);
                }
                catch (Exception exception)
                {
                    log.info("SQL Error on Method saveBankStaffInformation 1 ".concat(exception.getMessage()));
                    return status;
                }
            }
            catch (Exception error)
            {
                log.info("SQL Error on Method saveBankStaffInformation 2 ".concat(error.getMessage()));
                return  status;
            }
            return  status;
    }

    public String saveBankCustomer(RegistrationRequest registrationRequest)
    {
        String status="";
        try(Connection connection =jdbcTemplate.getDataSource().getConnection())
        {
            try(CallableStatement saveBankCustomer=connection.prepareCall("{ ? = call saveCustomerInfo(?,?,?,?,?,?,?,?)}")) {

                saveBankCustomer.registerOutParameter(1, Types.VARCHAR);
                saveBankCustomer.setString(2, registrationRequest.getFirstName());
                saveBankCustomer.setString(3, registrationRequest.getLastName());
                saveBankCustomer.setString(4, registrationRequest.getEmail());
                saveBankCustomer.setString(5, registrationRequest.getUserName());
                saveBankCustomer.setString(6, registrationRequest.getPassword());
                saveBankCustomer.setInt(7, Integer.parseInt(registrationRequest.getRoleCode()));
                saveBankCustomer.setInt(8, Integer.parseInt(registrationRequest.getBranchId()));
                saveBankCustomer.setInt(9, Integer.parseInt(registrationRequest.getCreatedBy()));

                saveBankCustomer.execute();
                status = saveBankCustomer.getString(1);
            }
            catch (Exception exception)
            {
                log.info("SQL Error on Method saveBankCustomerInformation 1 ".concat(exception.getMessage()));
                return status;
            }
        }
        catch (Exception error)
        {
            log.info("SQL Error on Method saveBankCustomerInformation 2 ".concat(error.getMessage()));
            return  status;
        }
        return  status;
    }
}

