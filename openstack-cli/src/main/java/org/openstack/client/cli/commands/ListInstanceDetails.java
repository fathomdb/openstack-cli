package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;

public class ListInstanceDetails extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName serverId;

	public ListInstanceDetails() {
		super("list", "instancedetails");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getContext().getComputeClient();
		// TODO: We don't format any extra details

		return client.execute(ServersCore.showServer(serverId.getKey()));
	}

}
