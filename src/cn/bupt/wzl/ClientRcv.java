package cn.bupt.wzl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientRcv extends Thread{
	protected DatagramSocket socket = null;
	
	public ClientRcv() throws IOException{
		this("ClientRcv");
	}
	public ClientRcv(String name) throws IOException{
		super(name);
	}
	
	
	@Override
	public void run(){
		String msg;
		DatagramPacket packet;
		
		try {
			socket = new DatagramSocket(4444);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		while(true) {
			
			byte[] buf = new byte[1024];
			packet= new DatagramPacket(buf, buf.length);			
			msg = new String(packet.getData());
			System.out.println(msg);
			String a = msg.substring(0,6);
			if(a.equals("##UPD:")) {
				a = msg.substring(msg.indexOf("##UPD:")+6,msg.indexOf("UPD!!"));
				int index = new Integer(a);
				System.out.println(index);
				GV.defaultListModel.add(index,msg.substring(msg.indexOf("##MNM:")+6,msg.indexOf("MNM!!")));

			}else {
				int i =new Integer(msg.substring(msg.indexOf("##IDX:")+6, msg.indexOf("IDX!!")));
				GV.textArea[1].append("\n" + msg.substring(msg.indexOf("IDX!!") + 5));
				
			}
			
		}
	}

	
}
