package streaming_readFile.entities;

public class State {
	private long id;
	private String stateName;
	private String abbreviation;
	private String capital;
	private int stateHood;
	
	
	public State() {
		
	}
	
	
	public State(long id, String stateName, String abbreviation, String capital, int stateHood) {
		this.id = id;
		this.stateName = stateName;
		this.abbreviation = abbreviation;
		this.capital = capital;
		this.stateHood = stateHood;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public String getAbbreviation() {
		return abbreviation;
	}


	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	public String getCapital() {
		return capital;
	}


	public void setCapital(String capital) {
		this.capital = capital;
	}


	public int getStateHood() {
		return stateHood;
	}


	public void setStateHood(int stateHood) {
		this.stateHood = stateHood;
	}


	@Override
	public String toString() {
		return "State [id=" + id + ", stateName=" + stateName + ", abbreviation=" + abbreviation + ", capital="
				+ capital + ", stateHood=" + stateHood + "]";
	}


	
}
