package service;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "currencyUpdater")
public class CurrencyUpImpl implements CurrencyUp {

	private Date dateUpdate;
	private List<Double> exchangeMap;
	File site2;

	public CurrencyUpImpl() {
		this.dateUpdate = new Date();
		this.exchangeMap = new ArrayList<Double>();
		this.exchangeMap.add(1.1177);
		this.exchangeMap.add(0.9051);
		this.exchangeMap.add(1.0644);
		this.exchangeMap.add(77.5981);
		this.exchangeMap.add(119.4747);
		this.exchangeMap.add(34.6650);
		this.exchangeMap.add(7.9050);
		this.exchangeMap.add(3.8555);
		this.exchangeMap.add(1.5211);
		this.exchangeMap.add(19.5322);
		this.exchangeMap.add(10.8106);
		this.exchangeMap.add(5.9380);
		this.exchangeMap.add(3.1973);
		this.site2 = new File("src/ressoucres/save.txt");
	}

	@WebMethod
	public ArrayList<Double> getExchanges(@WebParam(name="montant") double amount) throws IOException{
		// this line to update the values every day from the site 
		if (new Date() != this.dateUpdate) {
			this.updater();
		}
		
		ArrayList<Double> result = (ArrayList<Double>) this.exchangeMap;
		
		return result;
	}

	public void updater() throws IOException {

		// set the date of the update
		this.dateUpdate = new Date();
		Iterator<Double> it = this.exchangeMap.iterator();

		File site = new File("src/ressoucres/site.txt");
		File site1 = new File("src/ressoucres/ol.txt");
		
		FileOutputStream fos = new FileOutputStream(site);
		FileOutputStream fos1 = new FileOutputStream(site1);
		FileOutputStream fos2 = new FileOutputStream(site2);
		
		FileInputStream fis = new FileInputStream(site);
		FileInputStream fis1 = new FileInputStream(site1);

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));


		URL url = new URL("https://www.capital.fr/bourse/devises/cours-devises");
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();

		uc.connect();

		BufferedInputStream bis = new BufferedInputStream(uc.getInputStream());

		byte[] buffer = new byte[1024];

		int stream = 0;

		String part = "";

		while ((stream = bis.read(buffer)) != -1) {
			part = new String(buffer, StandardCharsets.UTF_8);
			fos.write(buffer);
		}

		String line = "";
		boolean actif = false;
		while ((line = br.readLine()) != null) {
			if (line.contains("<strong")) {
				actif = true;
			} else {
				actif = false;
			}

			if (actif == true) {
				String line1 = line + "\n";
				fos1.write(line1.getBytes());
			}
		}

		int count = 0;
		this.exchangeMap.clear();
		while ((line = br1.readLine()) != null) {
			if (count > 4) {
				if (count == 8 || count == 11 || count == 12) {
					String mot = line.trim().substring(8, line.trim().indexOf('/'));
					String newMot = mot.substring(0, mot.length() - 1);
					String newMott = newMot.replace(',','.');
					if(newMott.length() <= 6) {
						this.exchangeMap.add(Double.valueOf(newMott));
					}
					

				} else {
					
					String mott = line.trim().substring(8, line.trim().indexOf("&"));
					String mottt = mott.replace(',','.');
					System.out.println(count);
					if(mottt.length() <= 6) {
						this.exchangeMap.add(Double.valueOf(mottt));
					}
					
				}
			}

			count++;
		}
		

	    Iterator<Double> it2= this.exchangeMap.iterator();
	      while(it2.hasNext()){
	    	 String ligne = String.valueOf(it2.next());
	    	 ligne+="\n";
	         fos2.write(ligne.getBytes());
	      }

	}

}
