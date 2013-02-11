package org.openstack.client.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.kohsuke.args4j.Option;

import com.fathomdb.cli.CliOptions;
import com.fathomdb.io.IoUtils;
import com.fathomdb.io.NoCloseInputStream;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

public class ConfigurationOptions extends CliOptions {
	@Option(name = "-u", aliases = { "--username", "--user" }, usage = "login username")
	String username;

	@Option(name = "-t", aliases = "--tenant", usage = "login tenant")
	String tenantId;

	@Option(name = "-p", aliases = "--password", usage = "login password")
	String password;

	@Option(name = "-s", aliases = "--server", usage = "specify authentication server")
	String server;

	@Option(name = "-debug", aliases = "--debug", usage = "enable debug output")
	boolean debug;

	@Option(name = "-c", aliases = "--config", usage = "specify configuration file")
	String configFile;

	OpenstackService service = null;

	public OpenstackService getOpenstackService() {
		if (service == null) {
			if (configFile == null) {
				OpenstackSessionInfo sessionInfo = new OpenstackSessionInfo(
						server, username, password, tenantId, debug);

				service = buildService(sessionInfo);
			} else {
				InputStream is = null;
				try {
					if (configFile.equals("-")) {
						// Read from stdin
						// Don't auto-close it, as that terminates nailgun
						is = new NoCloseInputStream(System.in);
					} else {
						if (isServerMode()) {
							throw new IllegalArgumentException(
									"Must pass config file over stdin in server mode");
						}
						File file = IoUtils.resolve(configFile);
						if (!file.exists()) {
							throw new FileNotFoundException(
									"Configuration file not found: " + file);
						}

						is = new FileInputStream(file);
					}

					Properties properties = new Properties();
					try {
						properties.load(is);
					} catch (IOException e) {
						throw new IOException(
								"Error reading configuration file", e);
					}
					String server = properties
							.getProperty(OpenstackProperties.AUTH_URL);
					String username = properties
							.getProperty(OpenstackProperties.AUTH_USER);
					String password = properties
							.getProperty(OpenstackProperties.AUTH_SECRET);
					String tenantId = properties
							.getProperty(OpenstackProperties.AUTH_TENANT);

					OpenstackSessionInfo sessionInfo = new OpenstackSessionInfo(
							server, username, password, tenantId, debug);

					service = buildService(sessionInfo);
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"Error reading configuration file", e);
				} finally {
					Closeables.closeQuietly(is);
				}
			}
		}

		return service;
	}

	static final SessionCache sessionCache = new SessionCache();

	private OpenstackService buildService(OpenstackSessionInfo sessionInfo) {
		OpenstackSession session = sessionCache.get(sessionInfo);
		return new OpenstackService(session);
	}
}
