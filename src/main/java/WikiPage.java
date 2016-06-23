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
	public String getTitle(){
		return this.Name;
	}
	
	public List<String> getSubSectionTitles(){
		LinkedList<String> titles = new LinkedList<String>();
		for(PageElement pe : this.elements){
			titles.add(pe.headline);
		}
		return titles;
	}
	
	public List<String> getMainPageParagraphs(){
		return this.headerText;
	}
	
	public String getHeaderImage(){
		return this.headerImagesrc;
	}
	
	public List<String> getSubSectionTexts(){
		LinkedList<String> texts = new LinkedList<String>();
		for(PageElement elem : elements){
			for(String text : elem.paragraphs){
				texts.add(text);
			}
		}
		return texts;
	}
	
	public List<String> getAllParagraphImages(){
		LinkedList<String> images = new LinkedList<String>();
		for(PageElement elem : this.elements){
			if(!elem.imageSources.isEmpty())
				images.addAll(elem.imageSources);
		}
		return images;
	}
	
	
	public List<String> getImagesByParagraph(List<String> paragraphs){
		LinkedList<String> images = new LinkedList<String>();
		
		for(PageElement elem : this.elements){
			if(paragraphs.contains(elem.headline) && !elem.imageSources.isEmpty())
				images.addAll((elem.imageSources));
		}
		return null;
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