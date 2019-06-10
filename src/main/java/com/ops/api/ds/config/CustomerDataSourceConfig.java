package com.ops.api.ds.config;

public class CustomerDataSourceConfig {

/*	@Autowired
	Environment environment;

	@ConfigurationProperties(prefix = "spring.ops")
	@Bean(name = "opsCustomerDataSource")
	public DataSource dataSource(String dbName) {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty("spring.ops.datasource2.url/" + dbName));
		driverManagerDataSource.setUsername(environment.getProperty("spring.ops.datasource2.username"));
		driverManagerDataSource.setPassword(environment.getProperty("spring.ops.datasource2.password"));
		driverManagerDataSource.setDriverClassName(environment.getProperty("spring.ops.datasource2.driver-class-name"));
		return driverManagerDataSource;
	}

	@Autowired
	@Qualifier("opsCustomerDataSource")
	DataSource opsCustomerDataSource;

	@Bean(name = "customerJdbcTemplate")
	public JdbcTemplate getJdbcTemplate(String dbName) {
		try {
			System.out.println("opsCustomerDataSource :: " + opsCustomerDataSource.getConnection().getSchema());
			JdbcTemplate jdbcTemplate = new JdbcTemplate(opsCustomerDataSource);
			System.out.println("customerJdbcTemplate : " + jdbcTemplate.toString());
			return jdbcTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}*/
}
