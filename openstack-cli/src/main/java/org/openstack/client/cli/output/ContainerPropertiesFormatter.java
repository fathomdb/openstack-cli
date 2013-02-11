package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.swift.model.Container;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ContainerPropertiesFormatter extends SimpleFormatter<Container> {

	public ContainerPropertiesFormatter() {
		super(Container.class);
	}

	@Override
	public void visit(Container o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("name", o.getName());
		// values.put("date", o.getDate());
		values.put("bytesUsed", o.getBytesUsed());
		values.put("objectCount", o.getObjectCount());

		sink.outputRow(values);
	}
}
