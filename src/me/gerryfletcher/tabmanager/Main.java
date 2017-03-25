package me.gerryfletcher.tabmanager;

import java.util.List;

import me.gerryfletcher.tabmanager.connect.ChromeConnect;
import me.gerryfletcher.tabmanager.tab.TabManager;

public class Main {
	
	public static void main(String[] args){
		
		ChromeConnect tabConnector = new ChromeConnect(100); //create the Connect object on port 100
		TabManager chrome = tabConnector.getTabManager(); //get the TabManager object

		
		/**
		 * TODO: return a list with getAllInWindow()
		 * TODO: create Update() method
		 */
	}
	
}
