package cn.bupt.wzl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;

public class Server {
	private DatagramSocket socket = null; 
	private InetAddress clientAddress = null;
	private String data = null;
    private int userNumber = 0;
    private int port = 0;
    private String receiver;
    private String msg;
    private String clientName;
    private String cmd;
    private DatagramPacket packet;
    public HashMap<String, User> user = new HashMap<String,User>();
    public HashMap<Integer, String> userIndex = new HashMap<Integer,String>();
    
	public static void main(String[] args) throws IOException{
		new Server();
	}
	public Server() throws IOException,IndexOutOfBoundsException {
		socket = new DatagramSocket(4869); 
		User tmp1 = new User();
		tmp1.address = "localhost";
		tmp1.password = "123";
		tmp1.isOnline = false;
		tmp1.index = 1;
		user.put("wzl",tmp1);
		userIndex.put(1, "wzl");
		while(true) {
			byte[] rcvBuf = new byte[1024];
			byte[] sndBuf = new byte[1024];
			String sndStr = new String();	
			
			packet = new DatagramPacket(rcvBuf, rcvBuf.length);
			socket.receive(packet);
			
			clientAddress = packet.getAddress();
			port = 4444;//packet.getPort();
			data = new String(packet.getData());

			cmd = data.substring(data.indexOf("##CMD:")+6 ,data.indexOf("CMD!!"));  
			msg = data.substring(data.indexOf("##MSG:") + 6, data.indexOf("MSG!!"));
			receiver = data.substring(data.indexOf("##RCV:") + 6, data.indexOf("RCV!!"));
			clientName = data.substring(data.indexOf("##MNM:") + 6, data.indexOf("MNM!!"));
			
			

			
		//注册
			if(cmd.equals("REGISTER")) {
				for(int i = 1;i<=userNumber;i++) {
					sndStr = "##UPD:" + i + "UPD!!" + "##MNM:"+userIndex.get(i)+"MNM!!";
					sndBuf = sndStr.getBytes();
					packet = new DatagramPacket(sndBuf, sndBuf.length,clientAddress,port);
					socket.send(packet);
				}
				userNumber++;

				userIndex.put(userNumber,clientName);
				
				User tmp = new User();
				tmp.address = clientAddress.toString().substring(1);
				tmp.password = msg;
				tmp.isOnline = false;
				tmp.index = userNumber;
				user.put(clientName,tmp);

				for(int i= 1;i<=userNumber;i++) {
					sndStr = "##UPD:" + userNumber + "UPD!!" + "##MNM:"+ userIndex.get(userNumber)+"MNM!!";
					sndBuf = sndStr.getBytes();
					InetAddress address = InetAddress.getByName(user.get(userIndex.get(i)).address);
					packet = new DatagramPacket(sndBuf, sndBuf.length,address,port);
					socket.send(packet);
				}

				
			}
			
			
		//登陆
			if(cmd.equals("LOGIN")) {
				for(String key:user.keySet()) {
					if(clientName.equals(key)&&user.get(key).password.equals(msg)){
						user.get(key).isOnline = true;
						user.get(key).address = clientAddress.toString();
						//System.out.println(user.get(key).isOnline + "   " + user.get(key).address + "    " + user.get(key).index);
						//并发给用户消息
						
						
					}
				}
			}
			
			
			
		//一对一聊天
			if(cmd.equals("ACHAT")) {
				//发给 发送端用户 以显示在其对应聊天框中
				sndStr = user.get(receiver).index + "";
				sndStr = "##IDX:" + sndStr + "IDX!!" + clientName + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,clientAddress,port);
				socket.send(packet);
				//发给接受用户
				System.out.println(clientAddress.toString());
				InetAddress address = InetAddress.getByName("localhost");
				sndStr = "##IDX:" + user.get(clientName).index + "IDX!!" + clientName + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,address,port);
				socket.send(packet);
			}
			
			
			
		//群聊
			if(cmd.equals("GCHAT")) {
				//发给 发送端用户 以显示在其对应聊天框中
				//sndStr = "##IDX:" + user.get(receiver)[3] + "IIDX!!" + clientName + (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,clientAddress,port);
				socket.send(packet);
				//发给接受用户
				//未完待续
				InetAddress address = InetAddress.getByName("localhost");//需要改
				sndStr = "##IDX:" + clientName + "IDX!!" + clientName + (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,address,port);
				socket.send(packet);
			}
		
		}
	}

}
