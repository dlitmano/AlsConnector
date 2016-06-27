import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;

public class QueryHandler implements Handler {

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
	        	
	        	return;
	        }
	        	
	        
	        
	        response.getWriter().println(target);

	}

	@Override
	public void setServer(Server arg0) {
		// TODO Auto-generated method stub

	}

}
