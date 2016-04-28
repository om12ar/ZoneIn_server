package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Action{

	private int actionID;
	private int userID;
	private String actionType;
	private String description;
	private int actionParameterID;
	
	public Action(){
		
	}
	
	public Action(int actionID, int userID, String actionType, String description,int actionParameterID){
		
		this.actionID = actionID;
		this.userID = userID;
		this.actionType = actionType;
		this.description = description;
		this.actionParameterID = actionParameterID;
		
	}
	
	public int getActionID() {
		return actionID;
	}
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	
	public int getActionParameterID() {
		return actionParameterID;
	}
	public void setActionParamaterID(int actionParameterID) {
		this.actionParameterID = actionParameterID;
	}
	
	public static ArrayList<Action> getActions(int userID){
		
		ArrayList<Action> actions = new ArrayList<>();
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "SELECT * FROM `actions` where `UserID` = ? ORDER BY `actionID` DESC";
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				
				actions.add(new Action(result.getInt(1), result.getInt(2), result.getString(3), result.getString(4),result.getInt(5)));
			}
			
		}
		catch(SQLException e){
			
			e.printStackTrace();
			e.getMessage();
		}
		
		
		return actions;
		
		
	}
	
	public static boolean addAction(int userID, String actionType, String description,int actionParameterID){
	
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "INSERT INTO `actions`(`UserID`,`ActionType`,`Description`, `ParameterID`) VALUES(?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setInt(1, userID);
			statement.setString(2, actionType);
			statement.setString(3, description);
			statement.setInt(4, actionParameterID);
			statement.executeUpdate();
			
			return true;
		}
		catch(SQLException e){
			
			e.printStackTrace();
			e.getMessage();
		}
		
		return false;
	}
	
	public static boolean removeAction(int actionID){
		
		try{
			Connection connection = DBConnection.getActiveConnection();
			String sql = "DELETE FROM `actions` WHERE `ActionID` = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setInt(1, actionID);
			statement.executeUpdate();
			
			
			return true;
		}
		catch(SQLException e){
			
			e.printStackTrace();
			e.getMessage();
		}
		
		return false;
	}
	

	
	
}
