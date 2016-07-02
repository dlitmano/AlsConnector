import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WikiVoyageApi {

	private final String USER_AGENT = "Mozilla/5.0";

	public Map<String, String> getSectionsForSearchTerm(String language, String searchTerm) throws Exception {
		String url = String.format("https://%s.wikivoyage.org/w/api.php?action=parse&page=%s&prop=sections&format=json",
				language, searchTerm);
		JSONObject json = getJSONfromURL(url);
		JSONObject parse = (JSONObject) json.get("parse");
		JSONArray sections = (JSONArray) parse.get("sections");
		Map<String, String> sectionsList = new HashMap<String, String>();

		for (int i = 0; i < sections.size(); i++) {
			JSONObject jsonobject = (JSONObject) sections.get(i);
			String index = (String) jsonobject.get("index");
			String anchor = (String) jsonobject.get("anchor");
			sectionsList.put(index, anchor);
		}
		return sectionsList;
	}

	private JSONObject getJSONfromURL(String url) throws IOException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		StringBuffer buffer = new StringBuffer();
		int read;
		char[] chars = new char[1024];
		while ((read = reader.read(chars)) != -1)
			buffer.append(chars, 0, read);
		return (JSONObject) new JSONParser().parse(buffer.toString());
	}

}
