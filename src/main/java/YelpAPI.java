
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;

/**
 * Code sample for accessing the Yelp API V2.
 * <p>
 * This program demonstrates the capability of the Yelp API version 2.0 by using
 * the Search API to query for businesses by a search term and location, and the
 * Business API to query additional information about the top result from the
 * search query.
 * <p>
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp
 * Documentation</a> for more info.
 */
public class YelpAPI {

	private static final String API_HOST = "api.yelp.com";
	private static final String DEFAULT_TERM = "bars";
	private static final String DEFAULT_LOCATION = "Mannheim, Germany";
	private static final int SEARCH_LIMIT = 10;
	private static final String SEARCH_PATH = "/v2/search";
	private static final String BUSINESS_PATH = "/v2/business";

	/*
	 * Update OAuth credentials below from the Yelp Developers API site:
	 * http://www.yelp.com/developers/getting_started/api_access
	 */
	public static final String CONSUMER_KEY = "vYtQVBKAJvvHSR3p05e9tA";
	public static final String CONSUMER_SECRET = "uBB9-gtSr1jGs4KqQDQ-jniPFY0";
	public static final String TOKEN = "QE1_j18ObMuKZCzZTzOrBlLjbP70D29q";
	public static final String TOKEN_SECRET = "Bfij-IP9uMrligKQXsn4GGg4Mcw";

	OAuthService service;
	Token accessToken;

	public YelpAPI() {
		YelpAPICLI yelpApiCli = new YelpAPICLI();
		String[] args = { "--term", DEFAULT_TERM, "--location", DEFAULT_LOCATION };
		new JCommander(yelpApiCli, args);
		// new JCommander(yelpApiCli, args);

		YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
		queryAPI(yelpApi, yelpApiCli);
		
		
	}

	/**
	 * Setup the Yelp API OAuth credentials.
	 *
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */

	public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {

		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey).apiSecret(consumerSecret)
				.build();
		this.accessToken = new Token(token, tokenSecret);

	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * See
	 * <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp
	 * Search API V2</a> for more info.
	 *
	 * @param term
	 *            <tt>String</tt> of the search term to be queried
	 * @param location
	 *            <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchForBusinessesByLocation(String term, String location) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and sends a request to the Business API by business ID.
	 * <p>
	 * See
	 * <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp
	 * Business API V2</a> for more info.
	 *
	 * @param businessID
	 *            <tt>String</tt> business ID of the requested business
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchByBusinessId(String businessID) {

		OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);

		System.out.println("test       " + request.toString());
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and returns an {@link OAuthRequest} based on the API endpoint
	 * specified.
	 *
	 * @param path
	 *            API endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
		return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 *
	 * @param request
	 *            {@link OAuthRequest} corresponding to the API request
	 * @return <tt>String</tt> body of API response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		System.out.println("Querying " + request.getCompleteUrl() + " ...");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();

		System.out.println(request.getOauthParameters() + " " + request.getRealm());
		System.out.println("Response:" + request.toString());

