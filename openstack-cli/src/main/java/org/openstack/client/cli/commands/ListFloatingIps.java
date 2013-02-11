package org.openstack.client.cli.commands;

import org.openstack.nova.model.FloatingIp;

public class ListFloatingIps extends OpenstackCliCommandRunnerBase {
	public ListFloatingIps() {
		super("list", "floatingips");
	}

	@Override
	public Object runCommand() throws Exception {
		return getCache().listItems(FloatingIp.class, false);
	}

}
