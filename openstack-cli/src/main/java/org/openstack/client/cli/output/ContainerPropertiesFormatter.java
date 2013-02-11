package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.client.cli.model.ContainerProperties;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ContainerPropertiesFormatter extends
		SimpleFormatter<ContainerProperties> {

	public ContainerPropertiesFormatter() {
		super(ContainerProperties.class);
	}

	@Override
	public void visit(ContainerProperties o, OutputSink sink)
			throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		ContainerFormatter.putProperties(o.getContainer(), values);
		values.put("price", Formatters.formatPrice(o.getPrice()));

		sink.outputRow(values);
	}
}
