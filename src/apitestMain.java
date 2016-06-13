import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class apitestMain {

	public static void main(String[] args) throws Exception {

		WikiVoyageApi http = new WikiVoyageApi();
		LinkedList<String> wantedSections = new LinkedList<String>();
		wantedSections.add("Understand");
		wantedSections.add("Get in");
		seleniumFetcher fetcher = new seleniumFetcher();

		try {
			WikiPage resultPage = fetcher.getTopicsByList("en", "cologne", wantedSections, true);
			System.out.println("finished");
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		finally
		{
			fetcher.closeBrowser();
		}
	}

}
