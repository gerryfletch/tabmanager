package me.gerryfletcher.tabmanager;

import me.gerryfletcher.tabmanager.connect.ChromeConnect;
import me.gerryfletcher.tabmanager.tab.Tab;
import me.gerryfletcher.tabmanager.tab.TabManager;


public class Main {
    public static void main(String[] args) {
        ChromeConnect chromeConnect = new ChromeConnect(100);
        TabManager chrome = chromeConnect.getTabManager();
        Tab tab = chrome.getTab(6);

        System.out.println(tab.getTitle());
    }
}
