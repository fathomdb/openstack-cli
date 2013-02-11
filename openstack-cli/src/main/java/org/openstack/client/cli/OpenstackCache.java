package org.openstack.client.cli;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OpenstackCache {

	private final OpenstackService service;

	public OpenstackCache(OpenstackService service) {
		this.service = service;
	}

	public Iterable<org.openstack.nova.model.Image> getImages(boolean useCache) {
		return getCachedList(org.openstack.nova.model.Image.class, useCache);
	}

	public <V> Iterable<V> listItems(Class<V> modelClass, boolean useCache) {
		return getCachedList(modelClass, useCache);
	}

	final Map<Class<?>, List<?>> cachedLists = Maps.newHashMap();

	private <V> Iterable<V> getCachedList(Class<V> modelClass, boolean useCache) {
		List<V> cached = useCache ? (List<V>) cachedLists.get(modelClass)
				: null;
		if (cached == null) {
			cached = Lists.newArrayList(service.listItems(modelClass, true));
			cachedLists.put(modelClass, cached);
		}
		return cached;
	}

	public Iterable<org.openstack.glance.model.Image> getGlanceImages(
			boolean useCache) {
		return getCachedList(org.openstack.glance.model.Image.class, useCache);
	}

	public void invalidateCache(Class<?> modelClass) {
		cachedLists.remove(modelClass);
	}

	public Iterable<org.openstack.nova.model.SecurityGroup> getSecurityGroups(
			boolean useCache) {
		return getCachedList(org.openstack.nova.model.SecurityGroup.class,
				useCache);
	}

	public void invalidateAll() {
		cachedLists.clear();
	}

}
