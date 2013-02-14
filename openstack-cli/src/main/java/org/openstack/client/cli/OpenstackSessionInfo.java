package org.openstack.client.cli;

import com.fathomdb.hash.Md5Hash;

public class OpenstackSessionInfo {
	final boolean debug;
	final String username;
	final String password;
	final String tenantId;
	final String authUrl;

	public OpenstackSessionInfo(String authUrl, String username,
			String password, String tenantId, boolean debug) {
		super();
		this.authUrl = authUrl;
		this.username = username;
		this.password = password;
		this.tenantId = tenantId;
		this.debug = debug;
	}

	public String getUniqueId() {
		Md5Hash.Hasher hasher = new Md5Hash.Hasher();
		Md5Hash hash = hasher.hash(authUrl + ":" + username + ":" + tenantId);
		return hash.toHex();
	}

	public OpenstackSession buildSession() {
		OpenstackSession session = new OpenstackSession();
		// if (debug) {
		// session = session.with(Feature.VERBOSE);
		// }
		//
		// session.setLinkResolver(new CachingLinkResolver(session));

		if (!session.isAuthenticated()) {
			OpenstackCredentials credentials = new OpenstackCredentials(
					authUrl, username, password, tenantId);
			session.authenticate(credentials, true);
		}

		return session;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authUrl == null) ? 0 : authUrl.hashCode());
		result = prime * result + (debug ? 1231 : 1237);
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((tenantId == null) ? 0 : tenantId.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OpenstackSessionInfo other = (OpenstackSessionInfo) obj;
		if (authUrl == null) {
			if (other.authUrl != null) {
				return false;
			}
		} else if (!authUrl.equals(other.authUrl)) {
			return false;
		}
		if (debug != other.debug) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (tenantId == null) {
			if (other.tenantId != null) {
				return false;
			}
		} else if (!tenantId.equals(other.tenantId)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
}