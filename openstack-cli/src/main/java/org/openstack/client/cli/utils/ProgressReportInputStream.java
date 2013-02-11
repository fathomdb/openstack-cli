package org.openstack.client.cli.utils;

import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressReportInputStream extends FilterInputStream {

	private final ProgressListener progress;

	public ProgressReportInputStream(InputStream file, ProgressListener progress) throws FileNotFoundException {
		super(file);
		this.progress = progress;
	}

	@Override
	public int read() throws IOException {
		int b = super.read();
		notify(1);
		return b;
	}

	private void notify(int count) {
		if (this.progress != null) {
			this.progress.notify(count);
		}
	}

	@Override
	public int read(byte b[]) throws IOException {
		int count = super.read(b);
		notify(count);
		return count;
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int count = super.read(b, off, len);
		notify(count);
		return count;
	}
}
