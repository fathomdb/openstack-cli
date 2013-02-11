package org.openstack.client.cli.commands;

import java.util.Map;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.client.cli.model.ServerMetadata;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.api.ServersCore.ShowServerMetadata;

public class ListMetadata extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	public ListMetadata() {
		super("list", "instancemetadata");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		String instanceId = instanceName.findInstanceId(getContext());

		ShowServerMetadata command = ServersCore.showServerMetadata(instanceId);

		Map<String, String> metadata = client.execute(command);
		return new ServerMetadata(metadata);
	}

}
