package tabmanager;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object
		
		Tab google = chrome.newTab("http://google.com");
		Tab github = chrome.newTab("http://github.com");
		
		System.out.println("Google: " + google.isActive());
		System.out.println("GitHub: " + github.isActive());
		
		chrome.switchTo(google);
		
		System.out.println("Google: " + google.isActive());
		System.out.println("GitHub: " + github.isActive());
		
		chrome.switchTo(github);
		
		System.out.println("Google: " + google.isActive());
		System.out.println("GitHub: " + github.isActive());
		
		/**
		 * TODO: store a map with Tab + UUID
		 * TODO: Update UUID variables when needed.
		 * TODO: return a list with getAllInWindow()
		 * TODO: create Update() method
		 */
	}
	
}
