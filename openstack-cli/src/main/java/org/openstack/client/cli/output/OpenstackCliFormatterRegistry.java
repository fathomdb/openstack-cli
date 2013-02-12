package org.openstack.client.cli.output;

import java.util.Set;

import org.reflections.Reflections;

import com.fathomdb.cli.formatter.FormatterRegistryBase;
import com.fathomdb.cli.formatter.SimpleFormatter;

public class OpenstackCliFormatterRegistry extends FormatterRegistryBase {

	public OpenstackCliFormatterRegistry() {
		addDefaultFormatters();

		Reflections reflections = Reflections.collect();
		// isProduction() ? Reflections.collect() : new
		// Reflections(getClass().getPackage().getName());

		Set<Class<? extends SimpleFormatter>> subTypes = reflections
				.getSubTypesOf(SimpleFormatter.class);
		discoverFormatters(subTypes);

		// discoverFormatters(getClass().getPackage());
	}

}
