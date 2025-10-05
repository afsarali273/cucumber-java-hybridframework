package com.framework.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.hsqldb.types.Types;
import com.framework.report.Util;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import java.sql.DriverManager;

public class AccessDatabase {

	public HashMap<String, HashMap<String, String>> getvalue = new HashMap<String, HashMap<String, String>>();

	/**
	 * Function to connect access database using driver manager
	 * 
	 * @return Connection object
	 * @throws SQLException
	 * 
	 */
	public Connection accessConnect(String filename) throws SQLException {

		return DriverManager.getConnection("jdbc:ucanaccess://" + filename);	

	}

	/**
	 * Function to execute query and return the values in a resultset
	 * 
	 * @param connectionObj drivermanager connection object
	 * @param executeQuery Query to execute
	 * @return Resultset of a database
	 * @throws SQLException
	 */
	public ResultSet executeQuery(Connection connectionObj, String executeQuery) throws SQLException {

		PreparedStatement pst = connectionObj.prepareStatement(executeQuery);
		ResultSet resultSet = pst.executeQuery();
		return resultSet;

	}

	/**
	 * Function to execute query and return in Hashmap
	 * @return 
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	public HashMap<String, HashMap<String, String>> executeAccessData(String filepath, String filename, String testID) throws ClassNotFoundException, InterruptedException, SQLException {

		HashMap<String, String> getData;
		List<String> tblName = new ArrayList<String>();
		int i = 0;
		while (i < 3) {
			tblName = getTableName(filepath, filename);
			if(tblName.size()>0)
				break;
			i++;
		}	

		for(String table : tblName) {
			String query = "SELECT * from " +table+ " where TC_ID='"+testID+"'";
			getData = readAccess(accessConnect(filepath + Util.getFileSeparator() + filename + ".accdb"), query, table);
			getvalue.put(table, getData);
		}

		return getvalue;
	}

	/**
	 * Function to get all table names in a database
	 * 
	 * @return all the table names
	 * @throws SQLException
	 */
	public List<String> getTableName(String filepath, String filename) throws SQLException {

		List<String> tbl = new ArrayList<String>();
		try {
			DatabaseMetaData dbmd = accessConnect(filepath + Util.getFileSeparator() + filename + ".accdb").getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				tbl.add(rs.getString("TABLE_NAME"));
			}
		} 
		catch (SQLException e) {	
			//do nothing..
		}

