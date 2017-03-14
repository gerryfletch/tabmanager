package websockets;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class SocketHandler {

	static SocketHandler instance;

	static Session session;
	
	public SocketHandler(){
		instance = this;
	}
	
	//On the first connection, this runs. Sets the session so that I can access the web socket
	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception {
		System.out.println("Client connected.\n");
		this.session = session;
	}

	//when it closes
	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason){
		this.session = null;
	}
	
	//When we receive a message client --> server
	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("Client --> Server: " + message); //print message
		session.getRemote().sendString(message); //send it back
	}
	
	//Sending a message Server --> Client
	public void sendMessage(String message) throws IOException {
		if(session != null){
			this.session.getRemote().sendString(message);
			System.out.println("Server --> Client: " + message);
		} else {
			System.out.println("No client connected.");
		}
	}
}
