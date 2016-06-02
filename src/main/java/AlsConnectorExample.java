import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import als.connector.AlsConnector;
import als.connector.ExceptionCollectionTypeHelper;
import als.connector.JobCollectionResponseTypeHelper;
import als.connector.JobCollectionTypeHelper;
import exception.AlsException;
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
	private static final String JOB_XML_FILE_NAME_ = "/BeispielJobEebTeam2.xml";
			//"/JobCollection.xml";

	public static void main(String[] args)
			throws UnsupportedEncodingException, IOException, JAXBException, AlsException {
		AlsConnector alsConnector = new AlsConnector(ALS_URL_);

		// ------------------------- SEND JOB XML-FILE
		// --------------------------------------
		Object response = exampleSendFile(alsConnector);
		handleAlsResponse(response);

		// ------------------ READ FROM XML-FILE, EDIT, AND SEND
		// -----------------------------
		response = exampleReadJobFromFile(alsConnector);
		handleAlsResponse(response);

		// -------------------- CREATE AND SEND JOB - SHORT EXAMPLE
		// --------------------------
		response = exampleCreateAndSendJobShort(alsConnector);
		handleAlsResponse(response);

		// -------------------- CREATE AND SEND JOB - LONG EXAMPLE
		// ---------------------------
		response = exampleCreateAndSendJobLong(alsConnector);
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
				.createInstance(AlsConnector.class.getClass().getResourceAsStream("/JobCollection-2.xml"));

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

		for (int i = 0; i < 1; i++) {
			// Produkte erstellen
			ProductType product1 = new ProductType();
			product1.setPlaceableRefId("test1");
			product1.setTitle("neues Produkt - Rasierer");
			product1.setSubTitle("subtitle P1");
			product1.setShortDescription("short Des P1");
			product1.setLongDescription("Long Des");
			product1.setLink("www.google.de");
			product1.setPriceGross(39.99f);

			// CategoryType category1 = new CategoryType();
			// category1.setName("Cat1 P1");
			// CategoryType category2 = new CategoryType();
			// category2.setName("Cat2 P1");
			// product1.getCategories().add(category1);
			// product1.getCategories().add(category2);

			ImageType imgProduct1 = new ImageType();
			imgProduct1.setUrl("http://www.messerschmied-eisele.de/grossfotos/rasierer/philips_rasierer.jpg");
			imgProduct1.setWidth(400);
			imgProduct1.setHeight(300);
			imgProduct1.setAlternateText("Rasierer Bild");
			product1.getImages().add(imgProduct1);

			// die Produkte der contentCollection hinzufügen
			contentCollection.getContents().add(product1);
		}

		// Job erstellen
		JobType job = new JobType();
		PageCollectionType pageCollection = new PageCollectionType();

		AbstractPageType multiPage = new MultiPageType();
		pageCollection.getPages().add(multiPage);
		ContentToPageCollectionType contentToPageCollection = new ContentToPageCollectionType();
		multiPage.setContentToPageCollection(contentToPageCollection);
		ContentToPageType contentToPage = new ContentToPageType();
		contentToPageCollection.getContentToPage().add(contentToPage);

		// TODO jedes Produkt der Seite hinzufügen
		// weight every content equal
		float defaultWeight = 1f / contentCollection.getContents().size();
		for (ContentType content : contentCollection.getContents()) {
			contentToPage.setContentRef(content);
			contentToPage.setWeight(defaultWeight);
		}

		PageDesignType pageDesign = new PageDesignType();
		pageDesign.setCssFile("default.css");
		pageDesign.setCss("page{margin-top:0}");
		pageCollection.setPageDesign(pageDesign);

		LayoutGenerationJobType jobDescription = new LayoutGenerationJobType();
		jobDescription.setLayoutGenerator(new LayoutGeneratorTableType());
		jobDescription.setPageCollection(pageCollection);
		job.setJobDescription(jobDescription);

		OutputCollectionType outputCollection = new OutputCollectionType();
		OutputWebserverType outputWebserver = new OutputWebserverType();
		outputWebserver.setDirectOutput(true);
		outputWebserver.setOutputRefId("outputWebserver");
		outputWebserver.setAddPagination(false);
		outputWebserver.setShowSubmittedJob(false);
		outputWebserver.setShowWaiting(false);

		outputCollection.getOutputs().add(outputWebserver);

		job.setOutputCollection(outputCollection);

		ConfigurationType conf = new ConfigurationType();
		conf.setApiKey(API_KEY_);
		conf.setMaxRenderingTime(2);
		conf.setMinLayouts(12);
		job.setConfiguration(conf);

		// contentCollection jobColl hinzufügen
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
