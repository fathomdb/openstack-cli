package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.InstanceName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.OutputExtension.GetConsoleOutputServer;
import org.openstack.nova.model.ServerAction;
import org.openstack.nova.model.ServerAction.ConsoleOutput;

public class GetConsoleOutput extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public InstanceName instanceName;

	public GetConsoleOutput() {
		super("get", "consoleoutput");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		String instanceId = instanceName.findInstanceId(getContext());

		Integer length = null;
		GetConsoleOutputServer command = new GetConsoleOutputServer(instanceId,
				new ServerAction.GetConsoleOutput(length));
		ConsoleOutput consoleOutput = client.execute(command);

		return consoleOutput.getOutput();
	}

}
