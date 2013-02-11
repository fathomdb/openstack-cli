package org.openstack.client.cli.commands;

import java.io.PrintWriter;
import java.util.Map;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.StoragePath;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ListObjects;

import com.google.common.collect.Maps;

public class ListFiles extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public StoragePath path;

	public ListFiles() {
		super("list", "files");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		String containerName = path.getContainer();
		String objectPath = path.getObjectPath();

		Map<String, String> filters = Maps.newHashMap();
		filters.put("prefix", objectPath);
		// filters.put("delimeter", null);

		ListObjects command = new ListObjects(containerName, filters);
		return client.execute(command);
	}

	@Override
	public void formatRaw(Object o, PrintWriter writer) {
		Iterable<org.openstack.swift.model.Object> items = (Iterable<org.openstack.swift.model.Object>) o;
		for (org.openstack.swift.model.Object item : items) {
			String containerName = path.getContainer();
			writer.println(containerName + "/" + item.getName());
		}
	}

}
