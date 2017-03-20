package websockets;

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
	
	private String favIconUrl;
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
	/**
	 * @param active true/false
	 */
	public void setActive(boolean active) {
		this.active = active;
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
	/**
	 * @param audible  true/false
	 */
	public void setAudible(boolean audible) {
		this.audible = audible;
	}
	
	//discardable
	/**
	 * Whether the tab can be discarded automatically by the browser when resources are low.
	 * @return autoDiscardable value true/false
	 */
	public boolean isAutoDiscardable() {
		return autoDiscardable;
	}
	/**
	 * @param autoDiscardable  true/false
	 */
	public void setAutoDiscardable(boolean autoDiscardable) {
		this.autoDiscardable = autoDiscardable;
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
	/**
	 * @param discarded  true/false
	 */
	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}
	
	//highlighted
	/**
	 * Whether the tab is highlighted.
	 * @return highlighted value true/false
	 */
	public boolean isHighlighted() {
		return highlighted;
	}
	/**
	 * @param highlighted  true/false
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	//pinned
	/**
	 * Whether the tab is pinned.
	 * @return pinned value true/false
	 */
	public boolean isPinned() {
		return pinned;
	}
	/**
	 * @param pinned  true/false
	 */
	public void setPinned(boolean pinned) {
		this.pinned = pinned;
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
	/**
	 * @param selected  true/false
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	//incognito
	/**
	 * Whether the tab is in an incognito window.
	 * @return incognito value true/false
	 */
	public boolean isIncognito() {
		return incognito;
	}
	/**
	 * @param incognito  true/false
	 */
	public void setIncognito(boolean incognito) {
		this.incognito = incognito;
	}
	
	
	//STRINGS
	
	//favicon URL
	/**
	 * @return the favIconUrl
	 */
	public String getFavIconUrl() {
		return favIconUrl;
	}
	/**
	 * @param favIconUrl the favIconUrl to set
	 */
	public void setFavIconUrl(String favIconUrl) {
		this.favIconUrl = favIconUrl;
	}
	
	//status
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set. <i>loading</i> or <i>complete</i>
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the url
	 */
	
	//url
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	/**
	 * @param width the width to set in pixels
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	//height
	/**
	 * The height of the tab in pixels
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set in pixels
	 */
	public void setHeight(int height) {
		this.height = height;
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
	/**
	 * @param id the id to set
	 */
//	public void setId(int id) {
//		this.id = id;
//	}
	
	//index
	/**
	 * The zero-based index of the tab within its window.
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
	 * @param windowId the windowId to set
	 */
	public void setWindowId(int windowId) {
		this.windowId = windowId;
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
