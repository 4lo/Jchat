package cn.bupt.wzl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    protected DatagramSocket socket = null;
    private int userNumber = 0;
    
    
    
    
    
	public void main(String[] args) throws IOException {
		socket = new DatagramSocket(4869); 
		String msg = null;
		while(true) {
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			InetAddress address = packet.getAddress();
			msg = new String(packet.getData());
			
		}
	}

}
