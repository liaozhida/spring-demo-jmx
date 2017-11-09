package com.example.springjmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.stereotype.Component;

@Component
public class JmxClient {

	public void resetServer(String url) {
		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));
			MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

			if (mbsc != null) {
				System.out.println("客户端访问 JMX 成功");
				try {
					ObjectName objectName = new ObjectName("com.example.springjmx:name=StopWatch");
					jmxConnector.getMBeanServerConnection().invoke(objectName, "reset", null, null);

				} catch (InstanceNotFoundException | MalformedObjectNameException | ReflectionException
						| MBeanException e) {
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void objectNames(String url) {

		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));

			MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

			Set<ObjectName> objectNames = connection.queryNames(null, null);
			for (ObjectName objectName : objectNames) {
				System.out.println("========" + objectName + "========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
