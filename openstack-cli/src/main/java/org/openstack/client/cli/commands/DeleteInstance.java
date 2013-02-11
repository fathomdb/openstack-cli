package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.api.ServersCore.DeleteServer;
import org.openstack.nova.model.Server;

public class DeleteInstance extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	public DeleteInstance() {
		super("delete", "instance");
	}

	@Override
	public Object runCommand() throws Exception {
		OpenstackCliContext context = getContext();

		String serverId = instanceName.findInstanceId(context);
		if (serverId == null) {
			throw new IllegalArgumentException("Cannot find instance: "
					+ instanceName.getKey());
		}

		NovaClient client = context.getComputeClient();

		DeleteServer command = ServersCore.deleteServer(serverId);
		client.execute(command);

		invalidateCache(Server.class);

		return serverId;
	}

}
