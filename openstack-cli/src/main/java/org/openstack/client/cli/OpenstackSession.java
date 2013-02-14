package org.openstack.client.cli;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
import com.google.common.io.Closeables;

public class OpenstackSession implements Serializable {
	private static final long serialVersionUID = 1L;

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
		String token = getToken();

		GlanceClient client = new GlanceClient(url, token);
		return client;
	}

	public NovaClient getNovaClient() {
		String url = KeystoneUtils.findEndpointURL(access.getServiceCatalog(),
				"compute", null, "public");
		String token = getToken();

		NovaClient client = new NovaClient(url, token);
		return client;
	}

	public SwiftClient getSwiftClient() {
		String url = KeystoneUtils.findEndpointURL(access.getServiceCatalog(),
				"object-store", null, "public");
		String token = getToken();

		SwiftClient client = new SwiftClient(url, token);
		return client;
	}

	public List<org.openstack.keystone.model.Service> getServiceCatalog() {
		KeystoneClient keystone = new KeystoneClient(credentials.getAuthUrl());
		String token = getToken();
		keystone.setToken(token);

		ListServices command = new ListServices();
		return keystone.execute(command).getList();
	}

	private String getToken() {
		return access.getToken().getId();
	}

	public boolean isAuthenticated() {
		return access != null;
	}

	public WellKnownCloud getWellKnownCloud() {
		String authUrl = credentials.getAuthUrl();
		authUrl = authUrl.toLowerCase();
		authUrl = authUrl.trim();
		authUrl += "/";

		if (authUrl.startsWith("https://identity.api.rackspacecloud.com/")) {
			return WellKnownCloud.RACKSPACE_CLOUD;
		}

		return null;
	}

	public byte[] serialize() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();

		return baos.toByteArray();
	}

	public static OpenstackSession deserialize(byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return (OpenstackSession) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException("Error deserializing data", e);
		} finally {
			Closeables.closeQuietly(ois);
		}
	}
}
