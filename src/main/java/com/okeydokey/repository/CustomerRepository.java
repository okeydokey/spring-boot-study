package com.okeydokey.repository;

import com.okeydokey.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class CustomerRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<Customer> findAll() {
        String sql = "SELECT id, first_name, last_name FROM customers";

        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(rs.getInt("id")
                , rs.getString("first_name")
                , rs.getString("last_name"))
        );

        return customers;
    }

    public Customer findOne(Integer customerId) {
        String sql = "SELECT id, first_name, last_name FROM customers WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", customerId);

        Customer result = jdbcTemplate.queryForObject(sql, param
                , (rs, rowNum) -> new Customer(rs.getInt("id")
                        , rs.getString("first_name")
                        , rs.getString("last_name"))
        );

        return result;
    }

    public Customer save(Customer customer) {
        // TODO customer save
        return new Customer();
    }

    public void delete(Integer customerId) {
        // TODO customer delete
    }
}
