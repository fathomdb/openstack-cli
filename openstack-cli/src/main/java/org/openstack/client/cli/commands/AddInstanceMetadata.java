//package org.openstack.client.cli.commands;
//
//import org.kohsuke.args4j.Argument;
//import org.openstack.client.cli.model.InstanceName;
//import org.openstack.nova.NovaClient;
//import org.openstack.nova.model.Server;
//
//public class AddInstanceMetadata extends OpenstackCliCommandRunnerBase {
//	@Argument(index = 0)
//	public InstanceName instanceName;
//
//	@Argument(index = 1)
//	public String key;
//
//	@Argument(index = 2)
//	public String value;
//
//	public AddInstanceMetadata() {
//		super("add", "instancemetadata");
//	}
//
//	@Override
//	public Object runCommand() throws Exception {
//		NovaClient client = getComputeClient();
//
//		String instanceId = instanceName.findInstanceId(getContext());
//
//		Server server = new Server();
//		Metadata metadata = server.getMetadata();
//		if (metadata == null) {
//			metadata = new Metadata();
//			server.setMetadata(metadata);
//		}
//		Item item = new Item();
//		item.setKey(key);
//		item.setValue(value);
//		metadata.getItems().add(item);
//
//		return compute.root().servers().server(instanceId).update(server);
//	}
//
// }
