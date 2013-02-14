package org.openstack.client.cli;

import java.util.Map;

import com.google.common.collect.Maps;

public class InMemorySessionCache implements SessionCache {

	final Map<OpenstackSessionInfo, OpenstackSession> sessions = Maps
			.newHashMap();

	@Override
	public OpenstackSession get(OpenstackSessionInfo key) {
		OpenstackSession session = sessions.get(key);
		if (session == null) {
			session = key.buildSession();
			sessions.put(key, session);
		}
		return session;
	}

}
