package websockets;

import static spark.Spark.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.plaf.TabbedPaneUI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Tab Manager is to be used in conjunction with Selenium running a Chrome Driver.
 * <p>It is designed to bring more function to the Selenium library, by utilising
 * the Google Chrome Extension API. The Extension runs in the event page, which fires
 * once during initiation and holds a WebSocket connection until it is not necessary.
 * @author Gerry Fletcher @ gerryfletcher.me
 *
 */
public class TabManager implements Observer{

	private boolean serverOnline = false;
	
	private List<Tab> tabList = new ArrayList<>();
	private Tab activeTab = null;
	
	private Gson gson = new Gson();

	/** Constructor sets default port to 100, and calls startSocketServer() */
	TabManager(){ //constructor without options
		port(100); //set port to default: 100
		startSocketServer();
		
		
	}
	
	/** Constructor with <i>Tab Options</i>, like port number or JSON formatting. TODO: this */
//	TabManager(TabOptions options){ //constructor with options
//		startSocketServer();
//	}
	
	/**
	 * Starts the socket server, waits for initialisation, and sets an event observer.
	 * <p>The WebSocket server runs on localhost, and sockets are interacted with through
	 * the SocketHandler class, accessible by the SocketHandler.<i>instance</i>.
	 * <p>After initialisation, the socket handler waits for a connection, which has
	 * a listener here. When a connection is made, the Observer is alerted, and the
	 * program runs.
	 */
	private void startSocketServer() {
		webSocket("/", SocketHandler.class); //create Web Socket object
		init(); //start the socket server
		awaitInitialization(); //Wait till everything is up and running
		
		SocketHandler.instance.addObserver(this); //Set event handler
	}
	
	/**
	 * Sends a message to the WebSocket to return all tab information
	 * <p>Once returned, the event manager will call the function but with the
	 * response included.
	 */
	private void getAllInWindow() {
		JsonObject json = new JsonObject();
		json.addProperty("request", "getAllInWindow");
		String jsonOutput = gson.toJson(json);
		sendMessage(jsonOutput);
	}
	
	/**
	 * Takes the retrieved object, and stores the data.
	 * <p>The response is passed as JSON, so we store each tab as 
	 * a Tab object.
	 * @param msg	JSON formatted data
	 */
	private void getAllInWindow(Object response){
		JsonObject stringResponse = gson.fromJson(response.toString(), JsonObject.class);
		
		JsonArray tabsArray = stringResponse.get("tabData").getAsJsonArray();
		
		for(final JsonElement tabData : tabsArray){
			Tab tab = gson.fromJson(tabData, Tab.class);
			System.out.println(tab.getIndex() + " : " + tab.getUrl());
		}
		
	}
	
	/**
	 * New Tab Map to handle WebSocket:newTab response objects
	 */
	Map<String, CompletableFuture<Tab>> futureNewTabs = new ConcurrentHashMap<>();

	/**
	 * Creates a new tab with a String URL.
	 * <p>This method will create a new tab with optional parameters:
	 * URL
	 * @param url A string URL.
	 * 
	 * TODO: CREATE EMPTY OBJECT
	 * TODO: ASSIGN IDENTIFIER
	 * TODO: PASS IDENTIFIER IN JSON MESSAGE
	 * @return 
	 */
	public Tab newTab(String url) {
		if(serverOnline){
			final String requestId = UUID.randomUUID().toString(); //create a unique ID for the object
			
			JsonObject json = new JsonObject();
			//form JSON
			json.addProperty("request", "newTab");
			json.addProperty("requestId", requestId);
			json.addProperty("url", url);
			
			String jsonOutput = gson.toJson(json);
			sendMessage(jsonOutput);
			
			CompletableFuture<Tab> futureTab = new CompletableFuture<>();
			futureNewTabs.put(requestId,  futureTab);
			try {
				return futureTab.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	/**
	 * TODO: CHECK RESPONSE IDENTIFIER
	 * TODO: ASSIGN TAB DATA TO OBJECT
	 * @param response
	 */
	
	private void newTab(Object response){
		JsonObject stringResponse = gson.fromJson(response.toString(), JsonObject.class);
		String created = stringResponse.get("created").getAsString();
		String requestId = stringResponse.get("requestId").getAsString();
		JsonElement tabData = stringResponse.get("tabData");
		Tab tab = gson.fromJson(tabData, Tab.class);
		futureNewTabs.get(requestId).complete(tab);
	}
	
	
	/**
	 * Takes a String and sends it to the WebSocket.
	 * @param message	a <b>JSON formatted String</b> with instructions for CRX.
	 */
	public void sendMessage(String message){
		try {
			SocketHandler.instance.sendMessage(message);
		} catch (IOException e) {
			System.out.println("Cannot send message");
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when SocketHandler has activity, passing what activity there is.
	 * @param msg	a String or JSON formatted 
	 */
	@Override
	public void update(Observable o, Object msg) {	
		String response = getJSONResponse(msg);
		
		if(response.equals("connected")){
			System.out.println("\nThe CRX has connected.\n");
			this.serverOnline = true;
			getAllInWindow();
		} else if(response.equals("getAllInWindow")){
			getAllInWindow(msg);
		} else if(response.equals("newTab")){
			newTab(msg);
		}
	}

	/**
	 * Takes a JSON message and finds the response value.
	 * <p>This is usually a response from the websocket, but can be used to get the response
	 * from any JSON if it contains that attribute.
	 * @param msg	The <i>JSON formatted message.</i> This is sent as an Obj, because it is via the events.
	 * @return A string with the response value.
	 */
	private String getJSONResponse(Object msg) {
		Gson gson = new Gson();
		JsonObject response = gson.fromJson(msg.toString(), JsonObject.class);
		String responseString = response.get("response").getAsString();
		return responseString;
	}
}
