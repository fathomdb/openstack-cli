package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openstack.nova.model.Server.Addresses;
import org.openstack.nova.model.Server.Addresses.Address;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class AddressesFormatter extends SimpleFormatter<Addresses> {

	public AddressesFormatter() {
		super(Addresses.class);
	}

	@Override
	public void visit(Addresses o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		StringBuilder sb = new StringBuilder();
		formatNetworks(sb, o.getAddresses());

		values.put("networks", sb.toString());

		sink.outputRow(values);
	}

	public static String formatAddresses(Addresses addresses) {
		if (addresses == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		formatNetworks(sb, addresses.getAddresses());
		return sb.toString();
	}

	public static void formatNetworks(StringBuilder sb,
			Map<String, List<Address>> networks) {
		if (networks != null) {
			int i = 0;
			for (String key : networks.keySet()) {
				if (i != 0) {
					sb.append(",");
				}
				i++;
				formatNetwork(sb, key, networks.get(key));
			}
		}
	}

	public static void formatNetwork(StringBuilder sb, String key,
			List<Address> addresses) {
		sb.append(key + "=");
		formatIps(sb, addresses);
	}

	public static void formatIps(StringBuilder sb, List<Address> ips) {
		if (ips != null) {
			for (int i = 0; i < ips.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(ips.get(i).getAddr());
			}
		}
	}
}
