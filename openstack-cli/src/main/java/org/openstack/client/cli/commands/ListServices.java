package org.openstack.client.cli.commands;

import org.openstack.keystone.model.Service;

public class ListServices extends OpenstackCliCommandRunnerBase {
	public ListServices() {
		super("list", "services");
	}

	@Override
	public Object runCommand() throws Exception {
		return getCache().listItems(Service.class, false);
	}
}
