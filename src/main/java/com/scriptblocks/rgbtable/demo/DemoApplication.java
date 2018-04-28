package com.scriptblocks.rgbtable.demo;

import com.scriptblocks.rgbtable.demo.pi.table.TableSPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
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
				TableSPI.getInstance().setIpAddress(ipAddress);
                System.out.println("-------------------IP ADDRESS------------------- : " + ipAddress);
			}
		} catch (SocketException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


	}
}
