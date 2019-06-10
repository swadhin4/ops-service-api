package com.ops.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component 
@PropertySource("classpath:ops_sql_scripts.properties")
public class QueryUtils implements EnvironmentAware{

	
	@Autowired
	static Environment environment;
	
	
	public String getIncidentListQuery(){
		return environment.getProperty("ext_sp_incident_query");
	}


	@Override
	public void setEnvironment(Environment environment) {
		QueryUtils.environment=environment;
		
	}
}
