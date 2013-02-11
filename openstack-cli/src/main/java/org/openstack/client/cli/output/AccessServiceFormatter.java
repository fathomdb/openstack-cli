package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.openstack.keystone.model.Access;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class AccessServiceFormatter extends SimpleFormatter<Access.Service> {

	public AccessServiceFormatter() {
		super(Access.Service.class);
	}

	@Override
	public void visit(Access.Service o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("type", o.getType());
		values.put("name", o.getName());
		values.put("endpoints", formatEndpoints(o.getEndpoints()));

		sink.outputRow(values);
	}

	public static String formatEndpoints(List<Access.Service.Endpoint> endpoints) {
		StringBuilder sb = new StringBuilder();
		formatEndpoints(sb, endpoints);
		return sb.toString();
	}

	public static void formatEndpoints(StringBuilder sb,
			List<Access.Service.Endpoint> endpoints) {
		for (int i = 0; i < endpoints.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			formatEndpoint(sb, endpoints.get(i));
		}
	}

	public static void formatEndpoint(StringBuilder sb,
			Access.Service.Endpoint serviceEndpoint) {
		sb.append("publicUrl=" + serviceEndpoint.getPublicURL());
		sb.append(" internalUrl=" + serviceEndpoint.getInternalURL());
		sb.append(" region=" + serviceEndpoint.getRegion());
		// sb.append(" tenant=" + serviceEndpoint.getTenantId());
	}
}
