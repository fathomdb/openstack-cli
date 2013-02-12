package org.openstack.client.cli.commands;

import java.util.Collection;

import com.fathomdb.cli.commands.AutoComplete;
import com.fathomdb.cli.commands.CommandRegistryBase;

public class OpenstackCliCommandRegistry extends CommandRegistryBase {
	public OpenstackCliCommandRegistry() {
		addCommand(new AutoComplete());

		// isProduction() ? Reflections.collect() : new
		// Reflections(getClass().getPackage().getName());

		ReflectionDiscovery discovery = new ReflectionDiscovery();

		Collection<Class<? extends OpenstackCliCommandRunnerBase>> subTypes = discovery
				.getSubTypesOf(OpenstackCliCommandRunnerBase.class);

		if (subTypes == null || subTypes.isEmpty()) {
			throw new IllegalStateException("Unable to load list of commands");
		}

		discoverCommands(subTypes);

		// discoverCommands(getClass().getPackage());
	}

}
