package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.KeyPairsExtension;
import org.openstack.nova.api.extensions.KeyPairsExtension.DeleteKeyPair;

public class DeleteKeypair extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public String id;

	public DeleteKeypair() {
		super("delete", "keypair");
	}

	@Override
	public Object runCommand() throws Exception {
		DeleteKeyPair command = KeyPairsExtension.delete(id);

		NovaClient client = getContext().getComputeClient();
		client.execute(command);
		return id;
	}
}
