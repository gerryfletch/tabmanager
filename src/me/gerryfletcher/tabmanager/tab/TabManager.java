package me.gerryfletcher.tabmanager.tab;

import static spark.Spark.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import me.gerryfletcher.tabmanager.connect.SocketHandler;
import me.gerryfletcher.tabmanager.util.JsonUtils;
import me.gerryfletcher.tabmanager.util.UrlUtils;

/**
 * Tab Manager is to be used in conjunction with Selenium running a Chrome Driver.
 * <p>It is designed to bring more function to the Selenium library, by utilising
 * the Google Chrome Extension API. The Extension runs in the event page, which fires
 * once during initiation and holds a WebSocket connection until it is not necessary.
 * @author Gerry Fletcher @ gerryfletcher.me
 *
 */
public class TabManager implements Observer{
	
	private Gson gson = new Gson();
	
	/**
	 * The currently active tab
	 */
	private Tab activeTab = null;
	
	private Map<Integer, Tab> tabMap = new HashMap<>(); //tab id : tab
	
	/**
	 * tabActionMap stores each action with a null value, and blocks the thread till the
	 * action is completed.
	 */
	private Map<String, CompletableFuture<Void>> tabActionMap = new ConcurrentHashMap<>();
	
	/**
	 * New Tab Map to handle WebSocket:newTab response objects
	 */
	private Map<String, CompletableFuture<Tab>> futureNewTabs = new ConcurrentHashMap<>();

	/**
	 * Creates a new tab with a String URL.
	 * <p>This method will create a new tab with optional parameters:
	 * URL
	 * @param url A string URL.
	 * @return The Tab object containing all info
	 */
	public Tab newTab(String url) {
		
		url = UrlUtils.formatUrl(url);
		
		final String requestId = UUID.randomUUID().toString(); //create a unique ID for the object
		
		JsonObject json = new JsonObject();

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
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Takes the WebSocket newTab response, and stores the data in a tab object.
	 * @param response	JSON sent from the WebSocket.
	 */
	private void newTab(Object response){
		JsonObject stringResponse = gson.fromJson(response.toString(), JsonObject.class);
		
		String created = stringResponse.get("created").getAsString();
		String requestId = stringResponse.get("requestId").getAsString();
		JsonElement tabData = stringResponse.get("tabData");
		
		Tab tab = gson.fromJson(tabData, Tab.class);
		futureNewTabs.get(requestId).complete(tab);
		
		futureNewTabs.remove(requestId);
		
		tabMap.put(tab.getId(), tab);
	}
	
	/**
	 * Switches to a Tab using the TabID.
	 * @param tab
	 */
	public void switchTo(Tab tab){
		final String requestId = UUID.randomUUID().toString(); //create a unique ID for the object
		int tabId = tab.getId();
		
		JsonObject json = new JsonObject();
		
		json.addProperty("request", "switchTo");
		json.addProperty("requestId", requestId); //UUID
		json.addProperty("tabId", tabId); //actual tab ID
		
		String jsonOutput = gson.toJson(json);
		sendMessage(jsonOutput);
		
		waitUntilReceived(requestId);
		
	}

	/**
	 * After switching, we update the object with the response.
	 * @param response
	 */
	private void switchTo(Object response) {
		
		JsonObject stringResponse = gson.fromJson(response.toString(), JsonObject.class);
		JsonElement tabData = stringResponse.get("tabData");
		
		Tab tempTab = gson.fromJson(tabData, Tab.class);
		
		int id = tempTab.getId();
		
		Tab original = tabMap.get(id); //get the tab from the map
		
		original.copyValues(tempTab); //set the new values of the tab
		
		swapActiveTab(original);

	}

	private void swapActiveTab(Tab original) {
		if(this.activeTab == null){
			this.activeTab = original;
		} else {
			this.activeTab.active = false;
			this.activeTab = original;
		}
	}

	/**
	 * Takes the TabData object from a JSON response, looks for the ID
	 * and populates that Tab Object. 
	 * @param response
	 */
	public void updateTab(Object response) {
		JsonObject stringResponse = gson.fromJson(response.toString(), JsonObject.class);
		JsonElement tabData = stringResponse.get("tabData");
		
		Tab tempTab = gson.fromJson(tabData, Tab.class);
		if(tempTab != null){
			int tabId = tempTab.getId();
			//get actual tab from map
			
		}
	}

	/**
	 * Closes a Tab using the TabID.
	 * @param exampleTab
	 */
	public void close(Tab tab) {		
		tab.close(); //calls method from Tab instead
	}
	
	/**
	 * Refreshes the tab using the TabID.
	 */
	public void reload(Tab tab) {
		tab.reload(); //calls method from Tab instead
	}
	
	/**
	 * Sends a message to the WebSocket to return all tab information
	 * <p>Once returned, the event manager will call the function but with the
	 * response included.
	 */
	public void getAllInWindow() {
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
		
		List<Tab> tabList = null;
		
		for(final JsonElement tabData : tabsArray){
			Tab tab = gson.fromJson(tabData, Tab.class);
			tabList.add(tab);
			//TODO: Return Tab Objects as a List.
		}
		
	}
	

	private void waitUntilReceived(String requestId) {		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		tabActionMap.put(requestId, future);
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		tabActionMap.remove(requestId);
		
	}
	
	private void actionReceived(Object msg) {
		String requestId = JsonUtils.getJSON(msg, "requestId");
		if(requestId != null){
			tabActionMap.get(requestId).complete(null);
		}
	}
	
	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes a String and sends it to the WebSocket.
	 * @param message	a <b>JSON formatted String</b> with instructions for CRX.
	 */
	private void sendMessage(String message){
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
		
		String response = JsonUtils.getJSON(msg, "response");

		if(response.equals("newTab")){
			newTab(msg);
		} else {
			
			if(response.equals("getAllInWindow")){
				getAllInWindow(msg);
			} else if (response.equals("switchTo")){
				switchTo(msg);
			}
			
			actionReceived(msg);
			
		}
	}

	public void query() {
		System.out.println("query: ");
		for(Object o : tabMap.entrySet()){
			System.out.println("\n" + o.toString());
		}
	}
}
