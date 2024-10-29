package exercise01.data_transfer;


import com.mongodb.client.MongoCollection;

import exercise01.utils.MongoDBConnection;
import exercise01.utils.SQLServerConnection;

import org.bson.Document;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataProcessor {
    public static void main(String[] args) {
        Connection sqlConnection = null;
        MongoDBConnection mongoDBConnection = null;

        try {
            sqlConnection = SQLServerConnection.getConnection();
            mongoDBConnection = new MongoDBConnection();

            // Xử lý và chuyển dữ liệu từ SQL Server sang MongoDB Compass
            processBrands(sqlConnection, mongoDBConnection);
            processCategories(sqlConnection, mongoDBConnection);
            processProducts(sqlConnection, mongoDBConnection);
            processCustomers(sqlConnection, mongoDBConnection);
            processStocks(sqlConnection, mongoDBConnection);
            processStaffs(sqlConnection, mongoDBConnection);
            processOrders(sqlConnection, mongoDBConnection);
            processOrderItems(sqlConnection, mongoDBConnection);

            System.out.println("Data has been transferred from SQL Server to MongoDB Compass successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLServerConnection.closeConnection(sqlConnection);
            MongoDBConnection.closeConnection(mongoDBConnection);
        }
    }

    private static void processBrands(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM production.brands";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            String brandName = resultSet.getString("brand_name");

            Document document = new Document("brand_name", brandName);
            insertDocument(mongoDBConnection, "production.brands", document);
        }
    }

    private static void processCategories(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM production.categories";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            String categoryName = resultSet.getString("category_name");

            Document document = new Document("category_name", categoryName);
            insertDocument(mongoDBConnection, "production.categories", document);
        }
    }

    private static void processProducts(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM production.products";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            String productName = resultSet.getString("product_name");
            int brandId = resultSet.getInt("brand_id");
            int categoryId = resultSet.getInt("category_id");
            int modelYear = resultSet.getInt("model_year");
            double listPrice = resultSet.getDouble("list_price");

            Document document = new Document("product_name", productName)
                    .append("brand_id", brandId)
                    .append("category_id", categoryId)
                    .append("model_year", modelYear)
                    .append("list_price", listPrice);
            insertDocument(mongoDBConnection, "production.products", document);
        }
    }

    private static void processCustomers(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM sales.customers";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String phone = resultSet.getString("phone");
            String email = resultSet.getString("email");
            String street = resultSet.getString("street");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String zipCode = resultSet.getString("zip_code");

            Document document = new Document("first_name", firstName)
                    .append("last_name", lastName)
                    .append("phone", phone)
                    .append("email", email)
                    .append("street", street)
                    .append("city", city)
                    .append("state", state)
                    .append("zip_code", zipCode);
            insertDocument(mongoDBConnection, "sales.customers", document);
        }
    }

    private static void processStocks(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM production.stocks";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            int storeId = resultSet.getInt("store_id");
            int productId = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");

            Document document = new Document("store_id", storeId)
                    .append("product_id", productId)
                    .append("quantity", quantity);
            insertDocument(mongoDBConnection, "production.stocks", document);
        }
    }

    private static void processStaffs(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM sales.staffs";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            int staffId = resultSet.getInt("staff_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            int active = resultSet.getInt("active");
            int storeId = resultSet.getInt("store_id");
            int managerId = resultSet.getInt("manager_id");

            Document document = new Document("staff_id", staffId)
                    .append("first_name", firstName)
                    .append("last_name", lastName)
                    .append("email", email)
                    .append("phone", phone)
                    .append("active", active)
                    .append("store_id", storeId)
                    .append("manager_id", managerId);
            insertDocument(mongoDBConnection, "sales.staffs", document);
        }
    }

    private static void processOrders(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM sales.orders";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            int customerId = resultSet.getInt("customer_id");
            String orderStatus = resultSet.getString("order_status");
            String orderDate = resultSet.getString("order_date");
            String requiredDate = resultSet.getString("required_date");
            String shippedDate = resultSet.getString("shipped_date");
            int storeId = resultSet.getInt("store_id");
            int staffId = resultSet.getInt("staff_id");

            Document document = new Document("order_id", orderId)
                    .append("customer_id", customerId)
                    .append("order_status", orderStatus)
                    .append("order_date", orderDate)
                    .append("required_date", requiredDate)
                    .append("shipped_date", shippedDate)
                    .append("store_id", storeId)
                    .append("staff_id", staffId);
            insertDocument(mongoDBConnection, "sales.orders", document);
        }
    }

    private static void processOrderItems(Connection sqlConnection, MongoDBConnection mongoDBConnection) throws SQLException {
        String query = "SELECT * FROM sales.order_items";
        ResultSet resultSet = executeQuery(sqlConnection, query);

        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            int itemId = resultSet.getInt("item_id");
            int productId = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");
            double listPrice = resultSet.getDouble("list_price");
            double discount = resultSet.getDouble("discount");

            Document document = new Document("order_id", orderId)
                    .append("item_id", itemId)
                    .append("product_id", productId)
                    .append("quantity", quantity)
                    .append("list_price", listPrice)
                    .append("discount", discount);
            insertDocument(mongoDBConnection, "sales.order_items", document);
        }
    }


    private static ResultSet executeQuery(Connection sqlConnection, String query) throws SQLException {
        Statement statement = sqlConnection.createStatement();
        return statement.executeQuery(query);
    }

    private static void insertDocument(MongoDBConnection mongoDBConnection, String collectionName, Document document) {
        MongoCollection<Document> collection = mongoDBConnection.getDatabase().getCollection(collectionName);
        collection.insertOne(document);
    }
    
}


