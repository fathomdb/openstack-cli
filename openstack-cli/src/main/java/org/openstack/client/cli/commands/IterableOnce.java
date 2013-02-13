package org.openstack.client.cli.commands;

import java.util.Iterator;

public class IterableOnce<T> implements Iterable<T> {
	Iterator<T> iterator;

	public IterableOnce(Iterator<T> iterator) {
		super();
		this.iterator = iterator;
	}

	@Override
	public Iterator<T> iterator() {
		if (iterator == null) {
			throw new IllegalStateException(
					"Already iterated a one-pass iterable");
		}
		Iterator<T> ret = iterator;
		iterator = null;
		return ret;
	}

	public static <T> IterableOnce<T> build(Iterator<T> iterator) {
		return new IterableOnce<T>(iterator);
	}

}
