package org.openstack.client.cli;

public interface SessionCache {

	OpenstackSession get(OpenstackSessionInfo key);

}
