import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Currency;
import java.util.List;

import javax.xml.bind.JAXBException;

import als.connector.AlsConnector;
import als.connector.ExceptionCollectionTypeHelper;
import als.connector.JobCollectionResponseTypeHelper;
import als.connector.JobCollectionTypeHelper;
import exception.AlsException;
import jaxb.AbstractAlsPlaceable;
import jaxb.generated.content.ContentCollectionType;
import jaxb.generated.content.ContentType;
import jaxb.generated.content.ImageType;
import jaxb.generated.exception.ExceptionCollectionType;
import jaxb.generated.job.AbstractPageType;
import jaxb.generated.job.ConfigurationType;
import jaxb.generated.job.ContentToPageCollectionType;
import jaxb.generated.job.ContentToPageType;
import jaxb.generated.job.JobCollectionType;
import jaxb.generated.job.JobType;
import jaxb.generated.job.LayoutGenerationJobType;
import jaxb.generated.job.LayoutGeneratorGridType;
import jaxb.generated.job.LayoutGeneratorTableType;
import jaxb.generated.job.MultiPageType;
import jaxb.generated.job.OutputCollectionType;
import jaxb.generated.job.OutputWebserverType;
import jaxb.generated.job.PageCollectionType;
import jaxb.generated.job.PageDesignType;
import jaxb.generated.jobResponse.JobCollectionResponseType;
import jaxb.generated.jobResponse.JobResponseType;
import jaxb.generated.product.ProductType;


import static java.nio.charset.StandardCharsets.UTF_8;

public class AlsConnectorExample {

	// TODO - set correct API-Key
	private static final String API_KEY_ = "XJrEnTJksEFp9TdNm3VxFjBWkNRhpmdN";
	private static final String ALS_URL_ = "http://als.dev.medieninnovation.com:4003/";
	private static final String JOB_XML_FILE_NAME_ = "/test.xml";

	
			//"/JobCollection.xml";

	public static void main(String[] args)
			throws UnsupportedEncodingException, IOException, JAXBException, AlsException {
		YelpAPI yelpAPI = new YelpAPI();
		
		
		AlsConnector alsConnector = new AlsConnector(ALS_URL_);

		// ------------------------- SEND JOB XML-FILE
		// --------------------------------------
	//	Object response = exampleSendFile(alsConnector);
	//	handleAlsResponse(response);

		// ------------------ READ FROM XML-FILE, EDIT, AND SEND
		// -----------------------------
	//	Object response = exampleReadJobFromFile(alsConnector);
	//	handleAlsResponse(response);

		// -------------------- CREATE AND SEND JOB - SHORT EXAMPLE
		// --------------------------
	//	Object response = exampleCreateAndSendJobShort(alsConnector);
	//	handleAlsResponse(response);

		// -------------------- CREATE AND SEND JOB - LONG EXAMPLE
		// ---------------------------
		Object response = exampleCreateAndSendJobLong(alsConnector);
		handleAlsResponse(response);

	}

	private static Object exampleSendFile(AlsConnector alsConnector)
			throws UnsupportedEncodingException, IOException, JAXBException, AlsException {
		// send a XML-file directly
		return alsConnector.sendJobCollectionXMLFileToServer(JOB_XML_FILE_NAME_, UTF_8);
	}

	private static Object exampleReadJobFromFile(AlsConnector alsConnector)
			throws JAXBException, IOException, AlsException {
		// build JobCollectionType object from XML file
		JobCollectionType jobCollectionType2 = JobCollectionTypeHelper
				.createInstance(AlsConnector.class.getClass().getResourceAsStream("/BeispielJobEebTeam2.xml"));

		// jobCollectionType may be edited

		// send JobCollectionType object created from XML file
		return alsConnector.sendJobCollectionTypeToServer(jobCollectionType2);
	}

	private static Object exampleCreateAndSendJobShort(AlsConnector alsConnector)
			throws IOException, JAXBException, AlsException {
		// build JobCollectionType object by your own
				JobCollectionType jobCollectionType1 = new JobCollectionType();
				ContentCollectionType contentCollection = new ContentCollectionType();
				jobCollectionType1.setContentCollection(contentCollection);

				// Product
				ProductType product = new ProductType();
				product.setTitle("Mein Titel");
				// TODO - initialize product
				// ...
				contentCollection.getContents().add(product);

				// job
				JobType job = new JobType();
				ConfigurationType configuration = new ConfigurationType();
				configuration.setApiKey(API_KEY_);
				job.setConfiguration(configuration);
				// ...

				jobCollectionType1.getJobs().add(job);

		// send JobCollectionType
		return alsConnector.sendJobCollectionTypeToServer(jobCollectionType1);

	}

