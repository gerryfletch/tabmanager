package websockets;

import java.util.Observable;
import java.util.Observer;

import static spark.Spark.*;

/**
 * Connect is the initiating class for the Tab Manager library.
 * <p>This class turns on the WebSocket server, applies configurations
 * and waits for the extension to connect. The Observer class is used
 * to maintain an event driven environemnt. Commands are sent in
 * JSON. Connect only handles the connection response, other commands
 * are handled in TabManager.
 * @author Gerry Fletcher @ gerryfletcher.me
 *
 */
public class ChromeConnect implements Observer {
	
	private boolean serverOnline = false;
	private TabManager chromeManager;

	/** Starts the WebSocket server with Jettys default port **/
	ChromeConnect(){
		setTabManager();
		startSocketServer();
	}

	/** Starts the WebSocket server on a set port **/
	ChromeConnect(int port){
		setTabManager();
		port(port);
		startSocketServer();
	}

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
		awaitInitialization();
		SocketHandler.instance.addObserver(this); //Set event handler
		SocketHandler.instance.addObserver(chromeManager); //Set tabManager handler
		
		awaitChromeInitialization(); //wait for extension to connect
	}
	
	/**
	 * Sleeps the thread until an extension has connected.
	 */
	public void awaitChromeInitialization() {
		while(true){
			if(this.serverOnline){
				break;
			}
			try{
				Thread.sleep(1000);
			} catch (Exception e){
				System.out.println("Error sleeping thread.");
			}
		}
	}
	
	private void setTabManager() {
		this.chromeManager = new TabManager();
	}
	
	public TabManager getTabManager() {
		return this.chromeManager;
	}

	@Override
	public void update(Observable o, Object msg) {
		String response = JsonUtils.getJSONResponse(msg);
		if(response.equals("connected")){
			System.out.println("\nThe CRX has connected.\n");
			this.serverOnline = true;
		}
	}
}
