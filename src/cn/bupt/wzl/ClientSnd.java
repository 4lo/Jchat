package cn.bupt.wzl;

import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientSnd{
	private String serverAddress = "10.126.243.190";
	private String myName;
	private String receiver;
	private DatagramSocket socket;
	private String msg;
	private String cmd;
	public ClientSnd() {
	}
	public ClientSnd(String myName, String receiver,String msg) throws IOException{
		this.myName = myName;
		this.receiver = receiver;
		this.msg = msg;
	}
	
	public ClientSnd(String myName,String password) throws IOException{
		this.myName = myName;
		this.msg = password;
	}
	
	
	public void send() throws IOException {
		socket = new DatagramSocket();
		byte[] buf = new byte[1024];
		InetAddress address = InetAddress.getByName(serverAddress);
		if(receiver == "GROUP") {
			cmd = "GCHAT";
		}else {
			cmd = "ACHAT";
		}

		msg = "##CMD:" + cmd + "CMD!!" +"##RCV:" + receiver + "RCV!!##MNM:" + myName + "MNM!!##MSG:" + msg + "MSG!!";
		//System.out.println(msg);
		buf = msg.getBytes();
		
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address, 4869);
        socket.send(packet);	
        socket.close();
	}
	public void logIn() throws IOException {
		socket = new DatagramSocket();
		byte[] buf = new byte[1024];
		InetAddress address = InetAddress.getByName(serverAddress);
		cmd = "LOGIN";
		msg = "##CMD:" + cmd + "CMD!!" +"##RCV:" + receiver + "RCV!!##MNM:" + myName + "MNM!!##MSG:" + msg + "MSG!!";
		buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address, 4869);
        socket.send(packet);	
        socket.close();
	}
	public void register() throws IOException {
		socket = new DatagramSocket();
		byte[] buf = new byte[1024];
		InetAddress address = InetAddress.getByName(serverAddress);
		cmd = "REGISTER";
		msg = "##CMD:" + cmd + "CMD!!" +"##RCV:" + receiver + "RCV!!##MNM:" + myName + "MNM!!##MSG:" + msg + "MSG!!";
		buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address, 4869);
        socket.send(packet);	
        socket.close();
	}
}
