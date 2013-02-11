package org.openstack.client.cli.commands;

import java.util.List;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.ContainerName;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ListContainers;
import org.openstack.swift.model.Container;

public class ContainerInfo extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public ContainerName name;

	public ContainerInfo() {
		super("container", "info");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		ListContainers command = new ListContainers();
		List<Container> containers = client.execute(command);

		return containers;
	}

}
