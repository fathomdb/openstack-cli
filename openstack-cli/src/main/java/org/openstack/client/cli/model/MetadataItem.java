package org.openstack.client.cli.model;

public class MetadataItem {
	final String key;
	final String value;

	public MetadataItem(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
