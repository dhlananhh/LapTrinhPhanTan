package exercise05.entities;

import java.time.LocalDate;

public class Tomatoes {
	private Viewer viewer;
	private LocalDate lastUpdated;
	
	
	public Tomatoes() {

	}
	
	
	public Tomatoes(Viewer viewer, LocalDate lastUpdated) {
		this.viewer = viewer;
		this.lastUpdated = lastUpdated;
	}


	public Viewer getViewer() {
		return viewer;
	}


	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}


	public LocalDate getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	@Override
	public String toString() {
		return "Tomatoes [viewer=" + viewer + ", lastUpdated=" + lastUpdated + "]";
	}
	
}
