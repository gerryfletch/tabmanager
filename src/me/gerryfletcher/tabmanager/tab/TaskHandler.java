package me.gerryfletcher.tabmanager.tab;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import me.gerryfletcher.tabmanager.util.JsonUtils;

public class TaskHandler {
	
	/**
	 * tabActionMap stores each action with a null value, and blocks the thread till the
	 * action is completed.
	 */
	private Map<String, CompletableFuture<Void>> tabActionMap = new ConcurrentHashMap<>();
	
	/**
	 * New Tab Map to handle WebSocket:newTab response objects
	 */
	private Map<String, CompletableFuture<Tab>> futureNewTabs = new ConcurrentHashMap<>();
	
	void waitUntilReceived(String requestId) {		
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
	
	void actionReceived(Object msg) {
		String requestId = JsonUtils.getJSON(msg, "requestId");
		if(requestId != null){
			tabActionMap.get(requestId).complete(null);
		}
	}
	
}
