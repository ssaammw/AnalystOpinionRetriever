package ca.sammy.common.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ca.sammy.common.model.Constants;
import ca.sammy.common.model.Stock;
import ca.sammy.common.util.CsvFileUtil;

/**
 * 
 * @author Sammy
 *
 */
public class StockDataRetrieverService {

	public static Logger logger = LoggerFactory.getLogger(StockDataRetrieverService.class);
	
	@Autowired
	DataPopulationService dps;
	
	@Value( "${ao.sdrs.inputfilename}")
	private String inputFilename;
	
	/**
	 * Default Constructor
	 */
	public StockDataRetrieverService() {}
	
	public void retrieveStockData() throws IOException
	{
    	//get the symbols from a file.
    	 List<String> symbols = CsvFileUtil.readStockCSVFile(inputFilename);
    	
    	List<Stock> updatedStocks = dps.populateWithLatestData(symbols);

    	Date timestamp = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat(Constants.FILE_DATE_FORMAT_PATTERN);
    	
    	String filename = sdf.format(timestamp) + "_stock_output" + ".csv";
    	CsvFileUtil.writeCSVFile(updatedStocks, filename);
    	
    	String message = "\n\n"
    			+ "Please note that the resulting csv file may contain UNKNOWN values.\n"
    			+ "This may be because the webpage does not actually contain the information we are searching for.";
    	
    	logger.info(message);
	}
	
}
