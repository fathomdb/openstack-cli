package org.openstack.client.cli.commands;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.model.StoragePath;
import org.openstack.swift.SwiftClient;
import org.openstack.swift.api.ListObjects;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class ListFiles extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public StoragePath path;

	public ListFiles() {
		super("list", "files");
	}

	@Override
	public Object runCommand() throws Exception {
		SwiftClient client = getStorageClient();

		String containerName = path.getContainer();
		String objectPath = path.getObjectPath();

		FileIterator iterator = new FileIterator(client, containerName,
				objectPath, null);

		return IterableOnce.build(iterator);
	}

	class FileIterator implements Iterator<org.openstack.swift.model.Object> {
		final SwiftClient client;

		final String prefix;
		final String delimiter;

		Queue<org.openstack.swift.model.Object> queue = null;

		String marker;
		boolean couldHaveMore = true;

		private final String containerName;

		public FileIterator(SwiftClient client, String containerName,
				String prefix, String delimiter) {
			super();
			this.client = client;
			this.containerName = containerName;
			this.prefix = prefix;
			this.delimiter = delimiter;
		}

		@Override
		public boolean hasNext() {
			if (queue == null || queue.isEmpty()) {
				if (couldHaveMore) {
					queue = nextPage(marker);
				}
				if (queue == null || queue.isEmpty()) {
					queue = null;
				}
			}

			return (queue != null && !queue.isEmpty());
		}

		@Override
		public org.openstack.swift.model.Object next() {
			if (queue == null) {
				throw new IllegalStateException();
			}

			org.openstack.swift.model.Object so = queue.remove();
			marker = so.getName();
			return so;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		Queue<org.openstack.swift.model.Object> nextPage(String marker) {
			Map<String, String> filters = Maps.newHashMap();
			if (prefix != null) {
				filters.put("prefix", prefix);
			}

			if (!Strings.isNullOrEmpty(delimiter)) {
				filters.put("delimiter", delimiter);
			}

			if (!Strings.isNullOrEmpty(marker)) {
				filters.put("marker", marker);
			}

			int limit = 10000;

			ListObjects command = new ListObjects(containerName, filters);

			ArrayDeque<org.openstack.swift.model.Object> list = new ArrayDeque<org.openstack.swift.model.Object>();
			list.addAll(client.execute(command));
			if (list.size() != limit) {
				couldHaveMore = false;
			}
			return list;
		}
	}

	@Override
	public void formatRaw(Object o, PrintWriter writer) {
		Iterable<org.openstack.swift.model.Object> items = (Iterable<org.openstack.swift.model.Object>) o;
		for (org.openstack.swift.model.Object item : items) {
			String containerName = path.getContainer();
			writer.println(containerName + "/" + item.getName());
		}
	}

}
