package org.openstack.client.cli.autocomplete;

import java.util.List;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ListContainers;
import org.openstack.swift.model.Container;

import com.fathomdb.cli.CliContext;
import com.fathomdb.cli.autocomplete.SimpleArgumentAutoCompleter;
import com.google.common.collect.Lists;

public class ContainerNameAutoCompleter extends SimpleArgumentAutoCompleter {

	@Override
	public List<String> doComplete(CliContext context, String prefix)
			throws Exception {
		List<String> strings = Lists.newArrayList();

		OpenstackCliContext osContext = (OpenstackCliContext) context;
		SwiftClient client = osContext.getStorageClient();
		ListContainers command = new ListContainers();

		Iterable<Container> items = client.execute(command);
		for (Container item : items) {
			strings.add(item.getName());
		}
		addSuffix(strings, " ");

		return strings;
	}

}
