package com.example.rmi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;


@Configuration
@PropertySource("classpath:rmi.properties")
public class RmiConfig {

	@Value("${rmi.host}")
	private String rmiHost ;

	@Value("${rmi.port}")
	private Integer rmiPort ;

	@Bean
	public RmiRegistryFactoryBean rmiRegistry() {
		
		System.setProperty("java.rmi.server.hostname",rmiHost);
		
		final RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
		rmiRegistryFactoryBean.setPort(rmiPort);
		rmiRegistryFactoryBean.setAlwaysCreate(true);
		return rmiRegistryFactoryBean;
	}

	@Bean
	@DependsOn("rmiRegistry")
	public ConnectorServerFactoryBean connectorServerFactoryBean() throws Exception {
		
		
		final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
		connectorServerFactoryBean.setObjectName("connector:name=rmi");
		connectorServerFactoryBean.setServiceUrl(String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi",
				rmiHost, rmiPort, rmiHost, rmiPort));

		System.out.println("JMX url is : "+String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi", rmiHost, rmiPort, rmiHost,
				rmiPort));

		return connectorServerFactoryBean;
	}
}
