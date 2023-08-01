package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Account;
import com.datamanagerapi.datamanagerapi.Requests.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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


}
