package org.openstack.client.cli.commands;

import java.util.Set;

import org.reflections.Reflections;

import com.fathomdb.cli.commands.AutoComplete;
import com.fathomdb.cli.commands.CommandRegistryBase;

public class OpenstackCliCommandRegistry extends CommandRegistryBase {
	public OpenstackCliCommandRegistry() {
		addCommand(new AutoComplete());

		Reflections reflections = Reflections.collect();
		// isProduction() ? Reflections.collect() : new
		// Reflections(getClass().getPackage().getName());

		Set<Class<? extends OpenstackCliCommandRunnerBase>> subTypes = reflections
				.getSubTypesOf(OpenstackCliCommandRunnerBase.class);
		discoverCommands(subTypes);

		// discoverCommands(getClass().getPackage());
	}

}
