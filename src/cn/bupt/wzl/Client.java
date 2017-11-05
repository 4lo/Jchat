package cn.bupt.wzl;

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
	private JTextArea textArea[]= new JTextArea[20];
	public static JList list;
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


	public Client() {
		initialize();
	}

//������Ϣ
	private void send() throws IOException {
		
		String msg = new String(typeArea.getText());
		new ClientSnd(userNameArea.getText(),list.getSelectedIndex(),msg).send();
		typeArea.setText("");
		System.out.println(msg);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 954, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	//�ָ���
		separator = new JSeparator();
		separator.setBounds(156, 366, 793, 9);
		frame.getContentPane().add(separator);
		
		
    //��������Ĺ�����
		scrollPane_type = new JScrollPane();
		scrollPane_type.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_type.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_type.setBounds(160, 376, 775, 97);
		frame.getContentPane().add(scrollPane_type);
		
		
    //��������
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
						// TODO �Զ����ɵ� catch ��
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
		scrollPane_list.setBounds(14, 14, 140, 459);
		frame.getContentPane().add(scrollPane_list);
		
		
    //�û��б�
		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				System.out.println(list.getSelectedIndex()+"");
				cl_panel_text.show(panel_text,list.getSelectedIndex()+"");
			}
		});
		scrollPane_list.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"1", "2", "3", "4", "5", "6"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
    //������Ϣ�İ�ť
		sendButton = new JButton("\u53D1\u9001(ctrl+enter)");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					send();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		});
		sendButton.setToolTipText("send message");
		sendButton.setBounds(795, 478, 140, 27);
		frame.getContentPane().add(sendButton);
		
		
	//�����û���������
		userNameArea = new JTextArea();
		userNameArea.setText("\u8BF7\u8F93\u5165\u8D26\u53F7\u3001\u5BC6\u7801");
		//�Զ���ʾ�û���������
		userNameArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(userNameArea.getText().equals("�������˺š�����"))
				userNameArea.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(userNameArea.getText().equals(""))
					userNameArea.setText("�������˺š�����");
			}
		});
		userNameArea.setFont(new Font("Monospaced", Font.ITALIC, 16));
		userNameArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		userNameArea.setBounds(14, 480, 184, 24);
		frame.getContentPane().add(userNameArea);
		
		
    //��½�û����İ�ť
		loginButton = new JButton("\u767B\u9646");
		//��½����Ч���ľ���ʵ��
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!loginButtonIsPressed && !userNameArea.getText().equals("�������˺š�����")) {
					userNameArea.setForeground(Color.BLACK);
					userNameArea.setEnabled(false);
					passwordField.setEnabled(false);
					list.setEnabled(true);
					typeArea.setEnabled(true);

					loginButton.setText("����");
					loginButtonIsPressed = true;
					//name.setBackground(new Color(220,220,220));
				}else {
					userNameArea.setForeground(Color.BLACK);
					list.setEnabled(false);
					typeArea.setEnabled(false);
					userNameArea.setEnabled(true);
					passwordField.setText("");
					passwordField.setEnabled(true);
					loginButton.setText("��½");
					//name.setBackground(new Color(220,220,220));
					loginButtonIsPressed = false;
				}
				
			}
		});
		
	//��½��ť
		loginButton.setToolTipText("logIn");
		loginButton.setBounds(324, 478, 113, 27);
		frame.getContentPane().add(loginButton);
		
		
		
	//��ʾ���������
		scrollPane_text = new JScrollPane();
		scrollPane_text.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_text.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_text.setBounds(160, 14, 775, 343);
		frame.getContentPane().add(scrollPane_text);
		
		
	//���ֳ�ʼ��
		cl_panel_text = new CardLayout();
		
		panel_text = new JPanel();
		scrollPane_text.setViewportView(panel_text);
		panel_text.setLayout(cl_panel_text);
		for (int i = 0; i < 20; i++) {
			textArea[i] = new JTextArea();
			textArea[i].setEditable(false);
			panel_text.add(textArea[i], ""+i);
		}
		
	//ע�ᰴť
		JButton button = new JButton("\u6CE8\u518C");
		button.setBounds(441, 478, 63, 27);
		frame.getContentPane().add(button);
	
	//���������
		passwordField = new JPasswordField();
		passwordField.setBorder(new LineBorder(new Color(171, 173, 179)));
		passwordField.setBounds(199, 480, 101, 24);
		frame.getContentPane().add(passwordField);
		
		
		

		
		

	}
}
