package com.ops.api.query.contants;

// Query related to mobile apps
public class QueryConstants {

	public final static String USER_CLIENT_DETAILS_QUERY = "select u.user_id, u.first_name, u.last_name, u.email_id, "
			+ " u.phone, u.password,u.enabled,  r.role_id, r.role_name, r.role_desc, u.sys_password, "
			+ " t.company_code, t.db_name,m.client_id,m.client_secret from ops_users u "
			+ "  inner join tenants t on t.user_email=u.email_id"
			+ "  inner join mobile_tenants m on m.tenant_id=t.tenant_id"
			+ "  inner join ops_user_role ur  on ur.user_id=u.user_id"
			+ "  INNER join ops_role r on ur.role_id=r.role_id"
			+ "  where  u.user_id=ur.user_id and u.email_id  =  ? and usertype=?";

	public static final String USER_APP_DEVICE_QUERY = "select t.user_email,m.client_id, m.client_secret, m.db_name "
			+ " from mobile_tenants m left outer join tenants t on t.tenant_id=m.tenant_id where  t.app_device_id=?";;

	public static final String USER_ROLE_QUERY = "select u.user_id, u.first_name, u.last_name, u.email_id, u.phone, u.password,u.enabled, "
			+ " r.role_id, r.role_name, r.role_desc, u.sys_password "
			+ " from ops_users u inner join ops_user_role ur  INNER join ops_role r on ur.role_id=r.role_id "
			+ "  where  u.user_id=ur.user_id and u.email_id =  ?";
	
	public final static String USER_DETAILS_QUERY="select u.email_id, t.db_name from ops_users u"
			+ " inner join tenants t on t.user_email=u.email_id and"
			+ " u.email_id  =  ?";
}