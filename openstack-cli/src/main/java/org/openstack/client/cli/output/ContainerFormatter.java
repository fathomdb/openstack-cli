package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.swift.model.Container;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ContainerFormatter extends SimpleFormatter<Container> {

	public ContainerFormatter() {
		super(Container.class);
	}

	@Override
	public void visit(Container o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		putProperties(o, values);

		sink.outputRow(values);
	}

	static void putProperties(Container o, LinkedHashMap<String, Object> values) {
		values.put("name", o.getName());
		// values.put("date", o.getDate());
		values.put("bytesUsed", o.getBytesUsed());
		values.put("objectCount", o.getObjectCount());
	}
}