		return response.getBody();
	}

	/**
	 * Queries the Search API based on the command line arguments and takes the
	 * first result to query the Business API.
	 *
	 * @param yelpApi
	 *            <tt>YelpAPI</tt> service instance
	 * @param yelpApiCli
	 *            <tt>YelpAPICLI</tt> command line arguments
	 */
	public static void queryAPI(YelpAPI yelpApi, YelpAPICLI yelpApiCli) {
		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(yelpApiCli.term, yelpApiCli.location);

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		System.out.println("----------------------------------------------------------");
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
			System.exit(1);
		}
		
		
		JSONArray businesses = (JSONArray) response.get("businesses");
		JSONObject firstBusiness = (JSONObject) businesses.get(0);
		JSONObject secondBusiness = (JSONObject) businesses.get(1);
		JSONObject thirdBusiness = (JSONObject) businesses.get(2);
		
		String firstBusinessID = firstBusiness.get("id").toString();		
		String secondBusinessID = secondBusiness.get("id").toString();
		String thirdBusinessID = thirdBusiness.get("id").toString();

		System.out.println(String.format(
				"%s businesses found, querying business info for the top results \"%s\" , \"%s\" and \"%s\"",
				businesses.size(), firstBusinessID, secondBusinessID, thirdBusinessID));

		byte businesstext[] = firstBusinessID.toString().getBytes(ISO_8859_1);
		
		
		// String business = new String(businesstext, UTF_8);
		// System.out.println(business);
		
		// JSONArray arr = new JSONArray(result);
		// JSONObject jObj = arr.getJSONObject(0);
		// String date = jObj.getString("NeededString");

		// Select the first business and display business details

		String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
		System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID.toString()));
		//System.out.println(businessResponseJSON);
		String name_first = firstBusiness.get("name").toString();
		String rating_first = firstBusiness.get("rating").toString();
		String rating_img_first = firstBusiness.get("rating_img_url").toString();
		String url_first = firstBusiness.get("mobile_url").toString();
		String phone_first = firstBusiness.get("phone").toString();
		String image_first = firstBusiness.get("image_url").toString().replaceAll("ms.jpg", "o.jpg");
		System.out.println("Name: "+name_first);
		System.out.println("Rating: "+rating_first);
		System.out.println("Sterne: "+rating_img_first);
		System.out.println("URL: "+url_first);
		System.out.println("Phone: "+phone_first);
		System.out.println("Bild: "+image_first);
		
		
		
		System.out.println("----------------------------------------------------------");
		String businessResponseJSON2 = yelpApi.searchByBusinessId(secondBusinessID.toString());
		System.out.println(String.format("Result for business \"%s\" found:", secondBusinessID.toString()));
		//System.out.println(businessResponseJSON2);
		String name_second = secondBusiness.get("name").toString();
		String rating_second = secondBusiness.get("rating").toString();
		String rating_img_second = secondBusiness.get("rating_img_url").toString();
		String url_second = secondBusiness.get("mobile_url").toString();
		String phone_second = secondBusiness.get("phone").toString();
		String image_second = secondBusiness.get("image_url").toString().replaceAll("ms.jpg", "o.jpg");
		System.out.println("Name: "+name_second);
		System.out.println("Rating: "+rating_second);
		System.out.println("Sterne: "+rating_img_second);
		System.out.println("URL: "+url_second);
		System.out.println("Phone: "+phone_second);
		System.out.println("Bild: "+image_second);
		System.out.println("----------------------------------------------------------");
		String businessResponseJSON3 = yelpApi.searchByBusinessId(thirdBusinessID.toString());
		System.out.println(String.format("Result for business \"%s\" found:", thirdBusinessID.toString()));
		//System.out.println(businessResponseJSON3);
		String name_third = thirdBusiness.get("name").toString();
		String rating_third = thirdBusiness.get("rating").toString();
		String rating_img_third = thirdBusiness.get("rating_img_url").toString();
		String url_third = thirdBusiness.get("mobile_url").toString();
		String phone_third = thirdBusiness.get("phone").toString();
		String image_third = thirdBusiness.get("image_url").toString().replaceAll("ms.jpg", "o.jpg");
		System.out.println("Name: "+name_third);
		System.out.println("Rating: "+rating_third);
		System.out.println("Sterne: "+rating_img_third);
		System.out.println("URL: "+url_third);
		System.out.println("Phone: "+phone_third);
		System.out.println("Bild: "+image_third);
		System.out.println("----------------------------------------------------------");
		
	}

	/**
	 * Command-line interface for the sample Yelp API runner.
	 */
	public static class YelpAPICLI {
		@Parameter(names = { "-q", "--term" }, description = "Search Query Term")
		// public String term = DEFAULT_TERM;
		public String term;

		@Parameter(names = { "-l", "--location" }, description = "Location to be Queried")
		// public String location = DEFAULT_LOCATION;
		public String location;
	}

	/**
	 * Main entry for sample Yelp API requests.
	 * <p>
	 * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to
	 * run this example.
	 */
	/**
	 * public static void main(String[] args) { YelpAPICLI yelpApiCli = new
	 * YelpAPICLI(); new JCommander(yelpApiCli, args);
	 * 
	 * YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN,
	 * TOKEN_SECRET); queryAPI(yelpApi, yelpApiCli); }
	 */

}