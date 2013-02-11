package org.openstack.client.cli.model;

import java.util.Map;

public class ServerMetadata {

	private final Map<String, String> metadata;

	public ServerMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

}
