package service;

import java.io.IOException;
import java.util.ArrayList;

public interface CurrencyUp {
	public ArrayList<Double> getExchanges(double amount) throws IOException;
	public void updater() throws IOException;
	
}
