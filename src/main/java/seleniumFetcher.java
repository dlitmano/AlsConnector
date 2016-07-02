import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class seleniumFetcher {
	
	WebDriver driver;	
	public seleniumFetcher(){
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		if(driver == null)
			driver = new ChromeDriver();
		Dimension windowMinSize = new Dimension(100,100);
		driver.manage().window().setSize(windowMinSize);
	}
	
	
	public WikiPage getTopicsByList(String language, String searchTerm, List<String> wantedTopics, Boolean getIntro){
		String url= String.format("https://%s.wikivoyage.org/wiki/%s", language, searchTerm);
		driver.get(url);
		WikiPage currentPage = new WikiPage(searchTerm);
		
		if(getIntro)
			for(WebElement el : driver.findElements(By.cssSelector("#mw-content-text>p"))){
				if(el.getText().length()>1){
					currentPage.headerText.add(el.getText());
					currentPage.headerImagesrc = driver.findElement(By.cssSelector(".wpb-topbanner>a>img")).getAttribute("src");
				}
			}
		
		List<WebElement> topicElements =  driver.findElements(By.className("mw-h2section"));
				
		for(WebElement elem : topicElements){
			String headLine = elem.findElement(By.className("mw-headline")).getText();
			if(!wantedTopics.contains(headLine))
				continue;
			
			PageElement currentPageElement = new PageElement(headLine);
			//get Images for subsection
			for(WebElement pic : elem.findElements(By.cssSelector("a>img")))
				currentPageElement.imageSources.add(pic.getAttribute("src"));
			
			//get subsections and add them to current page
			for(WebElement subElem : elem.findElements(By.className("mw-h3section"))){
				PageElement subElement = new PageElement(subElem.findElement(By.tagName("h3")).getText());
				for(WebElement el : subElem.findElements(By.cssSelector("p"))){
					subElement.paragraphs.add(el.getText());
				}
				currentPageElement.subSection.add(subElement);
			}
			
			currentPage.elements.add(currentPageElement);
		}
		return currentPage;
	}
	
	public void closeBrowser(){
		driver.quit();
	}
	
}
