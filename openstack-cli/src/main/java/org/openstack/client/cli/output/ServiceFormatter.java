package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.keystone.model.Service;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ServiceFormatter extends SimpleFormatter<Service> {

	public ServiceFormatter() {
		super(Service.class);
	}

	@Override
	public void visit(Service o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("id", o.getId());
		values.put("type", o.getType());
		values.put("name", o.getName());

		sink.outputRow(values);
	}
}
