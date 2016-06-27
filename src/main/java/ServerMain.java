import org.eclipse.jetty.server.Server;

public class ServerMain {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub


	    Server server = new Server(8080);
	    server.setHandler(new QueryHandler());
	    
	 
	    server.start();
	    server.join();
	    
	}

}
