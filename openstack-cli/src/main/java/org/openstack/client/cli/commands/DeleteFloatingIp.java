package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.FloatingIpsExtension;
import org.openstack.nova.api.extensions.FloatingIpsExtension.DeallocateFloatingIp;

public class DeleteFloatingIp extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public String id;

	public DeleteFloatingIp() {
		super("delete", "floatingip");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		DeallocateFloatingIp command = FloatingIpsExtension
				.deallocateFloatingIp(id);

		client.execute(command);

		return id;
	}
}
