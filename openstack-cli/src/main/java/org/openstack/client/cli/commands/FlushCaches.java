package org.openstack.client.cli.commands;

public class FlushCaches extends OpenstackCliCommandRunnerBase {
	public FlushCaches() {
		super("flush", "cached");
	}

	@Override
	public Object runCommand() throws Exception {
		getCache().invalidateAll();
		return null;
	}

}
