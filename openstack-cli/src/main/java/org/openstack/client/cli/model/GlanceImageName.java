package org.openstack.client.cli.model;

import java.util.List;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.autocomplete.GlanceImageNameAutoCompleter;
import org.openstack.glance.model.Image;

import com.fathomdb.cli.StringWrapper;
import com.fathomdb.cli.autocomplete.HasAutoCompletor;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@HasAutoCompletor(GlanceImageNameAutoCompleter.class)
public class GlanceImageName extends StringWrapper {
	public GlanceImageName(String key) {
		super(key);
	}

	public Image resolve(OpenstackCliContext context) {
		List<Image> matches = Lists.newArrayList();
		for (Image image : context.getCache().getGlanceImages(true)) {
			if (Objects.equal(image.getName(), getKey())) {
				matches.add(image);
			} else if (Objects.equal(image.getId(), getKey())) {
				matches.add(image);
			}
		}

		if (matches.size() == 0) {
			return null;
		}

		if (matches.size() != 1) {
			throw new IllegalArgumentException("Image name is ambiguous");
		}

		return matches.get(0);
	}
}
