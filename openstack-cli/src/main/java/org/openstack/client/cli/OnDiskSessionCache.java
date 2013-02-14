package org.openstack.client.cli;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class OnDiskSessionCache implements SessionCache {
	static final Logger log = LoggerFactory.getLogger(OnDiskSessionCache.class);

	final File base;

	public OnDiskSessionCache(File base) {
		super();
		this.base = base;
	}

	@Override
	public OpenstackSession get(OpenstackSessionInfo sessionInfo) {
		String hash = sessionInfo.getUniqueId();

		OpenstackSession session = null;

		File sessionFile = new File(base, hash);
		if (sessionFile.exists()) {
			try {
				byte[] data = Files.toByteArray(sessionFile);
				session = OpenstackSession.deserialize(data);
			} catch (IOException e) {
				// Ignore
				log.debug("Error reading saved session file", e);
			}
		}

		if (session == null) {
			session = sessionInfo.buildSession();

			try {
				byte[] data = session.serialize();
				Files.write(data, sessionFile);
			} catch (IOException e) {
				// Ignore
				log.debug("Error writing session file", e);
			}
		}

		return session;
	}

}
