package org.openstack.client.cli.output;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.OpenstackService;
import org.openstack.nova.model.Flavor;
import org.openstack.nova.model.Image;
import org.openstack.nova.model.Server;

import com.fathomdb.cli.formatter.SimpleFormatter;
import com.fathomdb.cli.output.OutputSink;
import com.google.common.collect.Maps;

public class ServerFormatter extends SimpleFormatter<Server> {

	public ServerFormatter() {
		super(Server.class);
	}

	@Override
	public void visit(Server server, OutputSink sink) throws IOException {
		LinkedHashMap<String, Object> values = Maps.newLinkedHashMap();

		OpenstackService service = OpenstackCliContext.get()
				.getOpenstackService();

		// Flavor flavor = service.resolveFlavor(server.getFlavor());
		Flavor flavor = server.getFlavor();
		String flavorName = null;
		if (flavor != null) {
			flavorName = flavor.getName();
		}

		Image image = server.getImage();
		// Image image = service.resolveImage(server.getImage());
		String imageName = null;
		if (image != null) {
			imageName = image.getName();
		}

		values.put("id", server.getId());
		values.put("flavor", flavorName);
		values.put("image", imageName);
		values.put("name", server.getName());
		values.put("status", server.getStatus());
		values.put("networks",
				AddressesFormatter.formatAddresses(server.getAddresses()));

		// ExtensionRegistry registry = new ExtensionRegistry();
		// registry.add(new Extension(DiskConfigAttributes.class));
		// registry.add(new Extension(ExtendedStatusAttributes.class));
		//
		// ExtensionValues extensions = registry.parseAllExtensions(server
		// .getExtensionData());
		//
		// {
		// DiskConfigAttributes attributes = extensions
		// .get(DiskConfigAttributes.class);
		// values.put("disk", attributes);
		// }
		//
		// {
		// ExtendedStatusAttributes attributes = extensions
		// .get(ExtendedStatusAttributes.class);
		// values.put("extstatus", attributes);
		// }

		sink.outputRow(values);
	}
}
