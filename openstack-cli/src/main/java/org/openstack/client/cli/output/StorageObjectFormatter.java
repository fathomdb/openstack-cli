package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class StorageObjectFormatter extends
		SimpleFormatter<org.openstack.swift.model.Object> {

	public StorageObjectFormatter() {
		super(org.openstack.swift.model.Object.class);
	}

	@Override
	public void visit(org.openstack.swift.model.Object o, OutputSink sink)
			throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("name", o.getName());
		values.put("bytes", o.getBytes());
		values.put("hash", o.getHash());
		values.put("contentType", o.getContentType());

		sink.outputRow(values);
	}

}
