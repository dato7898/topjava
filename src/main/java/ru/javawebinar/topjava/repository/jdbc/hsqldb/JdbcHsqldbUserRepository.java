package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.postgres.JdbcUserRepository;

@Repository
public class JdbcHsqldbUserRepository extends JdbcUserRepository {
    public JdbcHsqldbUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
}
