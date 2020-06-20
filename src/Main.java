import java.io.IOException;

import javax.xml.ws.Endpoint;

import service.CurrencyUp;

public class Main {
	

	
	public static void main(String[] args) throws IOException{

		
		String url = "http://0.0.0.0:8888/";
		Endpoint.publish(url, new CurrencyUp());
		System.out.println("Web service deployé sur "+ url );
		
	

       
       

	}

}
