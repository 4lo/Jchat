package cn.bupt.wzl;

import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import cn.bupt.wzl.*;

public class ClientRcv extends Thread{
	protected DatagramSocket socket = null;
	
	public ClientRcv() throws IOException{
		this("ClientRcv");
	}
	public ClientRcv(String name) throws IOException{
		super(name);
		socket = new DatagramSocket(4444);
	}
	
	
	@Override
	public void run(){
		String msg;
		DatagramPacket packet;
		byte[] buf;
		try {
			while(true) {
				buf = new byte[1024];
				packet= new DatagramPacket(buf, buf.length);
				
				socket.receive(packet);

				msg = new String(packet.getData());
				int i =new Integer(msg.substring(msg.indexOf("##IDX:")+6, msg.indexOf("IDX!!")));
				GV.textArea[i].append("\n" + msg.substring(msg.indexOf("IDX!!") + 5));
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	
}
