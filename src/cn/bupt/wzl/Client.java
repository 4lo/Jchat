package cn.bupt.wzl;

import cn.bupt.wzl.GV;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.CardLayout;

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPasswordField;

public class Client {

	private JFrame frame;
	private JPanel panel_text;
	private JSeparator separator;
	private JScrollPane scrollPane_type;
	private JTextArea typeArea;
	private JScrollPane scrollPane_text;


	private JButton sendButton;
	private JButton loginButton;
	private JTextArea userNameArea;
	private CardLayout cl_panel_text;
	private Boolean ctrIsPressed = false;
	private Boolean loginButtonIsPressed = false;
	private JPasswordField passwordField;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
					new ClientRcv().start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Client() throws IOException {
		initialize();
		
	}

//发送消息
	private void send() throws IOException {
		
		String msg = new String(typeArea.getText());
		ClientSnd a = new ClientSnd(userNameArea.getText(),GV.list.getSelectedValue().toString(),msg);
		a.send();
		typeArea.setText("");
	}
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 954, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	//分隔线
		separator = new JSeparator();
		separator.setBounds(156, 366, 793, 9);
		frame.getContentPane().add(separator);
		
		
    //输入区域的滚动轴
		scrollPane_type = new JScrollPane();
		scrollPane_type.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_type.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_type.setBounds(160, 376, 775, 97);
		frame.getContentPane().add(scrollPane_type);
		
		
    //输入区域
		typeArea = new JTextArea();
		typeArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrIsPressed = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER && ctrIsPressed) {
					try {
						send();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrIsPressed = false;
				}
			}
		});
		typeArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		typeArea.setName("");
		typeArea.setEnabled(false);
		typeArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_type.setViewportView(typeArea);
		
		JScrollPane scrollPane_list = new JScrollPane();
		scrollPane_list.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_list.setBounds(39, 14, 107, 460);
		frame.getContentPane().add(scrollPane_list);
		
		
    //用户列表
		GV.list = new JList();
		GV.list.setEnabled(false);
		GV.list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				cl_panel_text.show(panel_text,GV.list.getSelectedIndex()+"");
			}
		});
		scrollPane_list.setViewportView(GV.list);
		GV.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GV.list.setModel(GV.defaultListModel);
		GV.defaultListModel.add(0,"GROUP");
		GV.defaultListModel.add(1,"wzl");
		GV.list.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		
    //发送消息的按钮
		sendButton = new JButton("\u53D1\u9001(ctrl+enter)");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					send();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		sendButton.setToolTipText("send message");
		sendButton.setBounds(795, 478, 140, 27);
		frame.getContentPane().add(sendButton);
		
		
	//输入用户名的区域
		userNameArea = new JTextArea();
		userNameArea.setText("\u8BF7\u8F93\u5165\u8D26\u53F7\u3001\u5BC6\u7801");
		//自动提示用户输入网名
		userNameArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(userNameArea.getText().equals("请输入账号、密码"))
				userNameArea.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(userNameArea.getText().equals(""))
					userNameArea.setText("请输入账号、密码");
			}
		});
		userNameArea.setFont(new Font("Monospaced", Font.ITALIC, 16));
		userNameArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		userNameArea.setBounds(14, 480, 184, 24);
		frame.getContentPane().add(userNameArea);
		
		
    //登陆用户名的按钮
		loginButton = new JButton("\u767B\u9646");
		//登陆表面效果的具体实现
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(!loginButtonIsPressed && !userNameArea.getText().equals("请输入账号、密码")) {
					userNameArea.setForeground(Color.BLACK);
					userNameArea.setEnabled(false);
					passwordField.setEnabled(false);
					GV.list.setEnabled(true);
					typeArea.setEnabled(true);
					try {
						new ClientSnd(userNameArea.getText(),passwordField.getText()).logIn();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					loginButton.setText("下线");
					loginButtonIsPressed = true;
					//name.setBackground(new Color(220,220,220));
				}else {
					userNameArea.setForeground(Color.BLACK);
					GV.list.setEnabled(false);
					typeArea.setEnabled(false);
					userNameArea.setEnabled(true);
					passwordField.setText("");
					passwordField.setEnabled(true);
					loginButton.setText("登陆");
					//name.setBackground(new Color(220,220,220));
					loginButtonIsPressed = false;
				}
				
			}
		});
		loginButton.setToolTipText("logIn");
		loginButton.setBounds(324, 478, 113, 27);
		frame.getContentPane().add(loginButton);
		
		
		
	//显示界面滚动条
		scrollPane_text = new JScrollPane();
		scrollPane_text.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_text.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_text.setBounds(160, 14, 775, 343);
		frame.getContentPane().add(scrollPane_text);
		
		
	//布局初始化
		cl_panel_text = new CardLayout();
		
		panel_text = new JPanel();
		scrollPane_text.setViewportView(panel_text);
		panel_text.setLayout(cl_panel_text);
		for (int i = 0; i < 20; i++) {
			GV.textArea[i] = new JTextArea();
			GV.textArea[i].setEditable(false);
			panel_text.add(GV.textArea[i], ""+i);
		}
		
	//注册按钮

		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClientSnd b = new ClientSnd(userNameArea.getText(),passwordField.getText());
					b.register();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		button.setBounds(441, 478, 63, 27);
		frame.getContentPane().add(button);
	
	//密码输入框
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(new Color(0, 0, 0)));
		passwordField.setBounds(199, 480, 101, 24);
		frame.getContentPane().add(passwordField);
	//在线离线状态表
		JList list_1 = new JList();
		list_1.setEnabled(false);
		list_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		list_1.setBounds(18, 14, 21, 458);
		frame.getContentPane().add(list_1);
		
		
		

		
		

	}
}
