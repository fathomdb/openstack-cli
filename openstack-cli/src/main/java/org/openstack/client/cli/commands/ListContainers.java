package org.openstack.client.cli.commands;

import java.util.List;

import org.openstack.client.cli.WellKnownCloud;
import org.openstack.client.cli.model.ContainerProperties;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.model.Container;

import com.google.common.collect.Lists;

public class ListContainers extends OpenstackCliCommandRunnerBase {
	public ListContainers() {
		super("list", "containers");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		org.openstack.swift.api.ListContainers command = new org.openstack.swift.api.ListContainers();
		List<Container> containers = client.execute(command);

		WellKnownCloud cloud = getContext().getWellKnownCloud();

		List<ContainerProperties> annotatedList = Lists.newArrayList();
		for (Container container : containers) {
			ContainerProperties annotated = new ContainerProperties(container);
			if (container.getBytesUsed() != null) {
				if (cloud != null) {
					Float price = cloud.getSwiftMonthlyPrice(container);

					annotated.setPrice(price);
				}
			}
			annotatedList.add(annotated);
		}
		return annotatedList;
	}

}
