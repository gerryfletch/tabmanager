package me.gerryfletcher.tabmanager.connect;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;


@WebSocket
public class SocketHandler extends Observable{

	public static SocketHandler instance;
	static Session session;
	
	private Gson gson = new Gson();
	
	public SocketHandler(){
		instance = this;		
	}

	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception {
		this.session = session;
		JsonObject json = new JsonObject();
		json.addProperty("response", "connected");
		String jsonOutput = gson.toJson(json);
		this.setChanged();
		this.notifyObservers(jsonOutput);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason){
		this.session = null;
		System.out.println("The WebSocket server has closed. This can cause "
				+ "unpredicted problems if it was unintentional.");
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("Client --> Server: " + message);
		this.setChanged();
		this.notifyObservers(message);
	}

	public void sendMessage(String message) throws IOException {
		if(session != null){
			this.session.getRemote().sendString(message);
			System.out.println("Server --> Client: " + message);
		} else {
			System.out.println("No client connected.");
		}
	}
}
