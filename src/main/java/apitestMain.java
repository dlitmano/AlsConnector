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
			WikiPage resultPage = fetcher.getTopicsByList("de", "cologne", wantedSections, true);
			
			//Main Page
			String headerPs="";
			for(String s: resultPage.getMainPageParagraphs())
				headerPs += s;
			System.out.println(headerPs);
			
		//	resultPage.headerImagesrc();
		//	resultPage.Name;
			//Subsections
			for(PageElement elem : resultPage.elements){
				System.out.println(elem.headline);
				String PageElementText = "";
				for(String s : elem.paragraphs)
					PageElementText += s;
				System.out.println(PageElementText);
				
				//sub-sub-Sections
				String SubSectionText = "";
				for(PageElement sub : elem.subSection){
					for(String s : sub.paragraphs)
						SubSectionText += sub.paragraphs;
					System.out.println(SubSectionText);
			
					
				}
				
			}
			
//			resultPage.getImagesByParagraph();
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