		return tbl;

	}

	/**
	 * Function to read all values from a table and assign column as key and row as values in a hashmap
	 * 
	 * @param connectionObj
	 * @param executeQuery
	 * @param tablename
	 * @return All values in hashmap
	 * @throws SQLException
	 */
	public HashMap<String, String> readAccess(Connection connectionObj, String executeQuery, String tablename) throws SQLException {

		//		HashMap<String, HashMap<String, String>> tcObj = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> dataObj = new HashMap<String, String>();
		ResultSet reslSet = executeQuery(connectionObj, executeQuery);
		if(reslSet!=null) {
			ResultSetMetaData rsm = reslSet.getMetaData();
			int numberOfValues = rsm.getColumnCount();
			while (reslSet.next()) {

				for(int i=2;i<=numberOfValues;i++) {
					if(reslSet.getObject(i)!=null) {
						dataObj.put(rsm.getColumnName(i), (String) reslSet.getObject(i));
					}
				}
				//				tcObj.put((String) reslSet.getObject(1), dataObj);
			}
		}

		return dataObj;

	}

	/**
	 * Create Access database in the result path
	 * 
	 * @param databaseName
	 * 				The database file path and name
	 * @return The database connection
	 * 
	 * @throws IOException
	 */
	private Database createDatabase(String databaseName) throws IOException {
		
		return DatabaseBuilder.create(FileFormat.V2010, new File(databaseName));
		
	}

	/**
	 * Method to create a table in access database
	 * 
	 * @param tableName
	 * 				The table name
	 * 
	 * @return The table builder connection
	 */
	private TableBuilder createTable(String tableName) {
		
		return new TableBuilder(tableName);
		
	}

	/**
	 * To add column to the access database table
	 * @param database
	 * 				The database name
	 * @param tableName
	 * 				Table name
	 * @param columnName
	 * 				Column name to add
	 * @param sqlType
	 * 				Type of the cell
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public void addColumn(Database database, TableBuilder tableName, String columnName, Types sqlType) throws SQLException, IOException {
		
		tableName.addColumn(new ColumnBuilder(columnName)
				.setSQLType(Types.INTEGER).toColumn()).toTable(database);
		
	}

	/**
	 * Method to create access database in the result folder
	 * 
	 * @param reportPath
	 * 				The result path
	 * @param currentTest
	 * 				The current test case name
	 * @param columnName
	 * 				The column name to insert the record
	 * @param value
	 * 				The value to insert
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	public void createAccessDB(String reportPath, String currentTest, String columnName, String value) throws IOException, SQLException {

		String databaseName = reportPath + Util.getFileSeparator() + "Output.accdb";

		if (!new File(databaseName).exists()) {
			
			startDatabaseProcess(databaseName, columnName);
			
		}
		
		insertRecord(databaseName, currentTest, columnName, value);
		
	}
	
	/**
	 * Method to Insert the record
	 * 
	 * @param databaseName
	 * 				The database name
	 * @param currentTest
	 * 				The current test case name
	 * @param columnName
	 * 				The column name to insert values
	 * @param value
	 * 			The value to insert in the column
	 * 
	 * @throws SQLException
	 */
	public void insertRecord(String databaseName, String currentTest, String columnName, String value) throws SQLException {
		
		checkColumnPresent(databaseName, currentTest, columnName);
		
		
		Connection connect = accessConnect(databaseName);
		
		String updateQuery = "SELECT * FROM ResultData WHERE TC_ID = '"+currentTest+"'";
		PreparedStatement pst = connect.prepareStatement(updateQuery);
		ResultSet resultSet = pst.executeQuery();
		int row = 0;
		while (resultSet.next()) {
			row = row + 1;
		}
		
		String insertQuery = "";
		
		if(row>0) 
			insertQuery= "UPDATE ResultData SET "+columnName+" = '"+value+"' where TC_ID = '"+currentTest+"'";
		else
			insertQuery = "INSERT INTO ResultData (TC_ID , "+columnName+") VALUES ('" + currentTest + "' , '" + value + "')" ;
		
		Statement statement = connect.createStatement();
		statement.execute(insertQuery);
		
		statement.close();
		
		connect.close();
		
	}
	
	/**
	 * Method to check whether the column is present or not. And create a new column if not present
	 * 
	 * @param databaseName
	 * 				The data base to connect
	 * @param currentTest
	 * 				Current test case name
	 * @param columnName
	 * 				Column name to create
	 * @throws SQLException
	 * 				
	 */
	public void checkColumnPresent(String databaseName, String currentTest, String columnName) throws SQLException {
		
		String query = "Select "+columnName+" from ResultData where TC_ID = '"+currentTest+"'";
		
		boolean isColumnPresent;
		
		Connection connect = accessConnect(databaseName);
		
		Statement statement = connect.createStatement();
		try {

			isColumnPresent = statement.execute(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isColumnPresent = false;
		}
		
		if(!isColumnPresent) {
			query = "ALTER TABLE ResultData ADD COLUMN "+columnName+" TEXT(255)";
			statement.execute(query);
		}
		
		statement.close();
		connect.close();
		
	}
	
	/**
	 * Function to create the database and table in accessDB
	 * 
	 * @param databaseName
	 * 				The database name
	 * @param columnName
	 * 				The column name
	 * @throws IOException
	 * 
	 */
	public void startDatabaseProcess(String databaseName, String columnName) throws IOException, SQLException {
		Database database = createDatabase(databaseName);

		String tableName = "ResultData"; 
		@SuppressWarnings("unused")
		Table table = null;
		table = createTable(tableName)
				.addColumn(new ColumnBuilder("TC_ID").setSQLType(Types.VARCHAR).toColumn())
				.addColumn(new ColumnBuilder(columnName).setSQLType(Types.VARCHAR).toColumn())
				.toTable(database);
	}
	
	


}
