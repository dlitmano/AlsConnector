import java.util.LinkedList;
import java.util.List;

public class WikiPage {
	public String Name;
	public String headerImagesrc;
	public LinkedList<String> headerText;
	public LinkedList<PageElement> elements;

	public WikiPage(String Name) {
		this.Name = Name;
		elements = new LinkedList<PageElement>();
		headerText = new LinkedList<String>();
	}
}

class PageElement {
	public String headline;
	public LinkedList<String> paragraphs;
	public LinkedList<PageElement> subSection;
	public LinkedList<String> imageSources;
	
	public PageElement(String headLine) {
		headline = headLine;
		paragraphs = new LinkedList<String>();
		subSection = new LinkedList<PageElement>();
		imageSources = new LinkedList<String>();
	}
}