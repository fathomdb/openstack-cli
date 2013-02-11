package org.openstack.client.cli.autocomplete;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ListContainers;
import org.openstack.swift.api.ListObjects;
import org.openstack.swift.model.Container;
import org.openstack.swift.model.Object;

import com.fathomdb.cli.CliContext;
import com.fathomdb.cli.autocomplete.SimpleArgumentAutoCompleter;
import com.google.common.collect.Lists;

public class StoragePathAutoCompleter extends SimpleArgumentAutoCompleter {

	@Override
	public List<String> doComplete(CliContext context, String prefix)
			throws Exception {
		List<String> strings = Lists.newArrayList();

		OpenstackCliContext osContext = (OpenstackCliContext) context;
		SwiftClient client = osContext.getStorageClient();

		if (!prefix.contains("/")) {
			ListContainers command = new ListContainers();

			Iterable<Container> items = client.execute(command);
			for (Container item : items) {
				strings.add(item.getName());
			}
			addSuffix(strings, "/");
			return strings;
		} else {
			String[] pathTokens = prefix.split("/");
			if (pathTokens.length == 1 || pathTokens.length == 2) {
				String containerName = pathTokens[0];
				Map<String, String> filters = Collections.emptyMap();
				ListObjects command = new ListObjects(containerName, filters);

				Iterable<Object> items = client.execute(command);
				for (Object item : items) {
					strings.add(item.getName());
				}

				addPrefix(strings, pathTokens[0] + "/");
				addSuffix(strings, " ");
				return strings;
			} else {
				return null;
			}
		}
	}

}
