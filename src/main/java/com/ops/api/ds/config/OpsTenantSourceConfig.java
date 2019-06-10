package com.ops.api.ds.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration 
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class}) 
@PropertySource("classpath:ops_tenant_ds.properties")
public class OpsTenantSourceConfig{
	
	@Autowired
	Environment environment;
	
	@ConfigurationProperties(prefix = "spring.ops") 
	@Bean(name = "opsTenantDataSource") 
	@Primary
    public DataSource  dataSource() { 
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty("spring.ops.datasource.url"));
		driverManagerDataSource.setUsername(environment.getProperty("spring.ops.datasource.username"));
		driverManagerDataSource.setPassword(environment.getProperty("spring.ops.datasource.password"));
		driverManagerDataSource.setDriverClassName(environment.getProperty("spring.ops.datasource.driver-class-name"));	
		return driverManagerDataSource; 
    } 
 
    
    @Autowired
    @Qualifier("opsTenantDataSource")
	DataSource opsTenantDataSource;
    
    @Bean(name = "templateOpsTenantSQL") 
    public JdbcTemplate getJdbcTemplate() {
    	try {
    		System.out.println("opsTenantDataSource :: "+opsTenantDataSource.toString());
        	JdbcTemplate  jdbcTemplate = new JdbcTemplate(opsTenantDataSource);
        	System.out.println("templateOpsTenantSQL : "+jdbcTemplate.toString());
            return jdbcTemplate; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    } 
}
