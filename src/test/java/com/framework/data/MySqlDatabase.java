package com.framework.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import com.framework.components.Settings;

public class MySqlDatabase {

	public static HashMap<String, String> resultData = new HashMap<String, String>();
	protected Properties properties = Settings.getInstance();
	/**
	 * Function to get data from a MS SQL database
	 * 
	 * @param tableName The table name
	 * @param Iteration Iteration
	 */
	public HashMap<String, String> SQL(String sqlQueryProperties) {
		
		String url = properties.getProperty("mySqlServer");
		String username = properties.getProperty("mySqlUsername");
		String password = properties.getProperty("mySqlPassword");
		String sqlQuery = getSqlqueries(sqlQueryProperties);
		try  {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("******Database connected!*******");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			if (resultSet != null) {
				ResultSetMetaData resultmetaData = resultSet.getMetaData();
				int count = resultmetaData.getColumnCount();
				int rowCount = 1;
				while (resultSet.next()) {
					for (int i = 1; i <= count; i++) {
						String column = String.valueOf(resultmetaData.getColumnName(i));
						String value = String.valueOf(resultSet.getObject(i));
						resultData.put(column + rowCount, value);
					}
					rowCount++;
				}
			}
			System.out.println(resultData.toString());
		} catch (SQLException | ClassNotFoundException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		return resultData;
	}
	
	  /**
     * Method to get SQL query from property file
     *
     * @param key
     * @return
     */
    public String getSqlqueries(String key) {
        File file = new File("src/test/resources/properties/SqlQueries.properties");
        Properties property = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            property.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property.getProperty(key);
    }
}
