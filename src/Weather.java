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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;



public class Weather {
	
	public static final String URL_SOURCE = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String API_KEY = "&6fd251f92751944ff770f6da75e94f48";
    public static final String UNITS = "&units=metric";
    public static final String LANG = "&lang=ru";

	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Map<String,Double> exchangeMap  = new HashMap<String,Double>();
		
		exchangeMap.put("da", 1.00);
		exchangeMap.put("ls", 0.00);
		exchangeMap.put("fs", 1.00);
		exchangeMap.put("rr", 77.00);
		exchangeMap.put("yj", 119.11);
		exchangeMap.put("bt", 34.00);
		exchangeMap.put("yc", 7.44);
		exchangeMap.put("si", 3.00);
		exchangeMap.put("dc", 1.88);
		exchangeMap.put("rsa", 19.99);
		exchangeMap.put("dm", 10.33);
		exchangeMap.put("rb", 5.00);
		exchangeMap.put("dt", 3.77);
		
		
		XPathFactory xpf = XPathFactory.newInstance();
		
		XPath path = xpf.newXPath();
		
		File site = new File("src/ressoucres/site.txt");
		File site1 = new File("src/ressoucres/ol.txt");
		//FileOutputStream fos = new FileOutputStream(site);
		FileOutputStream fos = new FileOutputStream(site);
		FileOutputStream fos1 = new FileOutputStream(site1);
		FileInputStream fis = new FileInputStream(site);
		FileInputStream fis1 = new FileInputStream(site1);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
	
		
		URL url = new URL("https://www.capital.fr/bourse/devises/cours-devises");
		
		HttpURLConnection  uc = (HttpURLConnection) url.openConnection();

		
		
		uc.connect();
		
		BufferedInputStream bis = new BufferedInputStream(uc.getInputStream());
		
		byte[] buffer = new byte[1024];
		
		int stream = 0;
		
		String part = "";
		
		while((stream = bis.read(buffer)) != -1) {
			//System.out.println("Stream "+ new String(buffer,StandardCharsets.UTF_8));
			part = new String(buffer,StandardCharsets.UTF_8);
			//System.out.println(part);
			fos.write(buffer);
		}
		
		
		String line = "";
		boolean actif = false;
		while((line  = br.readLine()) != null) {
			if(line.contains("<strong")) {
				actif =true;
			}else {
				actif = false;
			}
			
			/*if(line.contains("</strong")) {
				actif =false;
			}*/
			if(actif == true) {
				String line1 = line+"\n";
				fos1.write(line1.getBytes());
			}
		}

		int count  = 0;
		int counter = 0;
		String[] currencies = new String[20];
		Set<Entry<String,Double>> set = exchangeMap.entrySet();
		Iterator<Entry<String,Double>> it = set.iterator();
		while((line = br1.readLine()) != null) {
			if(count > 4) {
				if(count == 8 || count == 13 || count == 12) {
					String mot = line.trim().substring(8,line.trim().indexOf('/'));
					String newMot = mot.substring(0, mot.length()-1);
					currencies[counter] = newMot;
					counter++;
					if(it.hasNext()) {
					Entry<String, Double> e = it.next();
					String newMott = newMot.replace(',', '.');
					e.setValue(Double.valueOf(newMott));
					}
					
				}else {
					String mott = line.trim().substring(8,line.trim().indexOf("&"));
					currencies[counter] = mott;
					counter++;
					if(it.hasNext()) {
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
	         System.out.println(e.getKey() + " : " + e.getValue());
	      }
	      
	      File site2 = new File("src/ressoucres/save.txt");
	      FileOutputStream fos2 = new FileOutputStream(site2);
	      

			Set<Entry<String, Double>> chus = exchangeMap.entrySet();
		    Iterator<Entry<String, Double>> it3 = chus.iterator();
		      while(it3.hasNext()){
		         Entry<String, Double> e = it3.next();
		         String entry = e.getKey() + " : " + e.getValue()+"\n";
		         fos2.write(entry.getBytes());
		      }
		
		
		
		
		/*Document doc = builder.parse(site);
		
		Element root = doc.getDocumentElement();
				
				
		
		NodeList list = (NodeList) path.evaluate("ol", root, XPathConstants.NODESET);
		
		for(int i = 0 ; i < list.getLength(); i++){
			   Node n = list.item(i);
			   System.out.println(n.getNodeName() + " : " + n.getTextContent());
		}*/

       

       
       

	}

}
