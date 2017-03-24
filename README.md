# Chrome TabManager
TabManager is a Java Library that uses WebSockets to manage a Chrome window.<br>

**IMPORTANT**: I am having some issues with Jetty's web socket queue, so the thread is bugging up if too many requests are made at once. This is a priority issue I am attempting to fix. A temporary fix is to sleep between requests, results may vary.

TabManager is a fully event-driven platform, asynchronously sending and receiving JSON formatted commands and responses to the TabManager Chrome Extension. Features currently include tab object storage and some basic methods like create, close, reload and switchTo. All of the tab attributes are stored, such as:
 - url
 - id
 - active
 - status
 
 ## Motivation
 I initially created TabManager to run along side Selenium, as there is no default tab management. But, over time it has become an extensible tab management suite utilising the Chrome Extension API.
 
 ## Initialising the Tab Manager
 TabManager was originally created to run alongside Selenium, which runs a version of Chrome at runtime. However, if you're controlling a window that is already open, you may need to reload the extension as Google's background page times out if it is not being used.
 
 ```Java
 /**
  * Creates the Connect object on port 100.
  * If you wish to change the port, you will need to change it in the Extension 'background.js' too.
  * If this param is left blank, it will start on Jetty's default port.
  */
 ChromeConnect tabConnector = new ChromeConnect(100);
 
 TabManager chrome = tabConnector.getTabManager(); //gets the TabManager object
 ```
 
 ChromeConnect initializes an internal Jetty server. Once everything is ready to go, TabManager waits for a chrome extension to connect.
 
 ## Creating a new tab
 I have implemented an observer and used future's to wait for a tab response before populating a Tab object, so there is a slight delay between creating a tab and being able to use it, but this shouldn't interfere with any other code.

```Java
 Tab github = chrome.newTab("http://github.com/gerryfletch");
 ```
 If you do not set `chrome.newTab()` to an object, the tab will still be created, but you will not have direct control over it without searching the window for it.
 
 ## Reloading and closing a tab
 If you have a `Tab` object, reloading and closing is easy. If you don't, you can also close a tab by ID if you know it. There are actually two ways to reload/close, but both do the same thing:
 ```Java
 //TabManager: chrome
 
 Tab github = chrome.newTab("http://github.com/gerryfletch");
 
 github.reload(); //refreshes the tab
 chrome.reload(github); //passes the Tab object to TabManager to reload it
 
 github.close(); //closes the tab
 chrome.close(github); //passes the Tab object to TabManager to close it
 ```
 
 ## Switching between tabs
 The switchTo() method is used to changes the active attribute of a tab. As you would expect, only *one* tab can be active at any one time. There are also two ways to switch:
 ```Java
 //TabManager: chrome
 
 Tab github = chrome.newTab("http://github.com/gerryfletch");
 github.switchTo(); //switches to the github tab
 
 Tab gerryPersonal = chrome.newTab("http://gerryfletcher.me");
 chrome.switchTo(gerryPersonal); //passes the Tab object to TabManager to switch to it
 
 //At this point, the github tab is no longer active, but gerryPersonal is.
 ```
 
 ## Getting all tabs in list form
 This can be used to search for a specific tab that might not be an object yet. The getAllInWindow() method returns a List of Tabs.
 ```Java
  //TabManager: chrome
  
  List<Tab> allTabs = chrome.getAllInWindow();
  for(Tab tab : allTabs){
    System.out.println(tab.getUrl());
  }
 ```
 
 ## Sleeping the thread (timed waits)
 TabManager is event driven, so you wont have to sleep the thread to wait for something to happen. Below is an example:
 ```Java
 Tab facebook = chrome.newTab("https://facebook.com"); //notoriously slow to load!
 facebook.switchTo(); //this will not execute until the tab has finished loading
 facebook.close(); //this will not execute until the tab has been switched to
 ```
 
 However, if you do need to sleep, you can use the sleep() method:
 
 ```Java
 //TabManager: chrome
 Tab github = chrome.newTab("http://github.com/gerryfletch");
 github.switchTo();
 
 chrome.sleep(2000); //time in MS (2 seconds)
 
 github.close();
 ```
 In this case, the tab will open, switch to it, wait 2 seconds, and close.
 
 ## Tab Attributes
 **IMPORTANT:** There is no constant tabListener updating the Tab objects. I have tried to update values as they change with the methods, but if you have any problems, submit an issue. For now, use the `Tab.Update();` method. This will get the current values.
 
| Name            | Type    | Method              |
|-----------------|---------|---------------------|
| active          | boolean | isActive()          |
| audible         | boolean | isAudible()         |
| autoDiscardable | boolean | isAutoDiscardable() |
| discarded       | boolean | isDiscarded()       |
| highlighted     | boolean | isHighlighted()     |
| pinned          | boolean | isPinned()          |
| selected        | boolean | isSelected()        |
| incognito       | boolean | isIncognito()       |
|                 |         |                     |
| faviconUrl      | String  | getFaviconUrl()     |
| status          | String  | getStatus()         |
| title           | String  | getTitle()          |
| url             | String  | getUrl()            |
|                 |         |                     |
| width           | int     | getWidth()          |
| height          | int     | getHeight()         |
|                 |         |                     |
| id              | int     | getId()             |
| index           | int     | getIndex()          |
| windowId        | int     | getWindowId()       |
