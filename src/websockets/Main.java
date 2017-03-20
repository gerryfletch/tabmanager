package websockets;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object
		
		Tab facebook = chrome.newTab("http://facebook.com");
		chrome.sleep(3000);
		//chrome.reload(facebook);
		facebook.reload();
		
	}
	
}
