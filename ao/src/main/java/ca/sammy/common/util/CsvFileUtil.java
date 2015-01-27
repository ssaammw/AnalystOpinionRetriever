package ca.sammy.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.sammy.common.model.Constants;
import ca.sammy.common.model.Stock;

/**
 * Provides a utility to perform io operations on CSV files
 * @author Sammy
 *
 */
public class CsvFileUtil {
	
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object [] FILE_HEADER = {"Symbol","Mean Recommendation (This Week)", "No. of Brokers"};

	public static Logger logger = LoggerFactory.getLogger(CsvFileUtil.class);
	
	/**
	 * Writes a list of Stocks to a csv output file.
	 * @param stocks
	 * @param filename
	 */
	public static void writeCSVFile(List<Stock> stocks, String filename)
    {
    	File outputDir = new File("output");
    	if(!outputDir.exists())
    	{
    		outputDir.mkdirs();
    	}
    		
    	
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    	try (FileWriter fileWriter = new FileWriter(outputDir + File.separator + filename);
    			CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);)
    	{
    		//Create the CSV File Header
    		csvFilePrinter.printRecord(FILE_HEADER);

    		for(Stock stock : stocks)
    		{
    			List<String> stockRecord = new ArrayList<String>();
    			stockRecord.add(stock.getSymbol());
    			stockRecord.add(stock.getAnalystOpinion().getMeanRecommendationThisWeek());
    			stockRecord.add(stock.getAnalystOpinion().getNoOfBrokers());
    			csvFilePrinter.printRecord(stockRecord);
    			
    			String message = "Writing record for: " + stock.getSymbol();
    			logger.debug(message);
    		}
    		
    		logger.info("CSV file, " + filename + ", created successfully!");
    		
    	} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
    }
	
	/**
	 * Reads the input csv file.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List<String> readStockCSVFile(String filename) throws IOException
	{
		String csvFile = filename;
		
		String line = "";
		String cvsSplitBy = Constants.CSV_SPLIT_COMMA_DELIMITED;
	 
		List<String> resultStockCodes = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader(csvFile));) 
		{
			while ((line = br.readLine()) != null) 
			{
				String[] stockCode = line.split(cvsSplitBy);
				resultStockCodes.add(stockCode[0]);
				
				//System.out.println(stockCode[0]);
			}
			
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage(), fnfe);
			throw fnfe;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
			throw ioe;
		}
		return resultStockCodes;
	}
}
