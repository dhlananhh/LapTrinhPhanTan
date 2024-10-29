package de01_ObjModel.entities;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Country {
	private int id;
	private List<String> altSpellings;
	private int area;
	private List<String> borders;
	private List<String> callingCode;
	private String capital;
	private String cca2;
	private String cioc;
	private List<String> currency;
	private String demonym;
	private boolean landLocked;
	private List<Double> latlng;
	private Name name;
	private String region;
	private String subregion;
	private List<Name> translations;
	
	
	public Country() {

	}


	public Country (int id, List<String> altSpellings, int area, List<String> borders, 
			List<String> callingCode, String capital, String cca2, String cioc, 
			List<String> currency, String demonym, boolean landLocked,
			List<Double> latlng, Name name, 
			String region, String subregion, List<Name> translations) {
		this.id = id;
		this.altSpellings = altSpellings;
		this.area = area;
		this.borders = borders;
		this.callingCode = callingCode;
		this.capital = capital;
		this.cca2 = cca2;
		this.cioc = cioc;
		this.currency = currency;
		this.demonym = demonym;
		this.landLocked = landLocked;
		this.latlng = latlng;
		this.name = name;
		this.region = region;
		this.subregion = subregion;
		this.translations = translations;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public List<String> getAltSpellings() {
		return altSpellings;
	}


	public void setAltSpellings(List<String> altSpellings) {
		this.altSpellings = altSpellings;
	}


	public int getArea() {
		return area;
	}


	public void setArea(int area) {
		this.area = area;
	}


	public List<String> getBorders() {
		return borders;
	}


	public void setBorders(List<String> borders) {
		this.borders = borders;
	}


	public List<String> getCallingCode() {
		return callingCode;
	}


	public void setCallingCode(List<String> callingCode) {
		this.callingCode = callingCode;
	}


	public String getCapital() {
		return capital;
	}


	public void setCapital(String capital) {
		this.capital = capital;
	}


	public String getCca2() {
		return cca2;
	}


	public void setCca2(String cca2) {
		this.cca2 = cca2;
	}


	public String getCioc() {
		return cioc;
	}


	public void setCioc(String cioc) {
		this.cioc = cioc;
	}


	public List<String> getCurrency() {
		return currency;
	}


	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}


	public String getDemonym() {
		return demonym;
	}


	public void setDemonym(String demonym) {
		this.demonym = demonym;
	}


	public boolean checkLandLocked() {
		return landLocked;
	}


	public void setLandLocked(boolean landLocked) {
		this.landLocked = landLocked;
	}


	public List<Double> getLatLng() {
		return latlng;
	}


	public void setLatLng(List<Double> latlng) {
		this.latlng = latlng;
	}


	public Name getName() {
		return name;
	}


	public void setName(Name name) {
		this.name = name;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getSubRegion() {
		return subregion;
	}


	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}


	public List<Name> getTranslations() {
		return translations;
	}


	public void setTranslations(List<Name> translations) {
		this.translations = translations;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "Country [id=" + id + ", altSpellings=" + altSpellings + ", area=" + area 
				+ ", borders=" + borders + ", callingCode=" + callingCode 
				+ ", capital=" + capital + ", cca2=" + cca2 + ", cioc=" + cioc
				+ ", currency=" + currency + ", demonym=" + demonym 
				+ ", landLocked=" + landLocked + ", latlng=" + latlng 
				+ ", name=" + name + ", region=" + region 
				+ ", subregion=" + subregion + ", translations=" + translations + "]";
	}

}
