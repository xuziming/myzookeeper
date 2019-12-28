/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.zookeeper.graph;

import org.apache.zookeeper.graph.servlets.FileLoader;
import org.apache.zookeeper.graph.servlets.Fs;
import org.apache.zookeeper.graph.servlets.GraphData;
import org.apache.zookeeper.graph.servlets.NumEvents;
import org.apache.zookeeper.graph.servlets.StaticContent;
import org.apache.zookeeper.graph.servlets.Throughput;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class LogServer extends ServletContextHandler {

	public LogServer(MergedLogSource src) throws Exception {
		super(ServletContextHandler.SESSIONS);
		setContextPath("/");

		addServlet(new ServletHolder(new StaticContent()), "/graph/*");

		addServlet(new ServletHolder(new Fs()), "/fs");
		addServlet(new ServletHolder(new GraphData(src)), "/data");
		addServlet(new ServletHolder(new FileLoader(src)), "/loadfile");
		addServlet(new ServletHolder(new NumEvents(src)), "/info");
		addServlet(new ServletHolder(new Throughput(src)), "/throughput");
	}

	public static void main(String[] args) {
		try {
			MergedLogSource src = new MergedLogSource(args);
			System.out.println(src);

			Server server = new Server(8182);
			server.setHandler(new LogServer(src));

			server.start();
			server.join();

		} catch (Exception e) {
			// Something is wrong.
			e.printStackTrace();
		}
	}

}