package com.java.maven.test.SpringTest1;


import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
/**
 * Hello world!
 *
 */
//java -cp Spring_jar2.jar:./Spring_jar2_lib/* com.java.maven.test.SpringTest1.App
	

	@ComponentScan
	@EnableAutoConfiguration
	public class App {

	    public static void main(String[] args) {
	    	String log4jConfPath = "/home/dren/Senior_D/log4j.properties";
	    	PropertyConfigurator.configure(log4jConfPath);
	        SpringApplication.run(App.class, args);
	    }

	}

