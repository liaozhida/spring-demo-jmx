package com.example.jmxmp;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanServerConnection;
import javax.management.ReflectionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.example.jmxmp.JmxServer.class)
public class JmxApplicationTests {

	private final static String url = "service:jmx:jmxmp://localhost:9875";
	private final static String NAME_STOPWATCH = "com.example.jmxmp.mbean:name=StopWatch";
	private final static String NAME_SERVERMANAGER = "com.example.jmxmp.mbean:name=serverManager,type=ServerManager";

	@Autowired
	private MBeanServerConnection clientConnector = null;

	@Autowired
	private JmxClient jmxClient;

	@Test
	public void printObjects() throws IOException {
		jmxClient.objectNames(url);
	}

	@Test
	public void printMeta() throws InstanceNotFoundException, IntrospectionException, MalformedURLException,
			ReflectionException, IOException {
		jmxClient.objectMeta(url, NAME_STOPWATCH);
	}

	@Test
	public void testStopWatch() throws InterruptedException {

		Thread.sleep(2000);

		jmxClient.triggerStopWatch(url, NAME_STOPWATCH);
		System.out.println("重置后的计数: ");

		Thread.sleep(2000);
	}

	@Test
	public void testServerManager() throws InterruptedException, IntrospectionException {
		jmxClient.triggerServerManager(url, NAME_SERVERMANAGER);

	}

}
