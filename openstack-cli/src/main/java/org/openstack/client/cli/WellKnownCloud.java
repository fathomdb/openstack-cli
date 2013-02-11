package org.openstack.client.cli;

import org.openstack.swift.model.Container;

public class WellKnownCloud {

	final float swiftPricePerMegabyte;

	public WellKnownCloud(float swiftPricePerMegabyte) {
		this.swiftPricePerMegabyte = swiftPricePerMegabyte;
	}

	public static final WellKnownCloud RACKSPACE_CLOUD = new WellKnownCloud(
			0.10f / 1024.0f);

	public Float getSwiftMonthlyPrice(Container container) {
		if (container.getBytesUsed() == null) {
			return null;
		}

		double price = container.getBytesUsed();
		price /= 1024; // Bytes => KB
		price /= 1024; // KB => MB
		price *= swiftPricePerMegabyte;
		return (float) price;
	}

}
