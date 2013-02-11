package org.openstack.client.cli.commands;

import org.kohsuke.args4j.Argument;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.extensions.SecurityGroupsExtension;
import org.openstack.nova.model.SecurityGroup.Rule;

public class CreateSecurityGroupRule extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public Integer securityGroupId;

	@Argument(index = 1)
	public String protocol;

	@Argument(index = 2)
	public String cidr;

	@Argument(index = 3)
	public Integer fromPort;
	@Argument(index = 4)
	public Integer toPort;

	public CreateSecurityGroupRule() {
		super("create", "securitygrouprule");
	}

	@Override
	public Object runCommand() throws Exception {
		NovaClient client = getComputeClient();

		if (fromPort == null) {
			fromPort = -1;
		}
		if (toPort == null) {
			toPort = fromPort;
		}

		org.openstack.nova.api.extensions.SecurityGroupsExtension.CreateSecurityGroupRule command = SecurityGroupsExtension
				.createSecurityGroupRule(securityGroupId, protocol, fromPort,
						toPort, cidr);

		Rule createdRule = client.execute(command);

		return createdRule;
	}

}
