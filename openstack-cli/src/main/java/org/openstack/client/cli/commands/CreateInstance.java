package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.model.FlavorName;
import org.openstack.client.cli.model.ImageName;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.api.ServersCore.CreateServer;
import org.openstack.nova.model.Image;
import org.openstack.nova.model.ServerForCreate;

public class CreateInstance extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0, usage = "Instance name")
	public String instanceName;

	@Argument(index = 1, usage = "Image Name")
	public ImageName imageName;

	@Argument(index = 2, usage = "Flavor Name")
	public FlavorName flavorName;

	@Option(name = "k", aliases = "--sshkey", usage = "SSH Key Name")
	public String sshKey;

	public CreateInstance() {
		super("create", "instance");
	}

	@Override
	public Object runCommand() throws Exception {
		OpenstackCliContext context = getContext();

		Image image = imageName.findImage(context);
		if (image == null) {
			throw new IllegalArgumentException("Cannot find image: "
					+ imageName.getKey());
		}

		String flavorId = flavorName.findImageId(context);
		if (flavorId == null) {
			throw new IllegalArgumentException("Cannot find flavor: "
					+ flavorName.getKey());
		}

		NovaClient client = getComputeClient();
		ServerForCreate serverForCreate = new ServerForCreate();
		serverForCreate.setName(instanceName);

		serverForCreate.setImageRef(image.getId());
		serverForCreate.setFlavorRef(flavorId);

		if (sshKey != null) {
			serverForCreate.setKeyName(sshKey);
		}

		CreateServer command = ServersCore.createServer(serverForCreate);
		return client.execute(command);
	}
}
