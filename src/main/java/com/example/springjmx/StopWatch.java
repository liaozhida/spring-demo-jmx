package com.example.springjmx;


public interface StopWatch {

	public abstract int getSeconds();

	public abstract void start();

	public abstract void stop();

	public abstract void reset();

}