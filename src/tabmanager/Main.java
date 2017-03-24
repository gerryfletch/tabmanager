package tabmanager;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object
		Tab google = chrome.newTab("http://google.com");
		Tab github= chrome.newTab("http://github.com");
		Tab reddit = chrome.newTab("http://reddit.com");
		System.out.println(google.isActive());
		System.out.println(github.isActive());
		System.out.println(reddit.isActive());
		System.out.println("\n");
		
		chrome.switchTo(github);
		
		chrome.sleep(2000);
		
		System.out.println(google.isActive());
		System.out.println(github.isActive());
		System.out.println(reddit.isActive());
		/**
		 * TODO: store a map with Tab + UUID
		 * TODO: Update UUID variables when needed.
		 * TODO: return a list with getAllInWindow()
		 * TODO: create Update() method
		 */
	}
	
}
