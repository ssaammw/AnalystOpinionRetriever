package ca.sammy.ao.model;

/**
 * 
 * @author Sammy
 *
 */
public class AnalystOpinion {

	private String meanRecommendationThisWeek;
	private String meanRecommendationLastWeek;
	private String change;
	private String noOfBrokers;
	public String getMeanRecommendationThisWeek() {
		return meanRecommendationThisWeek;
	}
	public void setMeanRecommendationThisWeek(String meanRecommendationThisWeek) {
		this.meanRecommendationThisWeek = meanRecommendationThisWeek;
	}
	public String getMeanRecommendationLastWeek() {
		return meanRecommendationLastWeek;
	}
	public void setMeanRecommendationLastWeek(String meanRecommendationLastWeek) {
		this.meanRecommendationLastWeek = meanRecommendationLastWeek;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getNoOfBrokers() {
		return noOfBrokers;
	}
	public void setNoOfBrokers(String noOfBrokers) {
		this.noOfBrokers = noOfBrokers;
	}
}
