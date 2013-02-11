package org.openstack.client.cli.commands;

import org.openstack.nova.model.Flavor;

public class ListFlavors extends OpenstackCliCommandRunnerBase {
	public ListFlavors() {
		super("list", "flavors");
	}

	@Override
	public Object runCommand() throws Exception {
		return getCache().listItems(Flavor.class, false);
	}

}
