package exercise03.entities;


import java.util.Objects;


public class State {
	private int stateID;
	private String stateName;
	private String abbreviation;
	private String capital;
	private int stateHood;
	
	
	public State() {
		
	}
	
	
	public State(int stateID, String stateName, String abbreviation, String capital, int stateHood) {
		this.stateID = stateID;
		this.stateName = stateName;
		this.abbreviation = abbreviation;
		this.capital = capital;
		this.stateHood = stateHood;
	}


	public int getStateID() {
		return stateID;
	}


	public void setStateID(int stateID) {
		this.stateID = stateID;
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
	public int hashCode() {
		return Objects.hash(stateID);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return stateID == other.stateID;
	}


	@Override
	public String toString() {
		return "State [stateID=" + stateID + ", stateName=" + stateName + ", abbreviation=" + abbreviation
				+ ", capital=" + capital + ", stateHood=" + stateHood + "]";
	}
	
}
