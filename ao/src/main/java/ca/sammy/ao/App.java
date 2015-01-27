package ca.sammy.ao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ca.sammy.ao.config.ApplicationConfig;
import ca.sammy.common.service.StockDataRetrieverService;

/**
 * @author Sammy
 *
 */
public class App 
{
	public static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
    		logger.debug("Starting up...");
    		
    		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    		StockDataRetrieverService sdrs = ctx.getBean(StockDataRetrieverService.class);
			sdrs.retrieveStockData();
			
			logger.debug("Shutting down...");
		} 
    	catch (IOException ioe)
    	{
    		logger.warn("\n\n\nInput file not found.  \n"
    				+ "Please check that input.csv file is located in the same directory as this executable.\n");
    		
    		
    		waitForCarriageReturn();
    	}
    	catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		
    		System.out.println("\n\n\nThere was a problem with the application.  Please see the log or contact your administrator.\n");
    		
    		waitForCarriageReturn();
		}
    }

	private static void waitForCarriageReturn() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
		{
			System.out.println("Please Press Enter to exit...");
			br.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
    
}
