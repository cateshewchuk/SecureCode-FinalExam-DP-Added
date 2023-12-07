package com.securecode.DataAccess;


import org.springframework.jdbc.core.JdbcTemplate;

public class DBAccesser {
    
    public static JdbcTemplate jdbcTemplate;

    public DBAccesser( JdbcTemplate jdbcTemplate) {
        DBAccesser.jdbcTemplate = jdbcTemplate;
    }

}
