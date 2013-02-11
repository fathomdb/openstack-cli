package org.openstack.client.cli.commands;

import java.io.InputStream;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.model.GlanceImageName;
import org.openstack.glance.GlanceClient;
import org.openstack.glance.model.Image;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

public class DownloadImage extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public GlanceImageName imageName;

	public DownloadImage() {
		super("download", "image");
	}

	@Override
	public Object runCommand() throws Exception {
		OpenstackCliContext context = getContext();

		Image image = imageName.resolve(context);
		if (image == null) {
			throw new IllegalArgumentException("Cannot find image: "
					+ imageName.getKey());
		}

		GlanceClient client = context.getImageClient();

		org.openstack.glance.api.DownloadImage command = new org.openstack.glance.api.DownloadImage(
				image.getId());
		InputStream is = client.execute(command).getInputStream();
		try {
			ByteStreams.copy(is, System.out);
		} finally {
			Closeables.closeQuietly(is);
		}

		return null;
	}

}
