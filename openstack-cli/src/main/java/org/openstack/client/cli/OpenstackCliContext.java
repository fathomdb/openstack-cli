package org.openstack.client.cli;

import java.io.IOException;

import org.openstack.client.cli.commands.OpenstackCliCommandRegistry;
import org.openstack.client.cli.output.OpenstackCliFormatterRegistry;
import org.openstack.glance.GlanceClient;
import org.openstack.nova.NovaClient;
import org.openstack.swift.SwiftClient;

import com.fathomdb.cli.CliContextBase;

public class OpenstackCliContext extends CliContextBase {
	final ConfigurationOptions options;

	public OpenstackCliContext(OpenstackCliCommandRegistry commandRegistry,
			ConfigurationOptions options) throws IOException {
		super(commandRegistry, new OpenstackCliFormatterRegistry());

		this.options = options;
	}

	// public OpenstackSession getOpenstackSession() {
	// return options.getOpenstackSession();
	// }

	public OpenstackService getOpenstackService() {
		return options.getOpenstackService();
	}

	public NovaClient getComputeClient() {
		return getOpenstackService().getNovaClient();
	}

	public GlanceClient getImageClient() {
		return getOpenstackService().getGlanceClient();
	}

	public SwiftClient getStorageClient() {
		return getOpenstackService().getSwiftClient();
	}

	public ConfigurationOptions getOptions() {
		return options;
	}

	@Override
	public void connect() {
		// getComputeClient();
	}

	public static OpenstackCliContext get() {
		return (OpenstackCliContext) CliContextBase.get();
	}

	// public SwiftClient getStorageClient() {
	// return getOpenstackSession().getStorageClient();
	// }

	public OpenstackCache getCache() {
		OpenstackService service = getOpenstackService();
		OpenstackCache cache = (OpenstackCache) service.getExtensions().get(
				OpenstackCache.class);
		if (cache == null) {
			cache = new OpenstackCache(service);
			service.getExtensions().put(OpenstackCache.class, cache);
		}
		return cache;
	}

	public WellKnownCloud getWellKnownCloud() {
		return getOpenstackService().getWellKnownCloud();
	}

}
