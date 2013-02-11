package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.client.cli.model.MetadataItem;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class MetadataItemFormatter extends SimpleFormatter<MetadataItem> {

	public MetadataItemFormatter() {
		super(MetadataItem.class);
	}

	@Override
	public void visit(MetadataItem o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("key", o.getKey());
		values.put("value", o.getValue());

		sink.outputRow(values);
	}

}
