import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import service.CurrencyUpImplProxy;
import service.IOException;

public class Main {

	public static void main(String[] args) throws IOException, RemoteException {
		
		String[] currencies = {"da","ls","fs","rr","yj","bt","yc","si","dc","rsa","dm","rb","dt"};
		double[] exchanges;
		
		double amount  = 10.5;
		CurrencyUpImplProxy proxy = new CurrencyUpImplProxy();
		exchanges = proxy.getExchanges(amount);
		
		for(int i = 0;i<exchanges.length;i++) {
			System.out.println(amount+ "euros are "+exchanges[i] * amount + " in "+ currencies[i] );
		}
		
		
		
		//Set<Entry<String, Double>> setHm = hmw.getTypeDesc().entrySet();
	    //Iterator<Entry<String, Double>> it1 = setHm.iterator();
	    

	}

}
