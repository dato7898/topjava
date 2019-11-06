package ru.javawebinar.topjava.service.jdbcservice;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbForJdbcProfileResolver;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(resolver = ActiveDbForJdbcProfileResolver.class)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
