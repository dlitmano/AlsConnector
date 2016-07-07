import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exception.AlsException;

public class QueryHandler implements Handler {

	public static String land;
	public static String stadt;
	
	@Override
	public void addLifeCycleListener(Listener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFailed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStarting() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStopping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeLifeCycleListener(Listener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("Ich druecke ma nur BASKET");
		
		
		// TODO Auto-generated method stub
	     //	response.setContentType("text/html;charset=utf-8");
	   //  	response.setHeader("content-disposition", "attachment; filename=TravelGuide.pdf");
	  //   	response.setContentType("application/pdf");
	        response.setStatus(HttpServletResponse.SC_OK);
	        baseRequest.setHandled(true);
	        String queryString = baseRequest.getQueryString();
	      // http://localhost:8080/download?quiry=koln%20germany
	        //System.out.println();
	        response.getWriter().println(queryString +" ->>>  "+  baseRequest.getMethod());
	        if(baseRequest.getMethod().toLowerCase().equals("get")){
	        	// TODO ...
	        	if(target.toLowerCase().equals("/download-as-pdf")){
	     	     	response.setHeader("content-disposition", "attachment; filename=TravelGuide.pdf");
	      	     	response.setContentType("application/pdf");
	      	     	//queryString abprüfen um id rauszufinden
	      	     	
	      	     //	response.getOutputStream().setWriteListener(arg0);;
	        	}
	        	return;
	        }
	        
	        if(baseRequest.getMethod().toLowerCase().equals("post")) {
	        	// Json Datei [{"id":"1", "city":"1","type":"hotel"}]
	        	// TODO ...
	        	
	        	// Json Datei [{"id":"1", "city":"1","type":"hotel"}]
	        	//POST /on-countries
	        	//{"query":"a"}
	        	        
	        	
	        	
	        	StringBuffer jb = new StringBuffer();
	        	  String line = null;
	        	  try {
	        	    BufferedReader reader = request.getReader();
	        	    while ((line = reader.readLine()) != null)
	        	      jb.append(line);
	        	  } catch (Exception e) { /*report an error*/ }
	        	 
	        	 System.out.println(jb.toString());
	        	 
	        	 // 
	        	
	        	  try{
	        	  String jbb = jb.toString();
	        	  String[] ersterSplit= jbb.split("=");
	        	  String ohneQuery = ersterSplit[1];
	        	  String[] zweiterSplit = ohneQuery.split("%2C");
	        	  String stadtt = zweiterSplit[0]; //FEST
	        	  String landUnfertig = zweiterSplit[1];
	        	  String[] dritterSplit = landUnfertig.split("+");
	        	  String landFastFertig = dritterSplit[1];
	        	  String[] vierterSplit = landFastFertig.split("+");
	        	  String landd = vierterSplit[0];	//FEST
	        	  
	        	  System.out.println(stadtt);
	        	  System.out.println(landd);
	        	  
	        	  stadt = stadtt;
	        	  land = landd;
	        	  
	        	  }
	        	  catch(Exception e){
	        		  
	        	  }
	        	
		    
	       
	        	       	        	       
	        	//POST /on-cities
	        	//{"query":"a","country":{"id":"2","name":"Country 2","description":"Country from server"}}
	        	
	        	//POST /on-hotels
	        	//{"query":"a","city":{"country":"2","id":"2","name":"City 2","description":"City from server"}}
	        	
	        	//POST /on-pdf
	        	//[{"country":"2","city":"2","id":"2","name":"Hotel 2","description":"Hotel from server"}]
	        	
	        	
	        	
	        
	        	
//	        	try{
//	        		Object obj = parser.parse(new FileReader(""));
//	        		JSONObject jsonObject = (JSONObject) obj;
//	        		
//	        		String id1 = (String) jsonObject.get("ID");
//	        		int id = Integer.parseInt(id1);
//	        		String city = (String) jsonObject.get("CITY");
//	        		String type = (String) jsonObject.get("TYPE");
//	        		
//	        	    YelpAPI.TERM = "Landmarks & Historical Buildings";
//	        		YelpAPI.LOCATION = "Cologne, Germany";
//	        	    AlsConnectorExample als = new AlsConnectorExample();
//	        	    
//	        		//packe Infos in Yelp-Api zum Anfrage schicken.
//	        		//YelpAPI.TERM = "Bar";
//	        		//YelpAPI.LOCATION = "Cologne";
//	        		
//	        		//AlsConnectorExample als = new AlsConnectorExample();
//	        		
//	        		//NUN: XML Generieren, BZW. XML-Struktur (die irgendwo liegt, hoffentlich,
//	        		//aufrufen, und dann einfach Werte einfuegen).
//
//	        	
//	        	} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 

	        	
	        	return;
	        }
	        	
	        
	        
	        response.getWriter().println(target);

	}

	@Override
	public void setServer(Server arg0) {
		// TODO Auto-generated method stub

	}

}
