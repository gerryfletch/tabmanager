package tabmanager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtils {
	/**
	 * Takes a JSON message and finds the response value.
	 * <p>This is usually a response from the websocket, but can be used to get the response
	 * from any JSON if it contains that attribute.
	 * @param msg	The <i>JSON formatted message.</i> This is sent as an Obj, because it is via the events.
	 * @return A string with the response value.
	 */
	public static String getJSONResponse(Object msg) {
		Gson gson = new Gson();
		JsonObject response = gson.fromJson(msg.toString(), JsonObject.class);
		String responseString = response.get("response").getAsString();
		return responseString;
	}
}