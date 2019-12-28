package org.apache.zookeeper.version;

public class VersionInfoMain implements org.apache.zookeeper.version.Info {

	public static void main(String[] args) {
		final String VER_STRING = MAJOR + "." + MINOR + "." + MICRO + 
			(QUALIFIER == null ? "" : "-" + QUALIFIER) + " " + BUILD_DATE;
		System.out.println("Apache ZooKeeper, version " + VER_STRING);
	}

}