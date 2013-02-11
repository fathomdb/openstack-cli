package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.api.ServersCore.RebootServer;
import org.openstack.nova.model.Server;

public class RebootInstance extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	public RebootInstance() {
		super("reboot", "instance");
	}

	@Override
	public Object runCommand() throws Exception {
		OpenstackCliContext context = getContext();

		String serverId = instanceName.findInstanceId(context);
		if (serverId == null) {
			throw new IllegalArgumentException("Cannot find instance: "
					+ instanceName.getKey());
		}

		NovaClient client = getComputeClient();

		RebootServer command = ServersCore.reboot(serverId, "SOFT");

		client.execute(command);

		invalidateCache(Server.class);

		return serverId;
	}

}
