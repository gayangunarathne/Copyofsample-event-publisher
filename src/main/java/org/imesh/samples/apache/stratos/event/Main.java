/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.imesh.samples.apache.stratos.event;

import java.net.URL;

import org.apache.stratos.messaging.event.Event;
import org.apache.stratos.messaging.listener.instance.notifier.ArtifactUpdateEventListener;
import org.apache.stratos.messaging.listener.instance.notifier.InstanceCleanupClusterEventListener;
import org.apache.stratos.messaging.listener.instance.notifier.InstanceCleanupMemberEventListener;
import org.apache.stratos.messaging.message.receiver.instance.notifier.InstanceNotifierEventReceiver;
import org.imesh.samples.apache.stratos.event.generator.TopologyEventGenerator;

/**
 * Run this main class to send a set of sample topology events.
 */
public class Main {

	public static void main(String[] args) {

		URL path = Main.class.getResource("/");
		System.setProperty("jndi.properties.dir", path.getFile());

		TopologyEventGenerator topologyEventGenerator = new TopologyEventGenerator(1);
		Thread generatorThread = new Thread(topologyEventGenerator);
		generatorThread.start();

		// subscribeToTopicsAndRegisterListeners();
		// TopicSubscriber topicSub = new TopicSubscriber("topology/#");
		// Thread subsThread = new Thread(topicSub);
		// subsThread.start();
	}

	private static void subscribeToTopicsAndRegisterListeners() {

		InstanceNotifierEventReceiver instanceNotifierEventReceiver =
		                                                              new InstanceNotifierEventReceiver();
		instanceNotifierEventReceiver.addEventListener(new ArtifactUpdateEventListener() {
			@Override
			protected void onEvent(Event event) {
				try {
					System.out.println("message received.........");
				} catch (Exception e) {

				}
			}
		});

		instanceNotifierEventReceiver.addEventListener(new InstanceCleanupMemberEventListener() {
			@Override
			protected void onEvent(Event event) {
				try {
					System.out.println("message received.........");
				} catch (Exception e) {

				}

			}
		});

		instanceNotifierEventReceiver.addEventListener(new InstanceCleanupClusterEventListener() {
			@Override
			protected void onEvent(Event event) {
				System.out.println("message received.........");
			}
		});
		Thread eventReceiverThread = new Thread(instanceNotifierEventReceiver);
		eventReceiverThread.start();
	}

}
