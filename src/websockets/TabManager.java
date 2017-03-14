package websockets;

import static spark.Spark.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Tab Manager is to be used in conjunction with Selenium running a Chrome Driver.
 * <p>It is designed to bring more function to the Selenium library, by utilising
 * the Google Chrome Extension API. The Extension runs in the event page, which fires
 * once during initiation and holds a WebSocket connection until it is not necessary.
 * @author Gerry Fletcher @ gerryfletcher.me
 *
 */
public class TabManager {
	
	private Map<Tab, Integer> tabMap = new HashMap<>();
	private Tab activeTab = null;
	
	/**
	 * Constructs the TabManager object
	 * <p>Creates the WebSocket on Port 100 (or a given port)
	 */
	TabManager(){ //constructor without options
		port(100); //set port to default: 100
		startSocketServer();
	}
	
	TabManager(TabOptions options){ //constructor with options
		startSocketServer();
	}
	
	private void startSocketServer() {
		webSocket("/", SocketHandler.class); //create Web Socket object
		init(); //start the Tab Manager
		awaitInitialization();
		
		//waits for the extension to connect
		while(SocketHandler.session == null && !Thread.currentThread().isInterrupted()){
			System.out.println("TabManager Chrome Extension not connected.");
			try {
			    Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
		}
		
		//once connected, get current open tabs and store them in the map
		currentTabMap();
	}
	
	private void currentTabMap() {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("type", "command");
		json.addProperty("command", "getAllInWindow");
		String jsonOutput = gson.toJson(json);

		sendMessage(jsonOutput);
		
		//TODO: send JSON to client, WAIT for response
		//TODO: get tab data, store it in Map
		//TODO: get current tab, store it in currentTab
		
	}

	public void sendMessage(String message){
		try {
			SocketHandler.instance.sendMessage(message);
		} catch (IOException e) {
			System.out.println("Cannot send message");
			e.printStackTrace();
		}
	}

	public void newTab() {
		
	}
}
