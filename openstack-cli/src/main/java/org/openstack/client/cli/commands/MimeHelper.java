package org.openstack.client.cli.commands;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

public class MimeHelper {

	static final Map<String, String> TYPE_BY_EXTENSION = Maps.newHashMap();

	static {
		addContentType("image/png", "png");
		addContentType("image/gif", "gif");
		addContentType("image/jpeg", "jpg", "jpeg");
		addContentType("image/svg+xml", "svg");

		addContentType("text/plain", "txt");
		addContentType("text/css", "css");
		addContentType("text/html", "html");
		addContentType("text/xml", "xml");

		addContentType("application/x-font-ttf", "ttf");
		addContentType("application/x-font-woff", "woff");
		addContentType("application/vnd.ms-fontobject", "eot");
		addContentType("application/javascript", "js");
		addContentType("application/x-shockwave-flash", "swf");
		addContentType("application/zip", "zip");
	}

	public static String getContentType(File file) {
		String name = file.getName();
		int lastDot = name.lastIndexOf('.');
		if (lastDot != -1) {
			String extension = name.substring(lastDot + 1);
			extension = extension.toLowerCase();

			String contentType = TYPE_BY_EXTENSION.get(extension);
			if (contentType != null) {
				return contentType;
			}

		}

		System.err.println("Cannot deduce MIME type for " + name);
		return null;
	}

	private static void addContentType(String contentType, String... extensions) {
		for (String extension : extensions) {
			TYPE_BY_EXTENSION.put(extension, contentType);
		}
	}
}
