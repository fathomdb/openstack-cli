package org.openstack.client.cli.commands;

import java.io.InputStream;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.KeyPairsExtension.CreateKeyPair;
import org.openstack.nova.model.KeyPair;

import com.fathomdb.io.IoUtils;
import com.fathomdb.io.NoCloseInputStream;

public class CreateSshKey extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public String keyName;

	public CreateSshKey() {
		super("create", "sshkey");
	}

	@Override
	public Object runCommand() throws Exception {
		OpenstackCliContext context = getContext();

		// This command will probably be faster _not_ in nailgun mode
		InputStream stream = new NoCloseInputStream(System.in);
		String publicKey = IoUtils.readAll(stream);

		NovaClient client = context.getComputeClient();
		KeyPair keyPair = new KeyPair();
		keyPair.setName(keyName);
		keyPair.setPublicKey(publicKey);

		CreateKeyPair command = new CreateKeyPair(keyPair);
		return client.execute(command);
	}

}
