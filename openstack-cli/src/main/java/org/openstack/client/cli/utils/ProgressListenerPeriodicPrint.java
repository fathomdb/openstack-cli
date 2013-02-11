package org.openstack.client.cli.utils;

public class ProgressListenerPeriodicPrint extends ProgressListener {

	long total;
	int interval = 1024 * 1024;

	@Override
	public void notify(int count) {
		long oldBucket = total / interval;

		total += count;

		long newBucket = total / interval;

		if (oldBucket != newBucket) {
			System.out.println("Position: " + total);
		}
	}

}
