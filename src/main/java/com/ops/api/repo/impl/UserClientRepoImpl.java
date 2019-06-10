package com.ops.api.repo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ops.api.data.model.LoginUser;
import com.ops.api.data.model.User;
import com.ops.api.exception.OpsDatabaseException;
import com.ops.api.query.contants.QueryConstants;
import com.ops.api.repo.UserClientRepo;

@Repository
public class UserClientRepoImpl implements UserClientRepo  {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserClientRepoImpl.class);
	
	@Autowired
	@Qualifier("templateOpsTenantSQL")
	private JdbcTemplate templateOpsTenantSQL;
	
	  
    @Autowired
    @Qualifier("opsTenantDataSource")
	DataSource opsTenantDataSource;

	@Override
	public User validateClientCredentials(String userName) {
		LOGGER.info("Inside UserClientRepoImpl... validateClientCredentials");
		User userClientDetail = null;
		try{
			userClientDetail= templateOpsTenantSQL.query(QueryConstants.USER_CLIENT_DETAILS_QUERY, new Object[]{userName,"CUSTOMER"},
				new ResultSetExtractor<User>() {
			@Override
			public User  extractData(ResultSet rs) throws SQLException, DataAccessException {
				User userClientDetail = new  User();
				if(rs.next()){
					userClientDetail.setUserId(rs.getLong("user_id"));
					userClientDetail.setClientId(rs.getString("client_id"));
					userClientDetail.setClientSecret(rs.getString("client_secret"));
					userClientDetail.setDbName(rs.getString("db_name"));
					userClientDetail.setUsername(rs.getString("email_id"));
					userClientDetail.setFirstName(rs.getString("first_name"));
					userClientDetail.setLastName(rs.getString("last_name"));
					userClientDetail.setPhone(rs.getLong("phone"));
					userClientDetail.setPassword(rs.getString("password"));
					userClientDetail.setRoleId(rs.getLong("role_id"));
					userClientDetail.setRoleDesc(rs.getString("role_desc"));
					userClientDetail.setSysPassword(rs.getString("sys_password"));
				}
				return userClientDetail;
			}
	  });
		}catch(Exception e){
			e.printStackTrace();
			throw new OpsDatabaseException("Unable to fetch the client credentials");
		}
		
		LOGGER.info("exit UserClientRepoImpl... validateClientCredentials");
		return userClientDetail;
	}

	@Override
	public User validateUserDevice(String deviceId)  {
		LOGGER.info("Inside UserClientRepoImpl... validateUserDevice");
		User userClientDetail=null;
		try{
			userClientDetail = templateOpsTenantSQL.query(QueryConstants.USER_APP_DEVICE_QUERY, new Object[]{deviceId},
				new ResultSetExtractor<User>() {
			@Override
			public User  extractData(ResultSet rs) throws SQLException, DataAccessException {
				User userClientDetail = new  User();
				if(rs.next()){
					LOGGER.info("Retrieved userClientDetail record : ");
					userClientDetail.setEmail(rs.getString("user_email"));
					userClientDetail.setDbName(rs.getString("db_name"));
					userClientDetail.setClientId(rs.getString("client_id"));
					userClientDetail.setClientSecret(rs.getString("client_secret"));
					
				}
				return userClientDetail;
			}
	      });
					
		}catch(Exception e){
			e.printStackTrace();
			throw new OpsDatabaseException("Unable to fetch the client credentials");
		}
		LOGGER.info("Exit UserClientRepoImpl... validateUserDevice");
		return userClientDetail;
		
	}

	@Override
	public LoginUser getUserDetails(String username) {
		LOGGER.info("Inside UserClientRepoImpl... validateClientCredentials");
		LoginUser loginUser = null;
		try{
			loginUser= templateOpsTenantSQL.query(QueryConstants.USER_DETAILS_QUERY, new Object[]{username},
				new ResultSetExtractor<LoginUser>() {
			@Override
			public LoginUser  extractData(ResultSet rs) throws SQLException, DataAccessException {
				LoginUser user = new  LoginUser();
				if(rs.next()){
					user.setUsername(username);
					user.setDbName(rs.getString("db_name"));
				}
				return user;
			}
	  });
		}catch(Exception e){
			e.printStackTrace();
			throw new OpsDatabaseException("Unable to fetch the user db details");
		}
		
		LOGGER.info("exit UserClientRepoImpl... validateClientCredentials");
		return loginUser;
	}


	
}
