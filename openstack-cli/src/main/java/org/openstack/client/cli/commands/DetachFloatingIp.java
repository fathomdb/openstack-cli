package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.FloatingIpsExtension;
import org.openstack.nova.api.extensions.FloatingIpsExtension.DisassociateFloatingIp;

public class DetachFloatingIp extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	@Argument(index = 1)
	public String ip;

	public DetachFloatingIp() {
		super("detach", "floatingip");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		String instanceId = instanceName.findInstanceId(getContext());

		DisassociateFloatingIp command = FloatingIpsExtension
				.disassociateFloatingIp(instanceId, ip);
		client.execute(command);

		return ip;
	}
}
