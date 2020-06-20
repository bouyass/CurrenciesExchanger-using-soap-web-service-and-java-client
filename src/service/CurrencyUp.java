package service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jws.WebParam;

public interface CurrencyUp {
	public HashMapWrapper getExchanges(double amount) throws IOException;
	public void updater() throws IOException;
	
}
