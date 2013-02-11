package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.swift.SwiftClient;

public class CreateContainer extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public String name;

	public CreateContainer() {
		super("create", "container");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		org.openstack.swift.api.CreateContainer command = new org.openstack.swift.api.CreateContainer(
				name);
		client.execute(command);

		return name;
	}

}
