package Agents;
import java.io.*;
import java.net.*;
import java.util.*;

import NameBuilder.NameBuilder;

public class ServerAgent {
	static ArrayList<Socket> ClientSockets;
	static ArrayList<String> ConnectedUsers;
	ServerSocket _socket;
	

	/**
	 * construct an agent to host the server and mediate the game and connections
	 */
	public ServerAgent(InetAddress address, int userPort){
		ClientSockets = new ArrayList<Socket>(); //store client sockets
		ConnectedUsers = new ArrayList<String>(); //store client names
		Thread manager = new Thread( new AcceptManager() );
		try {
			_socket = new ServerSocket(userPort, 1000, address);
			System.out.println("Chat Server started at :" + new Date().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		manager.start();
	}

	/*
	 * 
	 */
	private class AcceptManager extends Thread{
		public void run(){
			while(true){
				Socket clientSocket = null;
				try {
					clientSocket = _socket.accept();
					AcceptClient client = new AcceptClient(clientSocket);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*
	 * This thread is instantiated when a new socket needs to be accepted
	 * 
	 */
	private class AcceptClient extends Thread{
		private Socket _clientSocket;
		private DataInputStream _inStream;
		private DataOutputStream _outStream;

		AcceptClient(Socket clientSocket) throws Exception {
			_clientSocket = clientSocket;

			_inStream = new DataInputStream(_clientSocket.getInputStream());
			_outStream = new DataOutputStream(_clientSocket.getOutputStream());

			String userName = _inStream.readUTF(); //protocol
			if (userName.equals("$REPLACE_TOKEN$")) { //do we have to generate a new client?
				NameBuilder nb = new NameBuilder();
				userName = nb.generateUniqueName();
			}
			for (String user : ConnectedUsers) {
				if (user.toLowerCase().trim()
						.equals(userName.toLowerCase().trim())) { //if we have duplicate name, reject client
					_outStream.writeUTF("$ERROR$||Username " + userName
							+ " is already taken");
					return;
				}
			}

			System.out.println("User Logged In :" + userName);
			ConnectedUsers.add(userName);
			ClientSockets.add(_clientSocket);

			// tell new user who's currently online
			_outStream.writeUTF("$YOUR_NAME$||" + userName);
			_outStream.writeUTF("$USERS$||" + getCommaDelimitedUsers());

			// start listening
			start();
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Thread#run()
		 * 
		 * Parse messages and take actions
		 * 
		 * Message Actions:
		 * * LOGOUT: remove the user from the List of sockets and the List of userNames
		 * * UPDATE: 
		 * 
		 */
		public void run() {
			while (true) {
				try {
					StringBuilder msg;

					//parse message into 3 tokens
					// the user that sent the message
					// the list of names that the message should be sent to
					// the action of the message
					String msgFromClient = new String();
					msgFromClient = _inStream.readUTF();
					StringTokenizer st = new StringTokenizer(msgFromClient);
					String userName = st.nextToken();
					String sendTo = st.nextToken();
					String msgAction = st.nextToken();
					int iCount = 0;

					msg = new StringBuilder();

					if (msgAction.equals("$LOGOUT$")) {
						for (iCount = 0; iCount < ConnectedUsers.size(); iCount++) {
							if (ConnectedUsers.get(iCount).equals(userName)) {
								ConnectedUsers.remove(iCount);
								ClientSockets.remove(iCount);
								System.out.println("User " + userName
										+ " Logged Out ...");
								break;
							}
						}
						notifyUsers("$PUBLIC$", "$UPDATE$" + "||"
								+ getCommaDelimitedUsers());
						break;
					}

					if (msgAction.equals("$UPDATE$"))
						msg.append(msgAction + "||");
					else
						msg.append(msgAction + "||" + userName + ":");
					while (st.hasMoreTokens()) {
						msg.append(" " + st.nextToken().replace("||", "|"));
					}

					//see how many users we can send our message to
					iCount = notifyUsers(sendTo, msg.toString());

					//hopefully we have sent the message to the available users
					//otherwise, we (this) send out and say that we are not online
					if (iCount == ConnectedUsers.size()
							&& !sendTo.equals("$PUBLIC$")) {
						_outStream.writeUTF("I am offline");
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		/*
		 * loop through the users and send them the message
		 * we either send the message to a user if:
		 *  1) the message type is private AND their name is one of the ones given
		 *  2) the message type is not private (public)
		 */
		private int notifyUsers(String sendTo, String msg) throws IOException {

			int iCount = 0;
			boolean isPrivate = !sendTo.equals("$PUBLIC$");

			String[] users = sendTo.split(";");
			for (String user : users) {
				for (iCount = 0; iCount < ConnectedUsers.size(); iCount++) {
					if (((ConnectedUsers.get(iCount).equals(user) && isPrivate) || !isPrivate)) {

						Socket tmpSocket = (Socket) ClientSockets.get(iCount);
						DataOutputStream tmpOutStream = new DataOutputStream(
								tmpSocket.getOutputStream());
						tmpOutStream.writeUTF(msg);

						// if we chose to send a private message, no use in looping more
						if (isPrivate)
							break;

					}
				}
			}

			return iCount;
		}

		/*
		 * generate a string of *connected* user
		 */
		private String getCommaDelimitedUsers() {
			StringBuilder connectedUsers = new StringBuilder();
			String delimiter = "";
			for (Object user : ConnectedUsers) {
				connectedUsers.append(delimiter);
				connectedUsers.append(((String) user).replace("~", "^"));
				delimiter = "~";
			}
			return connectedUsers.toString();
		}
	}
}
