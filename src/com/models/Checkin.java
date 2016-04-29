package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

import com.mysql.jdbc.Statement;

public class Checkin {

	protected int checkinID;
	protected int placeID;
	protected int userID;
	protected String placeName;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public static boolean checkIn(int placeID, int userID, String review, double rating) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into checkin (`placeID`,`userID`, `review` , `rating`) VALUES  (?,?,?,?)";

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, placeID);
			stmt.setInt(2, userID);
			stmt.setString(3, review);
			stmt.setDouble(4, rating);
			stmt.executeUpdate();

			Place place = Place.getPlaceByID(placeID);
			double newLongitude = place.getLongitude();
			double newLatitude = place.getLatitude();
			UserModel.updateUserPosition(userID, newLatitude, newLongitude);
			boolean flag = false;
			ResultSet rs = stmt.getGeneratedKeys();

			String sql2 = "update `places` set `checkins` = `checkins` + 1 where `id` = ?";
			PreparedStatement stmt2;
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, placeID);
			stmt2.executeUpdate();

			double averageRating = Place.getAverageRating(placeID);
			String sql3 = "update places set rating = ? where  id = ?";
			PreparedStatement stmt3;
			stmt3 = conn.prepareStatement(sql3);
			stmt3.setDouble(1, averageRating);
			stmt3.setInt(2, placeID);
			stmt3.executeUpdate();

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return false;
	}

	public static boolean removeCheckin(int placeID , int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "delete from checkin where placeID = ? and userID = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, placeID);
			stmt.setInt(2, userID);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return false;

	}

	public static ArrayList<Checkin> getCheckinsByPlace(int placeID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select checkin.id , users.name , checkin.review , "
				+ "checkin.rating ,checkin.likes from users inner join checkin " + " on checkin.userID = users.id "
				+ " where checkin.placeID = ? ";
		Place.getPlaceByID(placeID);

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, placeID);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Checkin> checkins = new ArrayList<Checkin>();
			while (rs.next()) {
				Checkin checkin = new Checkin();
				checkin.checkinID = rs.getInt(1);
				checkin.userName = rs.getString(2);
				checkin.review = rs.getString(3);
				checkin.rating = rs.getFloat(4);
				checkin.likes = rs.getInt(5);
				checkin.placeName = Place.getPlaceByID(placeID).getName();
				checkins.add(checkin);
			}
			return checkins;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Checkin> getCheckinsByUser(int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select checkin.id , users.name ,checkin.review , "
				+ "checkin.rating ,checkin.likes, checkin.placeID from users inner join checkin "
				+ " on checkin.userID = users.id " + " where checkin.userID = ? ";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Checkin> checkins = new ArrayList<Checkin>();

			while (rs.next()) {
				Checkin checkin = new Checkin();
				checkin.userID = userID;
				checkin.checkinID = rs.getInt(1);
				checkin.userName = rs.getString(2);
				checkin.review = rs.getString(3);
				checkin.rating = rs.getFloat(4);
				checkin.likes = rs.getInt(5);
				checkin.placeID = rs.getInt(6);
				checkin.placeName = Place.getPlaceByID(checkin.placeID).getName();
				checkins.add(checkin);
			}
			return checkins;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static boolean like(int checkinID, int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "update `checkin` set `likes` = `likes` + 1 where `id` = ?";
		String sql2 = "Insert into `likers` values (?,?)";
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			stmt.executeUpdate();

			PreparedStatement stmt2;
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, checkinID);
			stmt2.setInt(2, userID);
			stmt2.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean unlike(int checkinID, int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "update `checkin` set `likes` = `likes` + 1 where `id` = ?";
		String sql2 = "delete from `likers` where checkinID = ? and userID = ?";
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			stmt.executeUpdate();

			PreparedStatement stmt2;
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, checkinID);
			stmt2.setInt(2, userID);
			stmt2.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean comment(int checkinID, String comment, int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into comment (`checkinID`,`comment`,`userID`) VALUES  (?,?,?)";
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, checkinID);
			stmt.setString(2, comment);
			stmt.setInt(3, userID);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean uncomment(int checkinID , int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "delete from `comment` where checkinID = ? and userID = ? ";
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, checkinID);
			stmt.setInt(2, userID);
			stmt.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static ArrayList<CheckinComment> getComments(int checkinID) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from comment where `checkinID` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			ArrayList<CheckinComment> comments = new ArrayList<CheckinComment>();
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				CheckinComment comment = Place.getCommentByID(rs.getInt(1));
				comments.add(comment);
				return comments;

			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Checkin> getHomePage(int userID) {

		Connection conn = DBConnection.getActiveConnection();
		ArrayList<Checkin> homePage = getCheckinsByUser(userID);

		ArrayList<UserModel> follows = UserModel.getFollowedBy(userID);

		for (int i = 0; i < follows.size(); i++) {
			int followID = follows.get(i).getId();
			homePage.addAll(getCheckinsByUser(followID));

			String sql = "select checkin.id  ,checkin.review , "
					+ "checkin.rating ,checkin.likes, checkin.placeID, checkin.userID"
					+ " from checkin  inner join likers on checkin.id = likers.checkinID" + " where likers.userID = ?";
			PreparedStatement stmt;

			try {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, followID);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Checkin checkin = new Checkin();
					checkin.checkinID = rs.getInt(1);
					checkin.review = rs.getString(2);
					checkin.rating = rs.getFloat(3);
					checkin.likes = rs.getInt(4);
					checkin.placeID = rs.getInt(5);
					checkin.userID = rs.getInt(6);
					checkin.placeName = Place.getPlaceByID(checkin.placeID).getName();
					checkin.userName = UserModel.getUserById(checkin.userID).getName();

					if (!homePage.contains(checkin))
						homePage.add(checkin);
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

			String sql2 = "select checkin.id  ,checkin.review , "
					+ "checkin.rating ,checkin.likes, checkin.placeID, checkin.userID"
					+ " from checkin  inner join comment on checkin.id = comment.checkinID"
					+ " where comment.userID = ?";
			PreparedStatement stmt2;

			try {
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, followID);
				ResultSet rs = stmt2.executeQuery();

				while (rs.next()) {
					Checkin checkin = new Checkin();
					checkin.checkinID = rs.getInt(1);
					checkin.review = rs.getString(2);
					checkin.rating = rs.getFloat(3);
					checkin.likes = rs.getInt(4);
					checkin.placeID = rs.getInt(5);
					checkin.userID = rs.getInt(6);
					System.out.println(checkin.userID);
					checkin.userName = UserModel.getUserById(checkin.userID).getName();
					if (!homePage.contains(checkin))
						homePage.add(checkin);
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return homePage;
	}

	public static ArrayList<UserModel> getLikers(int checkinID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select `userID` from `likers` where checkinID = ?";
		ArrayList<UserModel> likers = new ArrayList<UserModel>();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				likers.add(UserModel.getUserById(rs.getInt(1)));
			}

			return likers;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static int returnCheckinID(int placeID, int userID) {

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select id from checkin where placeID = ? and userID = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, placeID);
			stmt.setInt(2, userID);
			ResultSet rs = stmt.executeQuery();
			int checkinID;
			if (rs.next()){
				checkinID = rs.getInt(1);
				return checkinID;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public static int getCommentID (int checkinID , int userID){

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select id from comment where checkinID = ? and userID = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			stmt.setInt(2, userID);
			ResultSet rs = stmt.executeQuery();
			int commentID;
			if (rs.next()){
				commentID = rs.getInt(1);
				return commentID;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}


		return -1;
	}

	
	public static Checkin getCheckinByID (int checkinID){
		
		Connection conn = DBConnection.getActiveConnection();
        String sql = "select * from checkin where id = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkinID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				Checkin checkin = new Checkin();
				checkin.checkinID = rs.getInt(1);
				checkin.placeID = rs.getInt(2);
				checkin.userID = rs.getInt(3);
				checkin.likes = rs.getInt(4);
				checkin.review = rs.getString(5);
				checkin.rating = rs.getFloat(6);
				checkin.placeName = Place.getPlaceByID(checkin.placeID).getName();
				checkin.userName = UserModel.getUserById(checkin.userID).getName();
				return checkin; 

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

        		
		
		return null; 
	}

	public static ArrayList<Checkin> getUserLikedCheckins (int userID){

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select checkinID from likers where userID = ?";
		PreparedStatement stmt;
		ArrayList<Checkin> checkins = new ArrayList<>();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				checkins.add(Checkin.getCheckinByID(rs.getInt(1)));
			}
			
			return checkins;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}



}


