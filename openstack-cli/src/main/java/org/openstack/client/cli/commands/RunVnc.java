//package org.openstack.client.cli.commands;
//
//import org.kohsuke.args4j.Argument;
//import org.openstack.client.cli.model.InstanceName;
//
//public class RunVnc extends OpenstackCliCommandRunnerBase {
//	@Argument(index = 0)
//	public InstanceName instanceName;
//
//	public RunVnc() {
//		super("run", "vnc");
//	}
//
//	@Override
//	public Object runCommand() throws Exception {
//		NovaClient client = getComputeClient();
//
//		String instanceId = instanceName.findInstanceId(getContext());
//
//		String type = "novnc";
//		Console console = compute.root().servers().server(instanceId).getVncConsole(type);
//
//		return console;
//	}
//
// }
