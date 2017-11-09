package com.example.springjmx;

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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CopyOfJMXApplicationTests {

//	@Test
	public void quert() throws MalformedURLException, IOException, InstanceNotFoundException, IntrospectionException,
			ReflectionException {

		JMXConnector jmxConnector = JMXConnectorFactory
				.connect(new JMXServiceURL("service:jmx:jmxmp://localhost:9875"));

		MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

		Set<ObjectName> objectNames = connection.queryNames(null, null);
		for (ObjectName objectName : objectNames) {
			System.out.println("========" + objectName + "========");
			MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
			// System.out.println("[Attributes]");
			for (MBeanAttributeInfo attr : mBeanInfo.getAttributes()) {
				Object value = null;
				try {
					value = attr.isReadable() ? connection.getAttribute(objectName, attr.getName()) : "";
				} catch (Exception e) {
					value = e.getMessage();
				}
				// System.out.println(attr.getName() + ":" + value);
			}
			// System.out.println("[Operations]");
			for (MBeanOperationInfo oper : mBeanInfo.getOperations()) {
				// System.out.println(oper.getName() + ":" +
				// oper.getDescription());
			}
			System.out.println("[Notifications]");
			for (MBeanNotificationInfo notice : mBeanInfo.getNotifications()) {
				// System.out.println(notice.getName() + ":" +
				// notice.getDescription());
			}
		}
	}

	@Test
	public void testClient() {

		try {
			JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(
					"service:jmx:jmxmp://localhost:9875"));
			MBeanServerConnection mbsc = jmxConnector.getMBeanServerConnection();

			if (mbsc != null) {
				System.out.println("客户端访问 JMX 成功 ， 开始重置计数器");
				ObjectName objectName = new ObjectName("com.example.springjmx:name=stopWatchDirect,type=StopWatchDirect");

				// 管理资源
				try {
					jmxConnector.getMBeanServerConnection().invoke(objectName, "reset", null, null);
				} catch (InstanceNotFoundException e) {
					// TODO 自动生成的 catch 块
					System.out.println("cao");
					e.printStackTrace();
				}

				// 遍历资源属性
//				MBeanInfo sw = jmxConnector.getMBeanServerConnection().getMBeanInfo(objectName);
//				MBeanAttributeInfo[] mas = sw.getAttributes();
//				for (MBeanAttributeInfo mBeanAttributeInfo : mas) {
//					System.out.println("" + mBeanAttributeInfo.getName());
//				}

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
