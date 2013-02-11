package org.openstack.client.cli;

import org.openstack.keystone.model.Authentication;
import org.openstack.keystone.model.Authentication.PasswordCredentials;

public class OpenstackCredentials {

	private final String authUrl;
	private final String username;
	private final String password;
	private final String tenantId;

	public OpenstackCredentials(String authUrl, String username,
			String password, String tenantId) {
		this.authUrl = authUrl;
		this.username = username;
		this.password = password;
		this.tenantId = tenantId;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public Authentication getAuthentication() {
		Authentication authentication = new Authentication();
		PasswordCredentials passwordCredentials = new PasswordCredentials();
		passwordCredentials.setUsername(username);
		passwordCredentials.setPassword(password);
		authentication.setPasswordCredentials(passwordCredentials);
		return authentication;
	}

}
