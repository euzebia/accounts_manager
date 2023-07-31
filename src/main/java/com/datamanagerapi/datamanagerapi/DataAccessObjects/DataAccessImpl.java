package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataAccessImpl {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Account getAccountDetails(LoginRequest loginRequest)
    {
        Account account = new Account();
        try
        {
            return jdbcTemplate.queryForObject("SELECT * FROM app_transactions WHERE debit_id = ? LIMIT 1",
                    new BeanPropertyRowMapper<Account>(Account.class),citrusId);
        }
        catch(Exception exception)
        {

        }
    }
}
