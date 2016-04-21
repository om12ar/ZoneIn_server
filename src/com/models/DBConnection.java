package com.models;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection = null;

	public static Connection getActiveConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//MAKE SURE USER, PASSWORD and DB NAME are CORRECT

			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZoneIn_DB?"
					+ "user=root&password=&characterEncoding=utf8");

//			connection = DriverManager
//					.getConnection("jdbc:mysql://127.6.228.2:3306/zonein?"
//							+ "user=admin2sLmsaB&password=f-ThUdYmsN34&characterEncoding=utf8");

			
							

			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
