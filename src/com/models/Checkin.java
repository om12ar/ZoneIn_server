package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Checkin {
	
	protected int checkinID; 
	protected int placeID; 
	protected int userID; 
	protected String userName; 
	protected float rating; 
	protected String review; 
	protected int likes; 
	
	public int getCheckinID() {
		return checkinID;
	}
	public void setCheckinID(int checkinID) {
		this.checkinID = checkinID;
	}
	public int getPlaceID() {
		return placeID;
	}
	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	
	public static ArrayList<Checkin> getCheckinsByPlace (int placeID){

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select checkin.id , users.name , checkin.review , "
				+ "checkin.rating ,checkin.likes from users inner join checkin "
				+ " on checkin.userID = users.id "
				+ " where checkin.placeID = ? ";

		PreparedStatement stmt; 
		try {
			stmt = conn.prepareStatement(sql); 
			stmt.setInt(1, placeID);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Checkin> checkins = new ArrayList<Checkin>(); 
			while (rs.next()){
				Checkin checkin = new Checkin (); 
				checkin.checkinID = rs.getInt(1); 
				checkin.userName = rs.getString(2); 
				checkin.review = rs.getString(3); 
				checkin.rating = rs.getFloat(4); 
				checkin.likes = rs.getInt(5); 
				checkins.add(checkin);
			}
			return checkins;
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null; 
	}
	
	public static ArrayList<Checkin> getCheckinsByUser (int userID){

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select checkin.id , users.name , checkin.review , "
				+ "checkin.rating ,checkin.likes from users inner join checkin "
				+ " on checkin.userID = users.id "
				+ " where checkin.userID = ? ";

		PreparedStatement stmt; 
		try {
			stmt = conn.prepareStatement(sql); 
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Checkin> checkins = new ArrayList<Checkin>(); 
			while (rs.next()){
				Checkin checkin = new Checkin (); 
				checkin.checkinID = rs.getInt(1); 
				checkin.userName = rs.getString(2); 
				checkin.review = rs.getString(3); 
				checkin.rating = rs.getFloat(4); 
				checkin.likes = rs.getInt(5); 
				checkins.add(checkin);
			}
			return checkins;
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null; 
	}


}
