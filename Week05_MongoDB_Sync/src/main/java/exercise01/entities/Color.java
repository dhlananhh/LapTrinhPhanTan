package exercise01.entities;


public enum Color {
	RED ("red"), 
	BLUE ("blue"), 
	GREEN ("green"), 
	BLACK ("black"), 
	WHITE ("white"),
	PINK ("pink"),
	YELLOW ("yellow");
	
	
	private String colorName;
	
	
	private Color (String colorName) {
		this.colorName = colorName;
	}


	public String getColorName() {
		return colorName;
	}


	@Override
	public String toString() {
		return "Color [colorName=" + colorName + "]";
	}
}
