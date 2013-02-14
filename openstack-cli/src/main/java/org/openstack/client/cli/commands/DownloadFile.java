package org.openstack.client.cli.commands;

import java.io.InputStream;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.StoragePath;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.model.ObjectDownload;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

public class DownloadFile extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public StoragePath path;

	public DownloadFile() {
		super("download", "file");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		ObjectDownload objectDownload = path.downloadObject(client);
		InputStream is = null;
		try {
			is = objectDownload.getInputStream();
			ByteStreams.copy(is, System.out);
		} finally {
			Closeables.closeQuietly(is);
		}

		return null;
	}

}
