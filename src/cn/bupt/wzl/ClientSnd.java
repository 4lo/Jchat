package cn.bupt.wzl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSnd{
	private String serverAddress = "localhost";
	private String myName;
	private int receiver;
	private DatagramSocket socket;
	private String msg;
	
	public ClientSnd() {
	}
	public ClientSnd(String myName, int receiver,String msg) throws IOException{
		this.myName = myName;
		this.receiver = receiver;
		this.msg = msg;
	}
	
	public void send() throws IOException {
		socket = new DatagramSocket();
		byte[] buf = new byte[1024];
		InetAddress address = InetAddress.getByName(serverAddress);
		

		msg = "##RCV:" + receiver + "##MNM:" + myName + msg;
		buf = msg.getBytes();
		
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address, 4869);
        socket.send(packet);	
	}
}
