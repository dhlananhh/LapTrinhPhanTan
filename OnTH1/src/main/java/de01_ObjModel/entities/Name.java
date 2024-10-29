package de01_ObjModel.entities;

public class Name {
	private String common;
	private String official;
	
	
	public Name() {
		
	}


	public Name (String common, String official) {
		this.common = common;
		this.official = official;
	}


	public String getCommon() {
		return common;
	}


	public void setCommon(String common) {
		this.common = common;
	}


	public String getOfficial() {
		return official;
	}


	public void setOfficial(String official) {
		this.official = official;
	}


	@Override
	public String toString() {
		return "Name [common=" + common + ", official=" + official + "]";
	}
}
