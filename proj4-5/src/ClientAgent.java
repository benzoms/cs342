import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JCheckBox;

import Game.GamePanel;


public class ClientAgent {

	//LOTS TO BE DONE
	private String _userName;
	private String _buddy;
	private Socket _soc;
	private DataInputStream _inStream;
	private DataOutputStream _outStream;
	private Thread _daemon;
	private int _serverPort;
	private String _serverHostname;
	private ChatPanel _chatPanel;
	private GamePanel _gamePanel;

	public ClientAgent(InetAddress addr, int port, ChatPanel chatPanel, GamePanel gamePanel) throws IOException{
		//init the connection to the panels
		this._chatPanel = chatPanel;
		this._gamePanel = gamePanel;
		
		//TODO
		_userName = "SET ME UP WHEN I CONENCT TO SERVER";
		
		//init the connection
		_soc = new Socket(this._serverHostname, this._serverPort);
		_inStream = new DataInputStream(_soc.getInputStream());
		_outStream = new DataOutputStream(_soc.getOutputStream());
		_outStream.writeUTF(_userName);
		_daemon = new AcceptClient();
		_daemon.start();
	}

	private class AcceptClient extends Thread{
		public void run() {
			while (true) {
				try {
					String[] msgArray = _inStream.readUTF().split("\\|\\|");
					if (msgArray.length > 1) {
						if (msgArray[0].equals("$YOUR_NAME$")) {
							//_chatPanel.setStuff();
	/*
							_userName = msgArray[1];
							_chatZoo.setTitle("CHATROOM ZOO - " + _userName);

							_chatZoo.validate();
							_chatZoo.repaint();
	*/
						} else if (msgArray[0].equals("$DATA$")) {
							//_chatDisplay.append("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$ERROR$")) {
							//_chatDisplay.append("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$USERS$")) {
							// Relay update list of online users to everyone
							_outStream.writeUTF(formatMsgToSrv("$UPDATE$",
									msgArray[1].trim()));
						} else if (msgArray[0].equals("$UPDATE$")) {
							// update list of online users

							//_userPanel.removeAll();
							//_userCheckboxes.clear();

							// adds new check box for every user
							String[] userArray = msgArray[1].trim().split("\\~");
							for (String user : userArray) {
	/*
								JCheckBox newCheckBox = new JCheckBox(user);
								newCheckBox.setSelected(isBuddy(user));
								if (user.equals(_userName))
									newCheckBox.setEnabled(false);
								newCheckBox.addActionListener(this);

								_userPanel.add(newCheckBox);
								_userCheckboxes.add(newCheckBox);
	*/						}
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}	
		
	}

	/**
	 * Build a tokenize string that the server knows how to parse
	 * 
	 * @param msg
	 *            Text from input box
	 * @return Tokenize message to server
	 */
	private String formatMsgToSrv(String action, String msg) {
		if (action.equals("$UPDATE$"))
			return String.format("%s %s %s %s", action, _buddy, action, msg);
		else
			return String.format("%s %s %s %s", _userName, _buddy, action, msg);
	}

	/*
	 * TODO 
	 */
	private String formatMsgToSrv(String action) {
		return formatMsgToSrv(action, "");
	}
	
	/**
	 * send a message to the server
	 * @param message, the message to be send
	 */
	void sendMessage(String message) {
		//XXX
		System.out.println(message);
		try {
			_outStream.writeUTF(formatMsgToSrv("$DATA$", message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO
	 */
	public boolean isBuddy(String user) {
		return _buddy.equals("$PUBLIC$") || _buddy.indexOf(user) != -1;
	}
}
