package com.example.springjmx;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JmxApplicationTests {

	private final static String url = "service:jmx:jmxmp://localhost:9875";

	@Autowired
	private MBeanServerConnection clientConnector = null;

	@Autowired
	private JmxClient jmxClient;

	@Test
	public void testObjects() throws IOException {
		jmxClient.objectNames(url);
	}

	@Test
	public void testClient() throws InterruptedException {

		Thread.sleep(2000);

		jmxClient.resetServer(url);
		System.out.println("重置后的计数: ");

		Thread.sleep(2000);
	}

}
