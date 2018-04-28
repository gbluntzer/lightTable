package com.scriptblocks.rgbtable.demo;

import com.scriptblocks.rgbtable.demo.pi.table.TableSPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.Inet4Address;
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
				String ipAddress = getCurrentIp().getHostAddress();
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

	public InetAddress getCurrentIp() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) networkInterfaces
						.nextElement();
				Enumeration<InetAddress> nias = ni.getInetAddresses();
				while(nias.hasMoreElements()) {
					InetAddress ia= (InetAddress) nias.nextElement();
					if (!ia.isLinkLocalAddress()
							&& !ia.isLoopbackAddress()
							&& ia instanceof Inet4Address) {
						return ia;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("unable to get current IP " + e.getMessage());
		}
		return null;
	}
}
