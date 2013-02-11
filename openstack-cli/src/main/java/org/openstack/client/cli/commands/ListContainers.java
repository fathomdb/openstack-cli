package org.openstack.client.cli.commands;

import org.openstack.swift.SwiftClient;

public class ListContainers extends OpenstackCliCommandRunnerBase {
	public ListContainers() {
		super("list", "containers");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		org.openstack.swift.api.ListContainers command = new org.openstack.swift.api.ListContainers();
		return client.execute(command);
	}

}
