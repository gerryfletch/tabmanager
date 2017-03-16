package websockets;

public class Main {
	
	public static void main(String[] args){
		TabManager chrome = new TabManager();
		
		try{
			Thread.sleep(10000);
		} catch (Exception e){
			//lol
		}
		Tab main = chrome.newTab("http://gerryfletcher.me");
		System.out.println("index: " + main.getIndex());
	}
	
}
