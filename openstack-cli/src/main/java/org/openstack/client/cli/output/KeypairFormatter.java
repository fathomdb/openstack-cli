package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.nova.model.KeyPair;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class KeypairFormatter extends SimpleFormatter<KeyPair> {

	public KeypairFormatter() {
		super(KeyPair.class);
	}

	@Override
	public void visit(KeyPair o, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		values.put("name", o.getName());
		values.put("fingerprint", o.getFingerprint());
		values.put("public_key", o.getPublicKey());

		sink.outputRow(values);
	}
}
