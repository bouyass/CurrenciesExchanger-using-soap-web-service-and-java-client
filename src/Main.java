import java.io.IOException;

import javax.xml.ws.Endpoint;

import service.CurrencyUpImpl;

public class Main {
	

	
	public static void main(String[] args) throws IOException{

		
		String url = "http://0.0.0.0:8888/";
		Endpoint.publish(url, new CurrencyUpImpl());
		System.out.println("Web service deployé sur "+ url );
		
	

       
       

	}

}
