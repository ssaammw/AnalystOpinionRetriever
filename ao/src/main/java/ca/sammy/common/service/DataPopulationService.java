package ca.sammy.common.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import ca.sammy.ao.model.AnalystOpinion;
import ca.sammy.common.model.Constants;
import ca.sammy.common.model.Stock;
import ca.sammy.exceptions.CannotFindDataException;
import ca.sammy.exceptions.UnableToConnectToWebsiteException;

/**
 * 
 * @author Sammy
 *
 */
public class DataPopulationService {

	public static Logger logger = LoggerFactory.getLogger(DataPopulationService.class);

	/**
	 * Populates the Stock with the latest data.
	 * @param symbols
	 * @return
	 */
	public List<Stock> populateWithLatestData(List<String> symbols)
	{
		List<Stock> stocks = new ArrayList<Stock>();
		
		for(String symbol : symbols)
		{
			Stock stock = new Stock(symbol);
			
			stock.setAnalystOpinion(getAnalystOpinion(symbol));
			
			stocks.add(stock);
		}
		
		return stocks;
	}

	/**
	 * Retrieves the data using the Document Parser.
	 * @param symbol
	 * @return
	 */
	private AnalystOpinion getAnalystOpinion(String symbol) 
	{
		DocumentParser symbolSpecificDataParser;
		AnalystOpinion ao = new AnalystOpinion();
		try {
			symbolSpecificDataParser = new DocumentParser(symbol);
	
			String meanRecommendationThisWeek = symbolSpecificDataParser.getCellDataByTitle(Constants.MEAN_RECOMMENDATION_THIS_WEEK);
			String noOfBrokers = symbolSpecificDataParser.getCellDataByTitle(Constants.NO_OF_BROKERS);
			ao.setMeanRecommendationThisWeek(meanRecommendationThisWeek);
			ao.setNoOfBrokers(noOfBrokers);
		}
		catch (UnableToConnectToWebsiteException e) {
			
			logger.error(e.getMessage(), e);
		} catch (CannotFindDataException e) {
			logger.error(e.getMessage(), e);
		}
		return ao;
	}
}
