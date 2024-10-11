package com.tony.config;

import com.tony.constant.ServiceBeanConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"xxx.zzz.aaa.qqq.service"},
        entityManagerFactoryRef = ServiceBeanConstants.MSSQL_ENTITY_MGR_XXX,
        transactionManagerRef = ServiceBeanConstants.MSSQL_TRANC_MGR_XXX)
public class MsSqlAccessConfigXxx {

    private static Logger logger = LoggerFactory.getLogger(MsSqlAccessConfigXxx.class);

    @Bean(name = ServiceBeanConstants.MSSQL_TRANC_MGR_XXX)
    public JpaTransactionManager transactionManager(
            @Qualifier(ServiceBeanConstants.MSSQL_ENTITY_MGR_XXX)EntityManagerFactory entityManagerFactory)
    {
        logger.info("Initialize :" + ServiceBeanConstants.MSSQL_TRANC_MGR_XXX);
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "qqq")
    public DataSource DataSourceMsSql(@Value() String jdbcUrl,
                                      @Value() String username,
                                         @Value() String password,
                                                  @Value() Integer poolsize,
                                                  @Value() String connectionTestQuery){
        logger.info("Initialize :" + "qqq");
        logger.info("Jdbc URL connect to[" + jdbcUrl +"]");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(poolsize);
        config.setConnectionTestQuery(connectionTestQuery);

        return new HikariDataSource(config);

    }

    @Bean(name = ServiceBeanConstants.MSSQL_ENTITY_MGR_XXX)
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMsSql(
            @Qualifier(ServiceBeanConstants.MSSQL_DATASOURCE_MGR_XXX) DataSource dataSource)
    {
        logger.info("Initialize :" + ServiceBeanConstants.MSSQL_ENTITY_MGR_XXX);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("xxx.sss.aaa.model");
        factoryBean.setPersistenceUnitName("mssql-xxx-model");
        factoryBean.setPersistenceUnitRootLocation("");
        return factoryBean;

    }



}
