package websockets;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class ExtWebSocket {
	
	private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
	
	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception {
		System.out.println("an extension has connected.");
		Scanner in = new Scanner(System.in);
		while(true){
			String command = in.nextLine();
			System.out.println("Sending: " + command);
			session.getRemote().sendString(command);
		}
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason){
		sessions.remove(session);
	}
	
	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("MESSAGE: " + message); //print message
		session.getRemote().sendString(message); //send it back
	}
}
