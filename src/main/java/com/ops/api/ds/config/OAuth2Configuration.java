package com.ops.api.ds.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.ops.api.security.CustomAuthenticationEntryPoint;
import com.ops.api.security.CustomLogoutSuccessHandler;

@Configuration
public class OAuth2Configuration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

		@Autowired
		private CustomLogoutSuccessHandler customLogoutSuccessHandler;

		@Autowired
		@Qualifier("templateOpsTenantSQL")
		private JdbcTemplate jdbcTemplate;

		@Bean
		public TokenStore tokenStore() {
			return new JdbcTokenStore(jdbcTemplate.getDataSource());
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId("restserve").tokenStore(tokenStore());
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		@Qualifier("templateOpsTenantSQL")
		private JdbcTemplate jdbcTemplate;

		@Bean
		public TokenStore tokenStore() {
			return new JdbcTokenStore(jdbcTemplate.getDataSource());
		}

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Bean
		public JdbcClientDetailsService jdbcClientDetailsService() {
			return new JdbcClientDetailsService(jdbcTemplate.getDataSource());
		}

		@Override
		public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));
			endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
					.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			JdbcClientDetailsService jdbcClientDetailsService = jdbcClientDetailsService();
			List<ClientDetails> clientDetails = jdbcClientDetailsService.listClientDetails();
			for (ClientDetails clientDetail : clientDetails) {
				try {
					jdbcClientDetailsService.removeClientDetails(clientDetail.getClientId());
				} catch (NoSuchClientException ignored) {
					ignored.printStackTrace();
				}
				jdbcClientDetailsService.addClientDetails(clientDetail);
			}

			// Configure clients
			clients.withClientDetails(jdbcClientDetailsService);
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
			defaultTokenServices.setTokenStore(tokenStore());
			defaultTokenServices.setSupportRefreshToken(true);
			return defaultTokenServices;
		}

		@Bean
		public TokenEnhancer tokenEnhancer() {
			return new CustomTokenEnhancer();
		}

		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

	}
}
