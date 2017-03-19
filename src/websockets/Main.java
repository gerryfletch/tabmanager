package websockets;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object
		
		chrome.getAllInWindow(); //List all the current tabs
		
		Tab exampleTab = chrome.newTab("http://google.co.uk"); //create a new tab
		
		System.out.println(exampleTab.getId()); //print its ID
	}
	
}
