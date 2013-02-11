package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.FloatingIpsExtension;
import org.openstack.nova.api.extensions.FloatingIpsExtension.AssociateFloatingIp;

public class AttachFloatingIp extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	@Argument(index = 1)
	public String ip;

	public AttachFloatingIp() {
		super("attach", "floatingip");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		String instanceId = instanceName.findInstanceId(getContext());

		AssociateFloatingIp command = FloatingIpsExtension.associateFloatingIp(
				instanceId, ip);

		client.execute(command);

		return ip;
	}
}
