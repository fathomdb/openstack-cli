package org.openstack.client.cli;

import java.util.List;

import org.openstack.glance.GlanceClient;
import org.openstack.keystone.KeystoneClient;
import org.openstack.keystone.api.Authenticate;
import org.openstack.keystone.api.ListServices;
import org.openstack.keystone.model.Access;
import org.openstack.keystone.model.Authentication;
import org.openstack.keystone.utils.KeystoneUtils;
import org.openstack.nova.NovaClient;
import org.openstack.swift.SwiftClient;

import com.google.common.base.Strings;

public class OpenstackSession {

	private Access access;
	private OpenstackCredentials credentials;

	public void authenticate(OpenstackCredentials credentials, boolean b) {
		this.credentials = credentials;
		String authUrl = credentials.getAuthUrl();
		if (Strings.isNullOrEmpty(authUrl)) {
			throw new IllegalArgumentException(
					"Authentication URL not provided");
		}
		KeystoneClient keystone = new KeystoneClient(authUrl);

		Authentication authentication = credentials.getAuthentication();

		// access with unscoped token
		Access access = keystone.execute(new Authenticate(authentication));

		this.access = access;

		// // use the token in the following requests
		// keystone.setToken(access.getToken().getId());
	}

	public GlanceClient getGlanceClient() {
		String url = KeystoneUtils.findEndpointURL(access.getServiceCatalog(),
				"image", null, "public");
		String token = access.getToken().getId();

		GlanceClient client = new GlanceClient(url, token);
		return client;
	}

	public NovaClient getNovaClient() {
		String url = KeystoneUtils.findEndpointURL(access.getServiceCatalog(),
				"compute", null, "public");
		String token = access.getToken().getId();

		NovaClient client = new NovaClient(url, token);
		return client;
	}

	public SwiftClient getSwiftClient() {
		String url = KeystoneUtils.findEndpointURL(access.getServiceCatalog(),
				"object-store", null, "public");
		String token = access.getToken().getId();

		SwiftClient client = new SwiftClient(url, token);
		return client;
	}

	public List<org.openstack.keystone.model.Service> getServiceCatalog() {
		KeystoneClient keystone = new KeystoneClient(credentials.getAuthUrl());
		String token = access.getToken().getId();
		keystone.setToken(token);

		ListServices command = new ListServices();
		return keystone.execute(command).getList();
	}

	public boolean isAuthenticated() {
		return access != null;
	}

}
