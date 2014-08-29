package com.app.spring.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Gareth
 *
 * http://krams915.blogspot.co.uk/2012/12/spring-and-thymeleaf-with-javaconfig_8540.html
 */
@Configuration
@ComponentScan(basePackages = {"com.app.spring.model", "com.app.spring.dao"})
@EnableTransactionManagement
@PropertySource({"classpath:sql.properties"})
public class DataConfig extends WebMvcConfigurerAdapter {

    @Autowired // Another example has this as @Resource but @PropertySource on Spring docs has as @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        try {
            org.apache.commons.dbcp.BasicDataSource ds = new org.apache.commons.dbcp.BasicDataSource();
            ds.setDriverClassName(env.getRequiredProperty("app.jdbc.driverClassName"));
            ds.setUrl(env.getRequiredProperty("app.jdbc.url"));
            ds.setUsername(env.getRequiredProperty("app.jdbc.username"));
            ds.setPassword(env.getRequiredProperty("app.jdbc.password"));
            // Below applies if using a connection pool data source.
            //ds.setAcquireIncrement(5);
            //ds.setIdleConnectionTestPeriod(60);
            //ds.setMaxPoolSize(100);
            //ds.setMaxStatements(50);
            //ds.setMinPoolSize(10);
            return ds;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();

        sf.setDataSource(dataSource());
        sf.setAnnotatedClasses(getAnnotatedClasses());
        sf.setHibernateProperties(hibernateProperties());

        return sf;
    }

    private Class<?> getAnnotatedClasses() {
        return com.app.spring.model.Customer.class;
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        props.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hbm2ddl.auto"));

        return props;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager tm = new HibernateTransactionManager();

        // http://stackoverflow.com/questions/20039333/how-to-spring-3-2-hibernate-4-on-javaconfig-correctly
        tm.setSessionFactory(sessionFactory().getObject());

        return tm;
    }
}
