package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.glassfish.jersey.server.ParamException.QueryParamException;

public class Like implements NotificationModel {
	public Integer user = 0;
	public Integer NotfID=0;
	public String notificationText;

//	public comment(String txt) {
//		notificationText = txt;
//	}

	public void addUserID(Integer notifierID) {
		user = notifierID;
	}

	public void deattach(Integer notifierID) {
		user = 0;

	}

	public Integer getnumberofNotification(Integer userID) {
		try {
			Connection connection = DBConnection.getActiveConnection();
			String sql = "Select numberofnotification from users where `id` = ? ";
			PreparedStatement stmt;
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			Integer number = 0;
			if (rs.next())
				number = rs.getInt(1);
			return number;
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void notifyUser() {
		// for all user table count increment by one
		Connection connection = DBConnection.getActiveConnection();

		int number = this.getnumberofNotification(user);
		String sql = "UPDATE `users` SET `numberofnotification` = ? WHERE `id` = ?";
		try {
			PreparedStatement stmt;
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, (number + 1));
			stmt.setInt(2, user);// .get(i));
			System.out.println(stmt.toString());
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// }

	public void addNotificationText(Integer fromID, Integer toID, Integer postID) {
		// add to the table notification user id and notification id and sender
		// id and text
		String sql = "INSERT INTO `notification`(`NotfID`, `toID`, `FromID`,`postID`, `Type`, `seen`, `txt`)"
				+ " VALUES (NULL,?,?,?,?,?,?)";
		Connection conn = DBConnection.getActiveConnection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, toID);
			stmt.setInt(2, fromID);
			stmt.setInt(3, postID);// 1 inducate Like type ...
			stmt.setInt(4, 1);// Zero inducate unseen...
			notificationText=fromID+"Likes your checkin "+toID+"Post";
			stmt.setInt(5, 0);
			stmt.setString(6, notificationText);
			System.out.println(stmt.toString());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		notifyUser();
	}

	@Override
	public ArrayList<NotificationModel> getNotificationText(Integer UserID) {
		//System.out.println("userID: " + UserID);
		// TODO Auto-generated method stub
		// search in database for the user ID
		String sql = "SELECT `NotfID`,  `FromID`,  `text` FROM `notification` "
				+ "WHERE `seen`=0 AND `Type`=1 AND `toID`=?";
		Connection conn = DBConnection.getActiveConnection();
		PreparedStatement stmt;
		ArrayList<NotificationModel> notf=new ArrayList<NotificationModel>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, UserID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
			
				comment temp = new comment("");
				temp.NotfID=rs.getInt(1);
				temp.user=rs.getInt(2);
				temp.notificationText=rs.getString(3);
				notf.add(temp);
				
				updateSeenofNotification(temp.NotfID);

			}
			//System.out.println("OKai");
			return notf;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updateSeenofNotification(int ID)
	{
		String sql = "UPDATE `notification` SET"
				+ "`seen`=1 WHERE `NotfID`= ?";
		Connection conn = DBConnection.getActiveConnection();
		PreparedStatement stmt;
		//ArrayList<NotificationModel> notf=new ArrayList<NotificationModel>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ID);
			
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public String toString() {
		return notificationText;
		
	}

}
