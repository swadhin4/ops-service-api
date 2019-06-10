package com.ops.api.ds.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.StringUtils;

@PropertySource("classpath:ops_tenant_ds.properties")
@Configuration 
public class ConnectionManager implements EnvironmentAware {

	final static Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

	/**
	 * Holds DataSource object
	 */
	private static DataSource customerDatasource;

	private static ConnectionManager connectionManagerObj;

	private static String dbInstance;

	static Environment environment;

	public ConnectionManager() {

	}

	public DataSource getDataSource(String dbName) {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty("spring.ops.datasource2.url")+"/"+dbName);
		driverManagerDataSource.setUsername(environment.getProperty("spring.ops.datasource2.username"));
		driverManagerDataSource.setPassword(environment.getProperty("spring.ops.datasource2.password"));
		driverManagerDataSource.setDriverClassName(environment.getProperty("spring.ops.datasource2.driver-class-name"));
		return driverManagerDataSource;
	}

	/**
	 * Method to return the ConnectionManager instance.
	 * 
	 * @return ConnectionManager
	 */
	public static synchronized ConnectionManager getInstance(String userDB) {
		try {
			if (connectionManagerObj != null && !StringUtils.isEmpty(dbInstance)
					&& getDbInstance().equalsIgnoreCase(userDB)) {
				LOGGER.info("Connection already exisits for :" + userDB);
				return connectionManagerObj;
			} else {
				LOGGER.info("Creating new connection to --" + userDB);
				LOGGER.info("Connected to DB - " + userDB);
				setDbInstance(userDB);
				connectionManagerObj = new ConnectionManager();
				ConnectionManager.setCustomerDatasource(connectionManagerObj.getDataSource(userDB));

			}
		} catch (Exception e) {
			connectionManagerObj = null;
			e.printStackTrace();
		}
		return connectionManagerObj;

	}

	/**
	 * Method to connect to DB.
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		LOGGER.info("Inside  getConnection.");
		Connection connection = null;
		try {
			connection = customerDatasource.getConnection();
			LOGGER.info("Connection created to DB.");
		} catch (SQLException e) {
			LOGGER.info("SQLException while getting connection : ");
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.info("Exception while getting connection : ");
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("Exit getConnection.");
		return connection;
	}

	/**
	 * Method to close connection.
	 * 
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		LOGGER.info("Closing connection.");
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("SQLException while closing connection : ");
				LOGGER.error(e.getMessage());
			}
		}
	}

	public static String getDbInstance() {
		return dbInstance;
	}

	public static void setDbInstance(String dbInstance) {
		ConnectionManager.dbInstance = dbInstance;
	}

	public static DataSource getCustomerDatasource() {
		return customerDatasource;
	}

	public static void setCustomerDatasource(DataSource customerDatasource) {
		ConnectionManager.customerDatasource = customerDatasource;
	}

	public JdbcTemplate getJdbcTemplate() {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.customerDatasource);
			System.out.println("customerJdbcTemplate : " + jdbcTemplate.toString());
			return jdbcTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		ConnectionManager.environment=environment;
		
	}
	
	public static String getProperty(String key) {
	    return environment.getProperty(key);
	}

}