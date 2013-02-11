package org.openstack.client.cli.commands;

import org.openstack.nova.model.Server;

public class ListInstances extends OpenstackCliCommandRunnerBase {
	public ListInstances() {
		super("list", "instances");
	}

	@Override
	public Object runCommand() throws Exception {
		return getCache().listItems(Server.class, false);
	}

}
