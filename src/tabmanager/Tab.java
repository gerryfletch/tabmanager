package tabmanager;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Tab {
	
	//private final Gson gson = new Gson();
	
	private boolean active;
	private boolean audible;
	
	private boolean autoDiscardable;
	private boolean discarded;
	
	private boolean highlighted;
	private boolean pinned;
	private boolean selected;
	private boolean incognito;
	
	private String faviconUrl;
	private String status;
	private String title;
	private String url;
	
	private int width;
	private int height;
	
	private int id;
	private int index;
	private int windowId;
	
	//BOOLEAN VALUES
	
	public Tab(JsonElement tab) {
		// TODO Auto-generated constructor stub
	}
	//active
	/**
	 * Whether the tab is active in its window. (Does not necessarily mean the window is focused.)
	 * @return active value true/false
	 */
	public boolean isActive() {
		return active;
	}
	
	//audible
	/**
	 * Whether the tab has produced sound over the past couple of seconds (but it might not be heard if also muted).
	 * <p>Equivalent to whether the speaker audio indicator is showing.
	 * @return audible value true/false
	 */
	public boolean isAudible() {
		return audible;
	}
	
	//discardable
	/**
	 * Whether the tab can be discarded automatically by the browser when resources are low.
	 * @return autoDiscardable value true/false
	 */
	public boolean isAutoDiscardable() {
		return autoDiscardable;
	}
	
	//discarded
	/**
	 * Whether the tab is discarded.
	 * <p>A discarded tab is one whose content has been unloaded from memory, but is still visible in the tab strip.
	 * Its content gets reloaded the next time it's activated.
	 * @return discarded value true/false
	 */
	public boolean isDiscarded() {
		return discarded;
	}
	
	//highlighted
	/**
	 * Whether the tab is highlighted.
	 * @return highlighted value true/false
	 */
	public boolean isHighlighted() {
		return highlighted;
	}
	
	//pinned
	/**
	 * Whether the tab is pinned.
	 * @return pinned value true/false
	 */
	public boolean isPinned() {
		return pinned;
	}
	
	//selected BE CAREFUL WITH THIS
	/**
	 * DEPRACATED. IF YOU SEE THIS IN PRODUCTION, IT IS BAD!
	 * TODO: Get rid of any Selected methods. Use Highlighted instead.
	 * @return selected value true/false
	 */
	public boolean isSelected() {
		return selected;
	}
	
	//incognito
	/**
	 * Whether the tab is in an incognito window.
	 * @return incognito value true/false
	 */
	public boolean isIncognito() {
		return incognito;
	}
	
	
	//STRINGS
	
	//favicon URL
	/**
	 * @return the favIconUrl
	 */
	public String getFavIconUrl() {
		return faviconUrl;
	}
	
	//status
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	//title
	/**
	 * The title of the tab.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the url
	 */
	//url
	public String getUrl() {
		return url;
	}
	
	
	//INTEGERS
	
	//width
	/**
	 * The width of the tab in pixels.
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	//height
	/**
	 * The height of the tab in pixels
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	//id
	/**
	 * The ID of the tab. Tab IDs are unique within a browser session. Under some circumstances a Tab may not be assigned an ID.
	 * <p>For example when querying foreign tabs using the sessions API, in which case a session ID may be present.
	 * Tab ID can also be set to chrome.tabs.TAB_ID_NONE for apps and devtools windows.
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	//index
	/**
	 * The zero-based index of the tab within its window.
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	
	//window id
	/**
	 * The ID of the window the tab is contained within.
	 * @return the windowId
	 */
	public int getWindowId() {
		return windowId;
	}
	
	/**
	 * Changes the browser focus to this tab.
	 */
	public void switchTo() {
		Gson gson = new Gson();
		
		int tabId = getId();
		JsonObject json = new JsonObject();
		json.addProperty("request", "switchTo");
		json.addProperty("tabId", tabId);
		String jsonOutput = gson.toJson(json);
		sendMessage(jsonOutput);
		
		
	}
	
	/**
	 * Closes this tab on the browser.
	 */
	public void close() {
		Gson gson = new Gson();
		
		int tabId = getId();
		JsonObject json = new JsonObject();
		json.addProperty("request", "closeTab");
		json.addProperty("tabId", tabId);
		String jsonOutput = gson.toJson(json);
		sendMessage(jsonOutput);
	}
	
	/**
	 * Refreshes this tab on the browser.
	 */
	public void reload() {
		Gson gson = new Gson();
		
		int tabId = getId();
		JsonObject json = new JsonObject();
		json.addProperty("request", "reloadTab");
		json.addProperty("tabId", tabId);
		String jsonOutput = gson.toJson(json);
		sendMessage(jsonOutput);
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
	
}
