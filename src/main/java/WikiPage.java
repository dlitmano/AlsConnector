import java.awt.JobAttributes.DestinationType;
import java.util.LinkedList;
import java.util.List;

import jaxb.generated.content.ContentCollectionType;
import jaxb.generated.content.ImageType;
import jaxb.generated.product.CustomPropertyType;
import jaxb.generated.product.ProductType;

/**
 * 
 * @author Patrick Dohmen
 *Datenstruktur für die Wikivoyage Seiten
 */

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
	
	public ProductType getWikiPageAsProductType(){
		
		ProductType WikiPage = new ProductType();
		WikiPage.setTitle(this.Name);
		String headerTextComplete = "";
		for(String s : this.headerText)
			headerTextComplete += s;
		WikiPage.setLongDescription(headerTextComplete);
		ImageType image = new ImageType();
		image.setUrl(this.headerImagesrc);
		image.setAlternateText("Header");
		WikiPage.getImages().add(image);

		for(PageElement el :this.elements)
		{
			CustomPropertyType temp = new CustomPropertyType();
			temp.setName(el.headline);
			temp.setValue(el.getPageText());
			ImageType tempImage;
			for(String s :el.imageSources){
				tempImage = new ImageType();
				tempImage.setUrl(s);
				WikiPage.getImages().add(tempImage);
			}
			WikiPage.getCustomProperties().add(temp);
			
		}
		
		return WikiPage;
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
	
	public String getPageText(){
		String text = "";
		for(String p : this.paragraphs){
			text += p;
		}
		for(int i=0; i<this.subSection.size(); i++){
			String text2 = "";
			PageElement temp = this.subSection.get(i);
			for(String s : temp.paragraphs)
				text2 += s;
			text += text2;
		}

		return text;
	}
	
	
}