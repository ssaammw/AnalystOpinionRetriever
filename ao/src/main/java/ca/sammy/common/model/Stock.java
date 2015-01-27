package ca.sammy.common.model;

import ca.sammy.ao.model.AnalystOpinion;


/**
 * 
 * @author Sammy
 *
 */
public class Stock {

	private String name;
	private String symbol;
	private AnalystOpinion analystOpinion;
	/**
	 * Stock with Symbol
	 * @param symbol
	 */
	public Stock(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public AnalystOpinion getAnalystOpinion() {
		return analystOpinion;
	}
	public void setAnalystOpinion(AnalystOpinion analystOpinion) {
		this.analystOpinion = analystOpinion;
	}
	
}
