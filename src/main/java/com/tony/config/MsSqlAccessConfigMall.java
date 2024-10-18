package com.tony.config;

import com.tony.constant.ServiceBeanConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.tony.service.impl","com.tony.repo"},
        entityManagerFactoryRef = ServiceBeanConstants.MSSQL_ENTITY_MGR_MALL,
        transactionManagerRef = ServiceBeanConstants.MSSQL_TRANC_MGR_MALL)
public class MsSqlAccessConfigMall {

    private static Logger logger = LoggerFactory.getLogger(MsSqlAccessConfigMall.class);
    @Primary
    @Bean(name = ServiceBeanConstants.MSSQL_TRANC_MGR_MALL)
    public JpaTransactionManager transactionManager(
            @Qualifier(ServiceBeanConstants.MSSQL_ENTITY_MGR_MALL) EntityManagerFactory entityManagerFactory)
    {
        logger.info("Initialize :" + ServiceBeanConstants.MSSQL_TRANC_MGR_MALL);
        return new JpaTransactionManager(entityManagerFactory);
    }
    @Primary
    @Bean(ServiceBeanConstants.MSSQL_DATASOURCE_MGR_MALL)
    public DataSource DataSourceMsSql(@Value("${mssql.datasource.jdbcUrl.mall}") String jdbcUrl,
                                      @Value("${mssql.datasource.username.mall}") String username,
                                      @Value("${mssql.datasource.password.mall}") String password,
                                      @Value("${mssql.datasource.poolsize.mall}") Integer poolsize){

        logger.info("Initialize :" + ServiceBeanConstants.MSSQL_DATASOURCE_MGR_MALL);
        logger.info("Jdbc URL connect to[" + jdbcUrl +"]");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(poolsize);

        return new HikariDataSource(config);

    }
    @Primary
    @Bean(name = ServiceBeanConstants.MSSQL_ENTITY_MGR_MALL)
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMsSql(
            @Qualifier(ServiceBeanConstants.MSSQL_DATASOURCE_MGR_MALL) DataSource dataSource)
    {
        logger.info("Initialize :" + ServiceBeanConstants.MSSQL_ENTITY_MGR_MALL);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("com.tony.model");
        factoryBean.setPersistenceUnitName("mssql-mall-model");
        factoryBean.setPersistenceUnitRootLocation("");
        return factoryBean;

    }



}
