package websockets;

import static spark.Spark.*;

import java.util.Scanner;

import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.util.log.Slf4jLog;

public class Main {
	public static void main(String[] args){
		
		port(100);
		webSocket("/", ExtWebSocket.class);
		init();
		
	}
}
