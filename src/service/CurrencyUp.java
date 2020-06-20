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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "currencyUpdater")
public class CurrencyUp {

	private Date dateUpdate;
	private Map<String, Double> exchangeMap;
	File site2;

	public CurrencyUp() {
		this.dateUpdate = new Date();
		this.exchangeMap = new HashMap<String, Double>();
		this.exchangeMap.put("da", 1.1177);
		this.exchangeMap.put("ls", 0.9051);
		this.exchangeMap.put("fs", 1.0644);
		this.exchangeMap.put("rr", 77.5981);
		this.exchangeMap.put("yj", 119.4747);
		this.exchangeMap.put("bt", 34.6650);
		this.exchangeMap.put("yc", 7.9050);
		this.exchangeMap.put("si", 3.8555);
		this.exchangeMap.put("dc", 1.5211);
		this.exchangeMap.put("rsa", 19.5322);
		this.exchangeMap.put("dm", 10.8106);
		this.exchangeMap.put("rb", 5.9380);
		this.exchangeMap.put("dt", 3.1973);
		this.site2 = new File("src/ressoucres/save.txt");
	}

	@WebMethod
	public HashMapWrapper getExchanges(@WebParam(name="montant") double amount) throws IOException {
		Map<String, Double> result = new HashMap<String, Double>();
		FileInputStream fis = new FileInputStream(this.site2);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		// this line to update the values every day from the site 
		if (new Date() != this.dateUpdate) {
			this.updater();
		}
		
		String line="";
		while((line = br.readLine()) != null) {
			String[] values = line.split(":");
			result.put(values[0].trim(), Double.valueOf(values[1])*amount);
			
		}
		return new HashMapWrapper(result);
	}

	public void updater() throws IOException {

		// set the date of the update
		this.dateUpdate = new Date();
		Set<Entry<String, Double>> set = exchangeMap.entrySet();
		Iterator<Entry<String, Double>> it = set.iterator();

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
		while ((line = br1.readLine()) != null) {
			if (count > 4) {
				if (count == 8 || count == 11 || count == 12) {
					String mot = line.trim().substring(8, line.trim().indexOf('/'));
					String newMot = mot.substring(0, mot.length() - 1);
					if (it.hasNext()) {
						Entry<String, Double> e = it.next();
						String newMott = newMot.replace(',', '.');
						e.setValue(Double.valueOf(newMott));
					}

				} else {
					System.out.println(count);
					String mott = line.trim().substring(8, line.trim().indexOf("&"));
					if (it.hasNext()) {
						Entry<String, Double> e = it.next();
						String mottt = mott.replace(',', '.');
						e.setValue(Double.valueOf(mottt));
					}
				}
			}

			count++;
		}
		
		Set<Entry<String, Double>> setHm = exchangeMap.entrySet();
	    Iterator<Entry<String, Double>> it2 = setHm.iterator();
	      while(it2.hasNext()){
	         Entry<String, Double> e = it2.next();
	         String entry = e.getKey() + " : " + e.getValue()+"\n";
	         fos2.write(entry.getBytes());
	      }

	}

}