	private static Object exampleCreateAndSendJobLong(AlsConnector alsConnector)
			throws IOException, JAXBException, AlsException {
		JobCollectionType jobCollectionType = new JobCollectionType();
		ContentCollectionType contentCollection = new ContentCollectionType();

		
		for (int i = 0; i < YelpAPI.SEARCH_LIMIT; i++) {
			// Produkte erstellen
			
			String id = "p"+i;
			
			ProductType product = new ProductType();
			product.setPlaceableRefId(id);
			//product1.setProductId(id);
			product.setTitle(YelpAPI.RESULTS[i][0]);
			System.out.println(YelpAPI.RESULTS[i][0]);
			product.setSubTitle(id);
			product.setShortDescription(YelpAPI.RESULTS[i][1]);
			product.setLongDescription("Long Description");
			//product1.setLink("www.google.de");
			product.setPriceGross((float) 234.64);
			product.setAvailability("available");
			product.setTaxes((float) 19.0);

		
			// CategoryType category1 = new CategoryType();
			// category1.setName("Cat1 P1");
			// CategoryType category2 = new CategoryType();
			// category2.setName("Cat2 P1");
			// product1.getCategories().add(category1);
			// product1.getCategories().add(category2);

			ImageType imgProduct = new ImageType();
			imgProduct.setUrl(YelpAPI.RESULTS[i][2]);
			imgProduct.setWidth(400);
			imgProduct.setHeight(300);
			imgProduct.setAlternateText("Bild");
			product.getImages().add(imgProduct);

			// die Produkte der contentCollection hinzufügen
			contentCollection.getContents().add(product);
			//System.out.println(contentCollection.getContents().get(i).getPlaceableRefId());;
			
			
		}
		
		// Job erstellen
		JobType job = new JobType();
		PageCollectionType pageCollection = new PageCollectionType();
		AbstractPageType multiPage = new MultiPageType();
		
		pageCollection.getPages().add(multiPage);
		
		ContentToPageCollectionType contentToPageCollection = new ContentToPageCollectionType();
		multiPage.setContentToPageCollection(contentToPageCollection);
		//((MultiPageType) multiPage).setMaxPages(10);
		
		// TODO jedes Produkt der Seite hinzufügen
		// weight every content equal
		float defaultWeight = 1f / contentCollection.getContents().size();
		
		System.out.println(contentCollection.getContents());
		
		for (AbstractAlsPlaceable content : contentCollection.getContents()) {
			ContentToPageType contentToPage = new ContentToPageType();
			contentToPage.setContentRef(content);
			contentToPage.setWeight(defaultWeight);
			contentToPageCollection.getContentToPage().add(contentToPage);
		}
		System.out.println("-----------------");

		PageDesignType pageDesign = new PageDesignType();
		pageDesign.setCssFile("default.css");
		pageDesign.setCss("page{ background-image: url(http://www.quinn.de/uploads/tx_dstsupersized/Hintergrundbild_grau_02.jpg); border: 1px solid black;}headline{font-size: 8.3pt;line-height: 9pt;}subheadline{font-size: 7.5pt;line-height: 8pt;}image{padding: 2px 0;}product {font-size: 7.5pt;line-height: 8.5pt;}first product {font-size: 10pt;line-height: 12pt;}first product currency {font-size: 15pt;line-height: 17pt;}first product headline {font-size: 11pt;line-height: 12pt;}first product subheadline {font-size: 10pt;line-height: 11pt;}");
		pageCollection.setPageDesign(pageDesign);

		LayoutGenerationJobType jobDescription = new LayoutGenerationJobType();
		jobDescription.setLayoutGenerator(new LayoutGeneratorTableType());
		jobDescription.setPageCollection(pageCollection);
		job.setJobDescription(jobDescription);

		OutputCollectionType outputCollection = new OutputCollectionType();
		OutputWebserverType outputWebserver = new OutputWebserverType();
		outputWebserver.setDirectOutput(true);
		outputWebserver.setOutputRefId("outputWebserver");
		outputWebserver.setAddPagination(true);
		outputWebserver.setShowSubmittedJob(false);
		outputWebserver.setShowWaiting(false);

		outputCollection.getOutputs().add(outputWebserver);

		job.setOutputCollection(outputCollection);

		ConfigurationType conf = new ConfigurationType();
		conf.setApiKey(API_KEY_);
		conf.setMaxRenderingTime(2);
		conf.setMinLayouts(3);
		job.setConfiguration(conf);

		// contentCollection jobColl hinzufÃ¼gen
		jobCollectionType.setContentCollection(contentCollection);
		jobCollectionType.getJobs().add(job);

		return alsConnector.sendJobCollectionTypeToServer(jobCollectionType);
	}

	private static void handleAlsResponse(Object response) throws JAXBException {

		if (response instanceof JobCollectionResponseType) {
			JobCollectionResponseType jobCollectionResponseType = (JobCollectionResponseType) response;
			for (JobResponseType jobResponseType : jobCollectionResponseType.getJobResponses())
				System.out.println(String.format("Status for job with ID '%s': %s", jobResponseType.getJobId(),
						jobResponseType.getStatus()));

			System.out.println(JobCollectionResponseTypeHelper.toXml(jobCollectionResponseType));
		} else if (response instanceof ExceptionCollectionType) {
			ExceptionCollectionType exceptionCollectionType = (ExceptionCollectionType) response;
			System.out.println(ExceptionCollectionTypeHelper.toXml(exceptionCollectionType));
		} else
			System.out.println(response);
	}
	
	

}
