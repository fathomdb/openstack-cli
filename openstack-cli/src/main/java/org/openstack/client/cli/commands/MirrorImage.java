//package org.openstack.client.cli.commands;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.net.URL;
//import java.util.Map.Entry;
//import java.util.Properties;
//import java.util.zip.GZIPInputStream;
//
//import org.kohsuke.args4j.Option;
//import org.openstack.client.cli.OpenstackCliContext;
//import org.openstack.client.cli.utils.ProgressListenerPeriodicPrint;
//import org.openstack.client.cli.utils.ProgressReportInputStream;
//import org.openstack.client.common.OpenstackImageClient;
//import org.openstack.model.image.Image;
//import org.openstack.utils.Io;
//
//import com.fathomdb.cli.CliException;
//
//public class MirrorImage extends OpenstackCliCommandRunnerBase {
//	@Option(name = "prefix", aliases = { "--prefix" })
//	public String prefix;
//
//	@Option(name = "force", aliases = { "--force" })
//	public boolean force;
//
//	@Option(name = "url", aliases = { "--url" }, required = true)
//	public String url;
//
//	public MirrorImage() {
//		super("mirror", "image");
//	}
//
//	@Override
//	public Object runCommand() throws Exception {
//		OpenstackCliContext context = getContext();
//		OpenstackImageClient imageClient = context.getImageClient();
//
//		URL propertiesUrl = new URL(this.url);
//
//		String propertiesString;
//		try {
//			propertiesString = Io.readAll(propertiesUrl);
//		} catch (IOException e) {
//			throw new CliException("Error reading from url", e);
//		}
//
//		Properties properties = new Properties();
//		try {
//			properties.load(new StringReader(propertiesString));
//		} catch (IOException e) {
//			throw new CliException("Error reading properties", e);
//		}
//
//		Image imageTemplate = new Image();
//
//		URL imageUrl = null;
//		long imageSize = -1;
//		String expand = null;
//		String name = null;
//		String checksum = null;
//
//		for (Entry<Object, Object> entry : properties.entrySet()) {
//			String key = (String) entry.getKey();
//			String value = (String) entry.getValue();
//
//			if (key.startsWith("org.openstack.sync__")) {
//				String subkey = key;
//				subkey = subkey.replace("org.openstack.sync__1__", "");
//				if (subkey.equals("name")) {
//					name = value;
//				} else if (subkey.equals("image")) {
//					imageUrl = new URL(propertiesUrl, value);
//				} else if (subkey.equals("size")) {
//					imageSize = Long.valueOf(value);
//				} else if (subkey.equals("expand")) {
//					expand = value;
//				} else if (subkey.equals("checksum")) {
//					checksum = value;
//				} else {
//					System.err.println("Warning: Unknown sync property: " + key + "=" + value);
//				}
//			} else {
//				imageTemplate.put(key, value);
//			}
//		}
//
//		if (imageUrl == null) {
//			String imageUrlString = this.url.replace(".properties", ".image");
//			imageUrl = new URL(imageUrlString);
//		}
//
//		if (name == null) {
//			name = extractName(imageUrl);
//		}
//
//		if (prefix != null) {
//			name = prefix + name;
//		}
//
//		imageTemplate.setName(name);
//
//		Image foundImage = null;
//		for (Image image : imageClient.root().images().list(true)) {
//			if (name.equals(image.getName())) {
//				foundImage = image;
//				break;
//			}
//		}
//
//		if (foundImage != null) {
//			if (force) {
//				System.out.println("Found image with same name; but --force specified so will replace");
//			} else {
//				System.out.println("Found image with same name; skipping");
//
//				String foundChecksum = foundImage.getChecksum();
//				if (foundChecksum != null) {
//					if (checksum != null) {
//						if (!foundChecksum.equalsIgnoreCase(checksum)) {
//							System.err.println("Image checksums do not match.  Use '--force yes' to replace.");
//						} else {
//							System.out.println("Checksums match");
//						}
//					} else {
//						System.err.println("Checksum not stored in properties; cannot validate");
//					}
//				} else {
//					System.err.println("Glance does not have image checksum; cannot validate");
//				}
//
//				return null;
//			}
//		}
//
//		System.out.println("Downloading from: " + imageUrl + " and uploading as " + name);
//
//		InputStream imageStream = imageUrl.openStream();
//
//		if (expand != null) {
//			if (expand.equals("gzip")) {
//				imageStream = new GZIPInputStream(imageStream);
//			} else {
//				throw new CliException("Image specifies unsupported expansion mode: " + expand);
//			}
//		}
//
//		if (imageSize == -1) {
//			System.err.println("Warning: image size not supplied; will buffer entire image in memory");
//			System.err.flush();
//		}
//
//		ProgressListenerPeriodicPrint listener = null; // new ProgressListenerPeriodicPrint();
//		ProgressReportInputStream progressInputStream = new ProgressReportInputStream(imageStream, listener);
//
//		Image image = imageClient.root().images().addImage(progressInputStream, imageSize, imageTemplate);
//
//		getCache().invalidateCache(Image.class);
//
//		return image;
//	}
//
//	private String extractName(URL imageUrl) {
//		String name = imageUrl.getPath();
//		int lastSlash = name.lastIndexOf('/');
//		name = name.substring(lastSlash + 1);
//
//		int firstDot = name.indexOf('.');
//		if (firstDot != -1) {
//			name = name.substring(0, firstDot);
//		}
//		return name;
//	}
//
// }
