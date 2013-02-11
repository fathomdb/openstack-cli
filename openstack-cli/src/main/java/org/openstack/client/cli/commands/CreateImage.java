package org.openstack.client.cli.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.utils.ProgressListenerPeriodicPrint;
import org.openstack.client.cli.utils.ProgressReportInputStream;
import org.openstack.glance.GlanceClient;
import org.openstack.glance.api.UploadImage;
import org.openstack.glance.model.Image;
import org.openstack.glance.model.ImageForUpload;

import com.fathomdb.io.NoCloseInputStream;
import com.google.common.base.Strings;

public class CreateImage extends OpenstackCliCommandRunnerBase {
	@Argument(index = 0)
	public String name;

	@Argument(index = 1, multiValued = true)
	public List<String> properties;

	public CreateImage() {
		super("create", "image");
	}

	@Override
	public Object runCommand() throws Exception {
		if (Strings.isNullOrEmpty(name)) {
			throw new IllegalArgumentException("Name is required");
		}

		OpenstackCliContext context = getContext();

		GlanceClient client = getGlanceClient();

		ImageForUpload imageTemplate = new ImageForUpload();
		imageTemplate.setName(name);

		String file = null;

		if (properties != null) {
			for (String property : properties) {
				int equalsIndex = property.indexOf('=');
				if (equalsIndex == -1) {
					throw new IllegalArgumentException("Can't parse: "
							+ property);
				}

				String key = property.substring(0, equalsIndex);
				String value = property.substring(equalsIndex + 1);

				if (key.equals("file")) {
					file = value;
				} else {
					imageTemplate.getProperties().put(key, value);
				}
			}
		}

		InputStream imageStream;
		long imageStreamSize = -1;
		if (file == null) {
			// os create-image ImageFactory-bootstrap is_public=True
			// disk_format=qcow2
			// system_id="http://org.platformlayer/service/imagefactory/v1.0:bootstrap"
			// container_format=bare <
			// disk.qcow2

			// This command will probably be faster _not_ in nailgun mode
			imageStream = new NoCloseInputStream(System.in);

			Integer size = imageTemplate.getSize();
			if (size != null) {
				imageStreamSize = size;
			}
		} else {
			if (getContext().getOptions().isServerMode()) {
				throw new IllegalArgumentException(
						"File upload not supported in server mode");
			}

			File imageFile = new File(file);
			imageStreamSize = imageFile.length();
			imageStream = new FileInputStream(imageFile);
		}
		if (imageStreamSize == -1) {
			System.err.println("Warning: image size not supplied");
			System.err.flush();
		}

		ProgressListenerPeriodicPrint listener = null; // new
														// ProgressListenerPeriodicPrint();
		ProgressReportInputStream progressInputStream = new ProgressReportInputStream(
				imageStream, listener);

		imageTemplate.setInputStream(progressInputStream);
		if (imageStreamSize != -1) {
			imageTemplate.setSize((int) imageStreamSize);
		}

		UploadImage command = new UploadImage(imageTemplate);
		Image image = client.execute(command);

		getCache().invalidateCache(Image.class);

		return image;
	}

	private GlanceClient getGlanceClient() {
		return getContext().getImageClient();
	}

}
