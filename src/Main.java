import javax.xml.ws.Endpoint;

import service.CurrencyUpdater;

public class Main {
	

	
	public static void main(String[] args){

		
		String url = "http://0.0.0.0:8888/";
		Endpoint.publish(url, new CurrencyUpdater());
		System.out.println("Web service deployé sur "+ url );

       
       

	}

}
