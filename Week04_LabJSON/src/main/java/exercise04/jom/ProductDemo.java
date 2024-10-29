package exercise04.jom;


import java.util.List;
import exercise04.entities.Category;
import exercise04.entities.Product;


public class ProductDemo {
	public static void main(String[] args) {
		List<Product> p = JsonHandler.fromJson("json/products.json");
		System.out.println(p);
		System.out.println("\n Tim theo sku: " + JsonHandler.findBySku(43900));
		System.out.println("\n Tim theo price: " + JsonHandler.findByPrice(3.4,10));
		
		// xuất file json
		JsonHandler.toJsonFile(p, "json/products_output.json");
	}
}
