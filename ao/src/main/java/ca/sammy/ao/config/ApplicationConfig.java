package ca.sammy.ao.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import ca.sammy.common.service.DataPopulationService;
import ca.sammy.common.service.StockDataRetrieverService;

@Configuration
public class ApplicationConfig {

	
	@Bean
	public static PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		
		ClassPathResource[] resources = new ClassPathResource[] { 
				new ClassPathResource("aoretriever.properties"),
				new ClassPathResource("aoretriever-override.properties")};
		
		ppc.setIgnoreUnresolvablePlaceholders(true);
		ppc.setIgnoreResourceNotFound(true);
		ppc.setLocations(resources);
		
		return ppc;
	}
	 
	 
	@Bean
	public DataPopulationService getDataPopulationService()
	{
		return new DataPopulationService();
	}
	
	@Bean
	public StockDataRetrieverService getStockDataRetrieverService()
	{
		return new StockDataRetrieverService();
	}
	
	
	
}