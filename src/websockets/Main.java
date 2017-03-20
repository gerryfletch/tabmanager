package websockets;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object
		
		Tab exampleTab = chrome.newTab("http://gerryfletcher.me"); //create a new tab
		exampleTab.switchTo();
		System.out.println(exampleTab.getId());
		try{
			Thread.sleep(1000);
		} catch (Exception e){
			
		}
		
		Tab github = chrome.newTab("http://github.com");
		chrome.newTab("http://facebook.com");
		
		exampleTab.close();
	}
	
}
