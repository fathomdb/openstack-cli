package org.openstack.client.cli.model;

import javax.ws.rs.core.Response;

import org.openstack.client.cli.autocomplete.StoragePathAutoCompleter;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ShowObject;

import com.fathomdb.cli.StringWrapper;
import com.fathomdb.cli.autocomplete.HasAutoCompletor;

@HasAutoCompletor(StoragePathAutoCompleter.class)
public class StoragePath extends StringWrapper {
	public StoragePath(String key) {
		super(key);
	}

	public StoragePath(StoragePath parent, String name) {
		super(join(parent.getKey(), name));
	}

	private static String join(String base, String relative) {
		String joined;
		if (base.endsWith("/")) {
			joined = base + relative;
		} else {
			joined = base + "/" + relative;
		}
		return joined;
	}

	public Response getResource(SwiftClient client) {
		String containerName = getContainer();
		String objectPath = getObjectPath();
		if (containerName == null || objectPath == null) {
			throw new IllegalArgumentException("Cannot parse: " + getKey());
		}

		ShowObject showObject = new ShowObject(containerName, objectPath);
		Response response = client.execute(showObject);
		return response;
	}

	public String getContainer() {
		String[] tokens = getKey().split("/");
		if (tokens.length == 0) {
			throw new IllegalArgumentException("Cannot parse: " + getKey());
		}
		return tokens[0];
	}

	public String getObjectPath() {
		String key = getKey();
		int firstSlash = key.indexOf('/');
		if (firstSlash == -1) {
			return null;
		}
		return key.substring(firstSlash + 1);
	}
}
