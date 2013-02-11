package org.openstack.client.cli.model;

import org.openstack.swift.model.Container;

public class ContainerProperties {
	final Container container;
	Float price;

	public ContainerProperties(Container container) {
		super();
		this.container = container;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Container getContainer() {
		return container;
	}

}
