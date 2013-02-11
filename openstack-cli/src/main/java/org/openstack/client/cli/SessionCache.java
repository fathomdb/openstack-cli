package org.openstack.client.cli;

import java.util.Map;

import com.google.common.collect.Maps;

public class SessionCache {

	final Map<OpenstackSessionInfo, OpenstackSession> sessions = Maps
			.newHashMap();

	public OpenstackSession get(OpenstackSessionInfo key) {
		OpenstackSession session = sessions.get(key);
		if (session == null) {
			session = key.buildSession();
			sessions.put(key, session);
		}
		return session;
	}

}
