package com.okeydokey.repository;

import com.okeydokey.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    SimpleJdbcInsert insert;

    @PostConstruct
    public void init() {
        insert = new SimpleJdbcInsert(
                (JdbcTemplate) jdbcTemplate.getJdbcOperations())
                .withTableName("customers")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(rs.getInt("id")
            , rs.getString("first_name")
            , rs.getString("last_name"));

    public List<Customer> findAll() {
        String sql = "SELECT id, first_name, last_name FROM customers";

        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);

        return customers;
    }

    public Customer findOne(Integer customerId) {
        String sql = "SELECT id, first_name, last_name FROM customers WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", customerId);

        Customer result = jdbcTemplate.queryForObject(sql, param, customerRowMapper);

        return result;
    }

    public Customer save(Customer customer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(customer);

        if(customer.getId() == null) {
            Number key = insert.executeAndReturnKey(param);
            customer.setId(key.intValue());

            jdbcTemplate.update("INSERT INTO customers(first_name, last_name) "  +
                    "VALUES(:firstName, :lastName)", param);
        } else {
            jdbcTemplate.update("UPDATE customers SET first_name=:firstName, " +
                    "last_name=:lastName WHERE id=:id", param);
        }
        return customer;
    }

    public void delete(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update("DELETE FROM customers WHERE id=:id", param);
    }
}
