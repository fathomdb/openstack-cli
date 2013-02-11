package org.openstack.client.cli.commands;

import org.openstack.client.cli.OpenstackCache;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.OpenstackService;
import org.openstack.nova.NovaClient;
import org.openstack.swift.SwiftClient;

import com.fathomdb.cli.commands.CommandRunnerBase;
import com.fathomdb.cli.commands.CommandSpecifier;

public abstract class OpenstackCliCommandRunnerBase extends CommandRunnerBase {

	protected OpenstackCliCommandRunnerBase(String verb, String noun) {
		super(verb, noun);
	}

	protected OpenstackCliCommandRunnerBase(CommandSpecifier commandSpecifier) {
		super(commandSpecifier);
	}

	protected SwiftClient getStorageClient() {
		return getContext().getStorageClient();
	}

	protected NovaClient getComputeClient() {
		return getContext().getComputeClient();
	}

	// protected OpenstackSession getOpenstackSession() {
	// return getContext().getOpenstackSession();
	// }

	@Override
	protected OpenstackCliContext getContext() {
		return (OpenstackCliContext) super.getContext();
	}

	protected void invalidateCache(Class<?> modelClass) {
		getCache().invalidateCache(modelClass);
	}

	protected OpenstackCache getCache() {
		return getContext().getCache();
	}

	protected OpenstackService getOpenstackService() {
		return getContext().getOpenstackService();
	}
}
