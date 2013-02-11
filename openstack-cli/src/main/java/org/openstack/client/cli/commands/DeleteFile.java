package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.StoragePath;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.DeleteObject;

public class DeleteFile extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public StoragePath path;

	public DeleteFile() {
		super("delete", "file");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		DeleteObject command = new DeleteObject(path.getContainer(),
				path.getObjectPath());
		client.execute(command);

		return path.getKey();
	}

}
