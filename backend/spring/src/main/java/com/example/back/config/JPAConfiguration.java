package com.example.back.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.example.back.repository")
public class JPAConfiguration{
    


    @Primary  
    @Bean("mainDataSource")  
    @ConfigurationProperties(prefix = "spring.datasource")  
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                                .url("jdbc:mysql://localhost:3307/blogTest?useSSL=false&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul")
                                
                                .password("test")
                                .username("1234abcd")
                                .build(); 
    }  


    @Primary  
    @Bean(name = "entityManagerFactory")  
    public LocalContainerEntityManagerFactoryBean entityManager() {  
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();  
        em.setDataSource(dataSource());  
        em.setPackagesToScan(new String[]{"com.example.back.model"});  

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        //hibernateJpaVendorAdapter.setDatabase(MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);  

        return em;  
    }  

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

}