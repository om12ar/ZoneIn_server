package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	
	public UserModel(UserModel other) {
		this.name= other.name;
		this.email=other.email;
		this.pass = other.pass;
		this.id = other.id;
		this.lat = other.lat;
		this.lon = other.lon; 
	}

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}
	
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			if (conn == null)
				System.out.println("null");
			String sql = "Select * from users where email = ? and password = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean follow(Integer followerID, Integer followedID){
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into follows (`follower`, `followed`) VALUES (?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, followerID);
			stmt.setInt(2, followedID);

			stmt.executeUpdate();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
		
	}
	


	public static boolean unfollow (int followerID , int followedID) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "delete from follows where follower = ? and followed = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, followerID);
			stmt.setInt(2, followedID);
			stmt.executeUpdate(); 
			return true; 
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false; 
	}
	
	public static UserModel getUserById(int id) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `id` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}


	public static Double getLatById(int id) {
		Double lat = -1.0;
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lat = rs.getDouble("lat");
				return lat;
			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static Double getLonById(int id) {
		Double lon = -1.0;
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lon = rs.getDouble("long");

				return lon;
			}
			return null;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}


	public static ArrayList<UserModel> getFollowersIDs(Integer id)
	{
		try {
			Connection conn=DBConnection.getActiveConnection();
			String sql="SELECT * FROM `follows` where `followed` = ?" ;
			PreparedStatement stmt;
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> followedBy= new ArrayList<>();
					
			while (rs.next()) {
				
				UserModel temp =  getUserById(rs.getInt(1)) ;
				followedBy.add(temp);
				System.out.println("UserModel.getFollowersIDs()" + temp.toString());
				
			}
			return followedBy;
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static ArrayList<UserModel> getAllUsers()
	{
		try {
			Connection conn=DBConnection.getActiveConnection();
			String sql="SELECT * FROM users" ;
			PreparedStatement stmt;
			stmt=conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> users= new ArrayList<>();
					
			while (rs.next()) {
				UserModel temp =  getUserById(rs.getInt(1)) ;
				users.add(temp);
				System.out.println("UserModel.getUserIDs()" + temp.toString());
				
			}
			return users;
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public static ArrayList<UserModel> getFollowedBy(Integer id)
	{
		try {
			Connection conn=DBConnection.getActiveConnection();
			String sql="select followed from follows where follower = ?" ;
			PreparedStatement stmt;
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> follows= new ArrayList<>();
					
			while (rs.next()) {
				
				UserModel temp =  getUserById(rs.getInt(1)) ;
				follows.add(temp);
				
			}
			return follows;
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
