package org.openstack.client.cli.model;

import java.util.List;

import org.openstack.client.cli.OpenstackCliContext;
import org.openstack.client.cli.autocomplete.SecurityGroupNameAutoCompleter;
import org.openstack.nova.model.SecurityGroup;

import com.fathomdb.cli.StringWrapper;
import com.fathomdb.cli.autocomplete.HasAutoCompletor;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@HasAutoCompletor(SecurityGroupNameAutoCompleter.class)
public class SecurityGroupName extends StringWrapper {

	public SecurityGroupName(String key) {
		super(key);
	}

	public SecurityGroup resolve(OpenstackCliContext context) {
		List<SecurityGroup> matches = Lists.newArrayList();
		for (SecurityGroup candidate : context.getCache().listItems(
				SecurityGroup.class, true)) {
			if (Objects.equal(candidate.getName(), getKey())) {
				matches.add(candidate);
			} else if (Objects.equal(candidate.getId(), getKey())) {
				matches.add(candidate);
			}
		}

		if (matches.size() == 0) {
			return null;
		}

		if (matches.size() != 1) {
			throw new IllegalArgumentException(
					"Security Group name is ambiguous");
		}

		return matches.get(0);
	}
}
