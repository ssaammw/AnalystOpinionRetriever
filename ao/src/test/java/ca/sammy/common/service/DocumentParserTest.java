package ca.sammy.common.service;

import org.junit.Assert;
import org.junit.Test;

import ca.sammy.common.model.Constants;
import ca.sammy.common.service.DocumentParser;
import ca.sammy.exceptions.CannotFindDataException;
import ca.sammy.exceptions.UnableToConnectToWebsiteException;

public class DocumentParserTest {

	@Test
	public void test_get_cell_data_by_title_unknown() throws UnableToConnectToWebsiteException, CannotFindDataException
	{
		DocumentParser dp = new DocumentParser("googl");
		String meanRecThisWeek = dp.getCellDataByTitle(Constants.MEAN_RECOMMENDATION_THIS_WEEK);
		
		Assert.assertTrue(meanRecThisWeek != Constants.UNKNOWN);
	}
	
	@Test(expected = UnableToConnectToWebsiteException.class)
	public void test_document_parser_website_fails() throws UnableToConnectToWebsiteException
	{
		DocumentParser dp = new DocumentParser("googl", "http://notworkingwebsite.com?stock=");
	}
}
