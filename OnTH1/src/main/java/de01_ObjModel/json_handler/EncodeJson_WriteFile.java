package de01_ObjModel.json_handler;


import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.util.List;

import de01_ObjModel.entities.Country;
import de01_ObjModel.entities.Name;

import java.util.ArrayList;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;


public class EncodeJson_WriteFile {
	public static void main (String[] args) throws Exception {
		int id = 1;
		
		List<String> lstAltSpellings = new ArrayList<String>();
		lstAltSpellings.add("VN");
		lstAltSpellings.add("Socialist Republic of Vietnam");
		lstAltSpellings.add("Cộng hòa Xã hội chủ nghĩa Việt Nam");
		lstAltSpellings.add("Viet Nam");
		
		int area = 331212;
		
		List<String> lstBorders = new ArrayList<String>();
		lstBorders.add("KHM");
		lstBorders.add("CHN");
		lstBorders.add("LAO");
		
		List<String> lstCallingCode = new ArrayList<String>();
		lstCallingCode.add("84");
		
		String capital = "Hanoi";
		String cca2 = "VN";
		String cioc = "VIE";
		
		List<String> lstCurrency = new ArrayList<String>();
		lstCurrency.add("VND");
		
		String demonym = "Vietnamese";
		boolean landLocked = false;
		
		List<Double> lstLatLng = new ArrayList<Double>();
		lstLatLng.add(16.16666666);
		lstLatLng.add(107.83333333);
		
		String common = "Vietnam";
		String official = "Socialist Republic of Vietnam";
		Name name = new Name(common, official);
		
		String region = "Asia";
		String subregion = "South-Eastern Asia";
		
		List<Name> lstTranslations = new ArrayList<Name>();
		Name name1 = new Name("Viet Nam", "République socialiste du Vietnam");
		Name name2 = new Name("Vietnam", "Repubblica socialista del Vietnam");
		lstTranslations.add(name1);
		lstTranslations.add(name2);
	
		Country country = new Country(
			id, lstAltSpellings, area, lstBorders, 
			lstCallingCode, capital, cca2, cioc, 
			lstCurrency, demonym, landLocked, lstLatLng, 
			name, region, subregion, lstTranslations
		);
		
		List<Country> lstCountries = new ArrayList<Country>();
		lstCountries.add(country);
		
		// Ghi Json String ra màn hình
		EncodeJson_WriteFile encodeJsonFile = new EncodeJson_WriteFile();
		String json = encodeJsonFile.generateToJsonFile(lstCountries);
		System.out.println("List Country: ");
		System.out.println(json);
		
		// Xuất file JSON
		encodeJsonFile.export("json/countries_output.json", json);
	}
	
	
	public static String generateToJsonFile (List<Country> lstCountries) {
//		JsonObjectBuilder jsonObject = Json.createObjectBuilder();
		
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		for (Country country : lstCountries) {
			// country obj
			JsonObjectBuilder countryBuilder = Json.createObjectBuilder();
			countryBuilder.add("id", country.getId());
			
			// altSpellings field
			JsonArrayBuilder altSpellingsBuilder = Json.createArrayBuilder();
			for (String altSpelling : country.getAltSpellings()) {
				altSpellingsBuilder.add(altSpelling);
			}
			countryBuilder.add("altSpellings", altSpellingsBuilder);
			
			// area field
			countryBuilder.add("area", country.getArea());
			
			// borders field
			JsonArrayBuilder bordersBuilder = Json.createArrayBuilder();
			for (String border : country.getBorders()) {
				bordersBuilder.add(border);
			}
			countryBuilder.add("borders", bordersBuilder);
			
			// callingCode field
			JsonArrayBuilder callingCodeBuilder = Json.createArrayBuilder();
			for (String callingCode : country.getCallingCode()) {
				callingCodeBuilder.add(callingCode);
			}
			countryBuilder.add("callingCode", callingCodeBuilder);
			
			// capital field
			countryBuilder.add("capital", country.getCapital());
			
			// cca2 field
			countryBuilder.add("cca2", country.getCca2());
			
			// cioc field
			countryBuilder.add("cioc", country.getCioc());
			
			// currency field
			JsonArrayBuilder currencyBuilder = Json.createArrayBuilder();
			for (String currency : country.getCurrency()) {
				currencyBuilder.add(currency);
			}
			countryBuilder.add("currency", currencyBuilder);
			
			// demonym field
			countryBuilder.add("demonym", country.getDemonym());
			
			// landLocked field
			countryBuilder.add("landLocked", country.checkLandLocked());
			
			// latlng field
			JsonArrayBuilder latlngBuilder = Json.createArrayBuilder();
			for (Double latlng : country.getLatLng()) {
				latlngBuilder.add(latlng);
			}
			countryBuilder.add("latlng", latlngBuilder);
			
			// name field
			JsonObjectBuilder nameBuilder = Json.createObjectBuilder();
			nameBuilder.add("common", country.getName().getCommon());
			nameBuilder.add("official", country.getName().getOfficial());
			countryBuilder.add("name", nameBuilder);
			
			// region field
			countryBuilder.add("region", country.getRegion());
			
			// region field
			countryBuilder.add("subregion", country.getSubRegion());
			
			// translations field
			List<JsonObject> lstTranslations = new ArrayList<JsonObject>();
			
			for (Name name : country.getTranslations()) {
				lstTranslations.add(Json.createObjectBuilder()
						.add("common", name.getCommon())
						.add("official", name.getOfficial())
						.build());
			}
			JsonArrayBuilder translationsBuilder = Json.createArrayBuilder(lstTranslations);
			countryBuilder.add("translations", translationsBuilder);
			
			// thêm countryBuilder vào mảng jsonArrayBuilder
			jsonArrayBuilder.add(countryBuilder);
		}
		
		
		// Chuyển đổi mảng đối tượng sang JSON
		JsonArray arrCountries = jsonArrayBuilder.build();
		String json = arrCountries.toString();
		
		return json;
	}
	
	
	private void export (String filePath, String json) throws Exception {
		PrintWriter out = new PrintWriter(new FileOutputStream((filePath), true));
		out.println(json);
		out.close();
	}
}
