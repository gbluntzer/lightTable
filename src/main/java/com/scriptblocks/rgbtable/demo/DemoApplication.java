package com.scriptblocks.rgbtable.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@SpringBootApplication
public class DemoApplication {


	public DemoApplication() {
		init();
	}

	public void init(){
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(networkInterfaces.hasMoreElements()){
				NetworkInterface ni = networkInterfaces.nextElement();
				InetAddress inetAddress = ni.getInetAddresses().nextElement();
				String ipAddress = inetAddress.getHostAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();

		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


	}
}
