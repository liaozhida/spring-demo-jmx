package com.example.jmx.mbean;

import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@ManagedResource
public class ServerManager {

	final org.slf4j.Logger logger = LoggerFactory.getLogger(ServerManager.class);

	private String serverName = "springServer";
	private boolean serverRunning = true;
	private int minPoolSize = 5;
	private int maxPoolSize = 10;

	/**
	 * Gets server name.
	 */
	@ManagedAttribute(description = "The server name.")
	public String getServerName() {
		return serverName;
	}

	/**
	 * Whether or not the server is running.
	 */
	@ManagedAttribute(description = "Server's running status.")
	public boolean isServerRunning() {
		return serverRunning;
	}

	/**
	 * Sets whether or not the server is running.
	 */
	@ManagedAttribute(description = "Whether or not the server is running.", currencyTimeLimit = 20, persistPolicy = "OnUpdate")
	public void setServerRunning(boolean serverRunning) {
		this.serverRunning = serverRunning;
	}

	/**
	 * Change db connection pool size.
	 * 
	 * @param min
	 *            Minimum pool size.
	 * @param max
	 *            Maximum pool size.
	 * 
	 * @return int Current pool size.
	 */
	@ManagedOperation(description = "Change db connection pool size.")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "min", description = "Minimum pool size."),
			@ManagedOperationParameter(name = "max", description = "Maximum pool size.") })
	public int changeConnectionPoolSize(Integer minPoolSize, Integer maxPoolSize) {

		System.out.println(this.minPoolSize + "- 连接池属性 -" + this.maxPoolSize);

		Assert.isTrue((minPoolSize > 0), "Minimum connection pool size must be larger than zero.  min=" + minPoolSize);
		Assert.isTrue((minPoolSize < maxPoolSize), "Minimum connection pool size must be smaller than the maximum."
				+ "  min=" + minPoolSize + ", max=" + maxPoolSize);

		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;

		int diff = (maxPoolSize - minPoolSize);

		// randomly generate current pool size between new min and max
		Random rnd = new Random();
		int currentSize = (minPoolSize + rnd.nextInt(diff));

		System.out.println(this.minPoolSize + "- 连接池属性 -" + this.maxPoolSize);

		return currentSize;

	}

}