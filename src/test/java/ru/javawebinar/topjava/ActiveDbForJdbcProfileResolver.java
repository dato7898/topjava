package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.JDBC_HSQLDB;
import static ru.javawebinar.topjava.Profiles.JDBC_POSTGRES;

public class ActiveDbForJdbcProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.HSQL_DB.equals(Profiles.getActiveDbProfile()) ? JDBC_HSQLDB : JDBC_POSTGRES};
    }
}
