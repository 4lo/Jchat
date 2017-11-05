package cn.bupt.wzl;

import java.io.IOException;
import java.net.DatagramSocket;

public class ClientRcv extends Thread{
	protected DatagramSocket socket = null;
	
	
	
	
	public ClientRcv() throws IOException{
		this("ClientRcv");
	}
	public ClientRcv(String name) throws IOException{
		super(name);
		socket = new DatagramSocket(4869);
	}
	
	
	
	@Override
	public void run() {
		while(true) {
			
		}
	}

	
}
