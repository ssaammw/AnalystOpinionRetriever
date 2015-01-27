package ca.sammy.common.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.sammy.ao.config.ApplicationConfig;
import ca.sammy.common.model.Stock;
import ca.sammy.exceptions.CannotFindDataException;
import ca.sammy.exceptions.UnableToConnectToWebsiteException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
public class DataPopulationServiceTest {

	@Autowired
	private DataPopulationService dataPopulationService;
	
	@Test
	public void test_populate_Stocks_with_data() throws UnableToConnectToWebsiteException, CannotFindDataException
	{
		String testStockSymbol = "googl";
		
		ArrayList<String> symbols = new ArrayList<String>();
		symbols.add(testStockSymbol);
		List<Stock> stocks = dataPopulationService.populateWithLatestData(symbols);
		
		Assert.assertEquals(1, stocks.size());
		Assert.assertEquals(testStockSymbol, stocks.get(0).getSymbol());
		Assert.assertNotNull(stocks.get(0).getAnalystOpinion().getMeanRecommendationThisWeek());
	}
	
}
