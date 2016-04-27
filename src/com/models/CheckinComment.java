package com.models;

public class CheckinComment {
	
	int ID; 
	String comment;
	int checkinID;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCheckinID() {
		return checkinID;
	}
	public void setCheckinID(int checkinID) {
		this.checkinID = checkinID;
	} 
	

}
