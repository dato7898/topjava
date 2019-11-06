package ru.javawebinar.topjava.service.jdbcservice;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbForJdbcProfileResolver;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(resolver = ActiveDbForJdbcProfileResolver.class)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
