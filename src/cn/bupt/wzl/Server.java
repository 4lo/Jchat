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
		while(true) {
			byte[] rcvBuf = new byte[1024];
			byte[] sndBuf = new byte[1024];
			String sndStr = new String();	
			new Date().toString();
			
			packet = new DatagramPacket(rcvBuf, rcvBuf.length);
			socket.receive(packet);
			
			clientAddress = packet.getAddress();
			port = 4444;//packet.getPort();
			data = new String(packet.getData());

			cmd = data.substring(data.indexOf("##CMD:")+6 ,data.indexOf("CMD!!"));  
			msg = data.substring(data.indexOf("##MSG:") + 6, data.indexOf("MSG!!"));
			receiver = data.substring(data.indexOf("##RCV:") + 6, data.indexOf("RCV!!"));
			clientName = data.substring(data.indexOf("##MNM:") + 6, data.indexOf("MNM!!"));
			
		//ע��
			if(cmd.equals("REGISTER")) {
				userNumber++;
				//user[userNumber].userName = msg;//password;
				//user[userNumber].address = clientAddress.toString();
				//user[userNumber].isOnline = false;
				//user[userNumber].index = userNumber;
				userIndex.put(userNumber,clientName);
				
				User tmp = new User();
				tmp.address = clientAddress.toString();
				tmp.password = msg;
				tmp.isOnline = false;
				tmp.index = userNumber;
				user.put(clientName,tmp);
				//System.out.println(user.get(clientName).address + user.get(clientName).password + user.get(clientName).isOnline);
			}
			
			
		//��½
			if(cmd.equals("LOGIN")) {
				for(String key:user.keySet()) {
					if(clientName.equals(key)&&user.get(key).password.equals(msg)){
						user.get(key).isOnline = true;
						user.get(key).address = clientAddress.toString();
						//System.out.println(user.get(key).isOnline + "   " + user.get(key).address + "    " + user.get(key).index);
						//�������û���Ϣ
						
						
					}
				}
			}
			
			
			
		//һ��һ����
			if(cmd.equals("ACHAT")) {
				//���� ���Ͷ��û� ����ʾ�����Ӧ�������
				sndStr = "##IDX:" + user.get(receiver).index + "IDX!!" + clientName +"ls"+ (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,clientAddress,port);
				socket.send(packet);
				//���������û�
				System.out.println(clientAddress.toString());
				InetAddress address = InetAddress.getByName("localhost");
				sndStr = "##IDX:" + user.get(clientName).index + "IDX!!" + clientName + (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,address,port);
				socket.send(packet);
			}
			
			
			
		//Ⱥ��
			if(cmd.equals("GCHAT")) {
				//���� ���Ͷ��û� ����ʾ�����Ӧ�������
				//sndStr = "##IDX:" + user.get(receiver)[3] + "IIDX!!" + clientName + (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,clientAddress,port);
				socket.send(packet);
				//���������û�
				//δ�����
				InetAddress address = InetAddress.getByName("localhost");//��Ҫ��
				sndStr = "##IDX:" + clientName + "IDX!!" + clientName + (new Date().toString()) + "\n" + msg;
				sndBuf = sndStr.getBytes();
				packet = new DatagramPacket(sndBuf, sndBuf.length,address,port);
				socket.send(packet);
			}
		
		}
	}

}
