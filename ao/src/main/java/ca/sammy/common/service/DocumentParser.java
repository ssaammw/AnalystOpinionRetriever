package ca.sammy.common.service;


import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import ca.sammy.common.model.Constants;
import ca.sammy.exceptions.CannotFindDataException;
import ca.sammy.exceptions.UnableToConnectToWebsiteException;

/**
 * 
 * @author Sammy
 *
 */
public class DocumentParser {

	public static Logger logger = LoggerFactory.getLogger(DocumentParser.class);
	
	private static final String YAHOO_FINANCE_URI = "https://ca.finance.yahoo.com/q/ao?s=";
	
	private String baseURI = YAHOO_FINANCE_URI;
	
	private String symbol = "";
	
	private Document doc = null;

	private int maxRetries = 5;
	
	/**
	 * Default constructor using default yahoo URI connection.
	 * 
	 * @param symbol - the stock code
	 * @throws UnableToConnectToWebsiteException
	 */
	public DocumentParser(String symbol) throws UnableToConnectToWebsiteException
	{
		this.symbol = symbol;
		initialize();
	}
	
	/**
	 * 
	 * @param symbol - the stock code
	 * @param baseURI - the base URI of the site.  For example: "https://ca.finance.yahoo.com/q/ao?s="
	 * @throws UnableToConnectToWebsiteException
	 */
	public DocumentParser(String symbol, String baseURI) throws UnableToConnectToWebsiteException{
		this.symbol = symbol;
		this.baseURI = baseURI;
		initialize();
	}
	
	/**
	 * Connects to the URI and retrieves the Document for this stock symbol.
	 * 
	 * The document is then saved in "this" object such that we do not need to parse the entire web page
	 * again to retrieve any new data. 
	 * 
	 * @throws UnableToConnectToWebsiteException 
	 */
	private void initialize() throws UnableToConnectToWebsiteException {
		
		boolean retryConnection = true;
		int retries = 0;
		
		while(retryConnection)
		{
			try 
			{
				if(doc==null)
				{
					logger.info("Fetching data at " + baseURI+symbol + "...");
					doc = Jsoup.connect(baseURI + symbol).get();
				}
				retryConnection = false;
			} 
			catch (MalformedURLException mue)
			{
				logger.error("The URL is malformed, please check baseURI.", mue);
				throw new UnableToConnectToWebsiteException("Unable retrieve data from " + baseURI + symbol, mue);
			}
			catch (Exception e) 
			{
				retries++;
				logger.warn("Unable to retrieve data (We may be hitting the website too often). retrying... retry #" + retries);
				
				//Sleep a little bit before a retry.
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//If after trying multiple times, still can't get anything then throw an exception
				if(retries >= maxRetries)
				{
					throw new UnableToConnectToWebsiteException("Unable retrieve data from " + baseURI + symbol, e);
				}
			}
		}
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Searches through the document looking for a td element containing the searchColumnName text.
	 * 
	 * Once found, it will attempt to read the td element right be side it to determine the value.
	 * 
	 * This method therefore relies heavily on where the element values are positioned.
	 * 
	 * Changes in the website will require a change to this method.
	 * 
	 * @param searchColumnName
	 * @return
	 * @throws CannotFindDataException
	 */
	public String getCellDataByTitle(String searchColumnName) throws CannotFindDataException {
		
		String value = Constants.UNKNOWN;
		try 
		{
			Elements tdElements = doc.getElementsByTag("td");
			
			for(Element td : tdElements)
			{
				Elements tdsContainingAttr = td.getElementsContainingOwnText(searchColumnName);
				
				if(tdsContainingAttr.size() > 0)
				{
					//using the first hit, go up the parent to the row (tr) node
					Element descriptionTd = tdsContainingAttr.get(0);
					Node trNode = descriptionTd.parentNode(); 
					if(trNode != null && trNode.childNodes().size() > 1)
					{
						//Assuming the cell right next to the description contains our value
						Node targetTdNode = trNode.childNodes().get(1);
						if(targetTdNode != null && targetTdNode.childNode(0) != null)
						{
							value = targetTdNode.childNode(0).toString();
						}
						else
						{
							throw new CannotFindDataException("The " + searchColumnName + " value cannot be found.");
						}
					}
					else
					{
						throw new CannotFindDataException("The " + searchColumnName + " value cannot be found.");
					}
				}
			}

		} 
		catch (Exception e) 
		{
			throw new CannotFindDataException("The webpage may have been updated or the elements you are parsing may have been moved.", e);
		}
		
		return value;
	}
}
