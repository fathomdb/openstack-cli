package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.nova.model.Extension;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ExtensionFormatter extends SimpleFormatter<Extension> {

	public ExtensionFormatter() {
		super(Extension.class);
	}

	@Override
	public void visit(Extension o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("alias", o.getAlias());
		values.put("name", o.getName());
		values.put("namespace", o.getNamespace());
		values.put("updated", o.getUpdated());

		sink.outputRow(values);
	}

}
