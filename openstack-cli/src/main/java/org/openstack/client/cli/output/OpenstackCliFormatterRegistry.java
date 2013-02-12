package org.openstack.client.cli.output;

import java.util.Collection;

import org.openstack.client.cli.commands.ReflectionDiscovery;

import com.fathomdb.cli.formatter.FormatterRegistryBase;
import com.fathomdb.cli.formatter.SimpleFormatter;

public class OpenstackCliFormatterRegistry extends FormatterRegistryBase {

	public OpenstackCliFormatterRegistry() {
		addDefaultFormatters();

		ReflectionDiscovery discovery = new ReflectionDiscovery();
		// isProduction() ? Reflections.collect() : new
		// Reflections(getClass().getPackage().getName());

		Collection<Class<? extends SimpleFormatter>> subTypes = discovery
				.getSubTypesOf(SimpleFormatter.class);
		discoverFormatters(subTypes);

		// discoverFormatters(getClass().getPackage());
	}

}
