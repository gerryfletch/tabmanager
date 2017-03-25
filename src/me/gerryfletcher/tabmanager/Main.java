package me.gerryfletcher.tabmanager;

import java.util.List;

import me.gerryfletcher.tabmanager.connect.ChromeConnect;
import me.gerryfletcher.tabmanager.tab.Tab;
import me.gerryfletcher.tabmanager.tab.TabManager;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object

		Tab google = chrome.newTab("http://google.com");
		Tab github = chrome.newTab("http://github.com");
		
		chrome.switchTo(github);
		
		System.out.println("\n");
		System.out.println("Google: " + google.isActive());
		System.out.println("Github: " + github.isActive());
		System.out.println("\n");
		
		chrome.switchTo(google);
		
		System.out.println("\n");
		System.out.println("Google: " + google.isActive());
		System.out.println("Github: " + github.isActive());
		System.out.println("\n");
		
		chrome.switchTo(github);
		
		System.out.println("\n");
		System.out.println("Google: " + google.isActive());
		System.out.println("Github: " + github.isActive());
		System.out.println("\n");
		
		/**
		 * TODO: Second param to newTab, boolean active
		 * TODO: create Update() method
		 * TODO: return a list with getAllInWindow()
		 */
	}
	
}
