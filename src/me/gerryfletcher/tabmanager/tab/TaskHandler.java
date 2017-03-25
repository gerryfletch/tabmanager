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
	private Map<String, CompletableFuture<Void>> taskMap = new ConcurrentHashMap<>();
	
	/**
	 * New Tab Map to handle WebSocket:newTab response objects
	 */
	private Map<String, CompletableFuture<Tab>> tabMap = new ConcurrentHashMap<>();
	
	/**
	 * Blocks the thread until a task is received and processed.
	 * @param requestId	the UUID of the task
	 */
	void waitUntilReceived(String requestId) {	
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		taskMap.put(requestId, future);
		
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		taskMap.remove(requestId);
		
	}
	
	/**
	 * Blocks the thread until a Tab returned task is received and processed.
	 * @param requestId
	 * @param futureTab
	 * @return
	 */
	Tab waitUntilReceived(String requestId, CompletableFuture<Tab> futureTab){
		
		tabMap.put(requestId,  futureTab);
		
		try {
			return futureTab.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		tabMap.remove(requestId);
		
		return null;
		
	}
	
	/**
	 * This method is called to complete tasks that require a UUID and nothing else.
	 * @param msg
	 */
	void taskReceived(Object msg) {
		String requestId = JsonUtils.getJSON(msg, "requestId");
		if(requestId != null){
			taskMap.get(requestId).complete(null);
		}
	}
	
	/**
	 * This method is called to complete tasks that require a UUID and a tab.
	 * @param requestId
	 * @param tab
	 */
	void tabReceived(String requestId, Tab tab){
		tabMap.get(requestId).complete(tab);
		tabMap.remove(requestId);
	}
	
}
