package org.openstack.client.cli.model;

import java.util.List;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.autocomplete.ImageNameAutoCompleter;
import org.openstack.nova.model.Image;

import com.fathomdb.cli.StringWrapper;
import com.fathomdb.cli.autocomplete.HasAutoCompletor;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@HasAutoCompletor(ImageNameAutoCompleter.class)
public class ImageName extends StringWrapper {
	public ImageName(String key) {
		super(key);
	}

	public Image findImage(OpenstackCliContext context) {
		List<Image> matches = Lists.newArrayList();
		for (Image image : context.getCache().getImages(true)) {
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
