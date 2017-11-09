package com.example.jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
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

	public void triggerStopWatch(String url, String objectNameStr) {
		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));
			MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

			if (mbsc == null) {
				return;
			}
			System.out.println("客户端访问 JMX 成功 ...");
			try {
				ObjectName objectName = new ObjectName(objectNameStr);
				jmxConnector.getMBeanServerConnection().invoke(objectName, "reset", null, null);

			} catch (InstanceNotFoundException | MalformedObjectNameException | ReflectionException | MBeanException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void triggerServerManager(String url, String objectNameStr) throws IntrospectionException {

		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));
			MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

			if (mbsc == null) {
				return;
			}
			System.out.println("客户端访问 JMX 成功 ...");
			try {
				ObjectName objectName = new ObjectName(objectNameStr);
				MBeanInfo mbean = jmxConnector.getMBeanServerConnection().getMBeanInfo(objectName);
				// 输出
				for (MBeanAttributeInfo attr : mbean.getAttributes()) {
					Object value = null;
					try {
						value = attr.isReadable() ? mbsc.getAttribute(objectName, attr.getName()) : "";
					} catch (Exception e) {
						value = e.getMessage();
					}
					System.out.println(attr.getName() + ":" + value);
				}
				// 调用
				jmxConnector.getMBeanServerConnection().invoke(objectName, "setServerRunning", new Object[] { false },
						new String[] { boolean.class.getName() });
				jmxConnector.getMBeanServerConnection().invoke(objectName, "changeConnectionPoolSize",
						new Object[] { 1, 2 }, new String[] { Integer.class.getName(), Integer.class.getName() });
				
				// 输出
				for (MBeanAttributeInfo attr : mbean.getAttributes()) {
					Object value = null;
					try {
						value = attr.isReadable() ? mbsc.getAttribute(objectName, attr.getName()) : "";
					} catch (Exception e) {
						value = e.getMessage();
					}
					System.out.println(attr.getName() + ":" + value);
				}

			} catch (InstanceNotFoundException | MalformedObjectNameException | ReflectionException | MBeanException e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 遍历所有的MBean
	 * 
	 * @param url
	 */
	public void objectNames(String url) {

		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));

			MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

			Set<ObjectName> objectNames = connection.queryNames(null, null);
			System.out.println("开始遍历所有MBean:");
			for (ObjectName objectName : objectNames) {
				System.out.println("========" + objectName + "========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查看指定 MBean的属性
	 * 
	 * @param url
	 * @param objectNameStr
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InstanceNotFoundException
	 * @throws IntrospectionException
	 * @throws ReflectionException
	 */
	public void objectMeta(String url, String objectNameStr) throws MalformedURLException, IOException,
			InstanceNotFoundException, IntrospectionException, ReflectionException {

		JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(url));

		MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
		Set<ObjectName> objectNames = connection.queryNames(null, null);
		for (ObjectName objectName : objectNames) {
			if (!objectName.toString().equals(objectNameStr)) {
				continue;
			}
			MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
			System.out.println("[Attributes]");
			for (MBeanAttributeInfo attr : mBeanInfo.getAttributes()) {
				Object value = null;
				try {
					value = attr.isReadable() ? connection.getAttribute(objectName, attr.getName()) : "";
				} catch (Exception e) {
					value = e.getMessage();
				}
				System.out.println(attr.getName() + ":" + value);
			}
			System.out.println("[Operations]");
			for (MBeanOperationInfo oper : mBeanInfo.getOperations()) {
				System.out.println(oper.getName() + ":" + oper.getDescription());
			}
			System.out.println("[Notifications]");
			for (MBeanNotificationInfo notice : mBeanInfo.getNotifications()) {
				System.out.println(notice.getName() + ":" + notice.getDescription());
			}
		}

	}

}
