package org.openstack.client.cli;

import java.util.Map;

import org.openstack.glance.GlanceClient;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ExtensionCore.ListExtensions;
import org.openstack.nova.api.FlavorsCore.ListFlavors;
import org.openstack.nova.api.ImagesCore;
import org.openstack.nova.api.ImagesCore.DeleteImage;
import org.openstack.nova.api.ImagesCore.ListImages;
import org.openstack.nova.api.ServersCore.ListServers;
import org.openstack.nova.api.extensions.FloatingIpsExtension;
import org.openstack.nova.api.extensions.KeyPairsExtension.ListKeyPairs;
import org.openstack.nova.api.extensions.SecurityGroupsExtension;
import org.openstack.nova.api.extensions.SecurityGroupsExtension.DeleteSecurityGroup;
import org.openstack.nova.api.extensions.SecurityGroupsExtension.ListSecurityGroups;
import org.openstack.nova.model.Extensions;
import org.openstack.nova.model.Flavor;
import org.openstack.nova.model.Flavors;
import org.openstack.nova.model.FloatingIps;
import org.openstack.nova.model.Images;
import org.openstack.nova.model.KeyPairs;
import org.openstack.nova.model.SecurityGroups;
import org.openstack.nova.model.Servers;
import org.openstack.swift.SwiftClient;

import com.google.common.collect.Maps;

public class OpenstackService {
	final OpenstackSession session;
	final Map<Object, Object> extensions = Maps.newHashMap();

	public OpenstackService(OpenstackSession session) {
		super();
		this.session = session;
	}

	public GlanceClient getGlanceClient() {
		return session.getGlanceClient();
	}

	public NovaClient getNovaClient() {
		return session.getNovaClient();
	}

	public SwiftClient getSwiftClient() {
		return session.getSwiftClient();
	}

	public Map<Object, Object> getExtensions() {
		return extensions;
	}

	public <T> Iterable<T> listItems(Class<T> itemClass, boolean details) {
		// TODO: Yuk...
		if (itemClass == org.openstack.nova.model.Server.class) {
			ListServers command = new ListServers();
			Servers ret = getNovaClient().execute(command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.nova.model.Image.class) {
			ListImages listImages = new ListImages(true);
			Images images = getNovaClient().execute(listImages);
			return (Iterable<T>) images.getList();
		}

		if (itemClass == org.openstack.nova.model.SecurityGroup.class) {
			ListSecurityGroups command = new ListSecurityGroups();
			SecurityGroups securityGroups = getNovaClient().execute(command);
			return (Iterable<T>) securityGroups.getList();
		}

		if (itemClass == org.openstack.nova.model.KeyPair.class) {
			ListKeyPairs command = new ListKeyPairs();
			KeyPairs ret = getNovaClient().execute(command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.nova.model.Flavor.class) {
			ListFlavors command = new ListFlavors();
			Flavors ret = getNovaClient().execute(command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.nova.model.Extension.class) {
			ListExtensions command = new ListExtensions();
			Extensions ret = getNovaClient().execute(command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.nova.model.FloatingIp.class) {
			FloatingIpsExtension.ListFloatingIps command = new FloatingIpsExtension.ListFloatingIps();
			FloatingIps ret = getNovaClient().execute(command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.glance.model.Image.class) {
			org.openstack.glance.api.ListImages command = new org.openstack.glance.api.ListImages();
			org.openstack.glance.model.Images ret = getGlanceClient().execute(
					command);
			return (Iterable<T>) ret.getList();
		}

		if (itemClass == org.openstack.keystone.model.Service.class) {
			return (Iterable<T>) session.getServiceCatalog();
		}

		throw new IllegalArgumentException("Unknown type: " + itemClass);
	}

	public <T> void delete(T item) {
		Class<? extends Object> itemClass = item.getClass();

		if (itemClass == org.openstack.nova.model.Image.class) {
			org.openstack.nova.model.Image image = (org.openstack.nova.model.Image) item;
			DeleteImage command = ImagesCore.deleteImage(image.getId());
			getNovaClient().execute(command);
		} else if (itemClass == org.openstack.glance.model.Image.class) {
			org.openstack.glance.model.Image image = (org.openstack.glance.model.Image) item;
			org.openstack.glance.api.DeleteImage command = new org.openstack.glance.api.DeleteImage(
					image.getId());
			getGlanceClient().execute(command);
		} else if (itemClass == org.openstack.nova.model.SecurityGroup.class) {
			org.openstack.nova.model.SecurityGroup securityGroup = (org.openstack.nova.model.SecurityGroup) item;
			DeleteSecurityGroup command = SecurityGroupsExtension
					.deleteSecurityGroup(securityGroup.getId());
			getNovaClient().execute(command);
		} else {
			throw new IllegalArgumentException("Unknown type: " + itemClass);
		}
	}

	public Flavor resolveFlavor(Flavor flavor) {
		// TODO Auto-generated method stub
		return null;
	}
}
