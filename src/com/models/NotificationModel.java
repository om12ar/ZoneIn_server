package com.models;


//observer pattern
public interface NotificationModel {
	
	public void addUserID(Integer notifierID);
	
	public void deattach(Integer notifierID);
	
	public Integer getnumberofNotification(Integer userID);

	
	public void notifyUser();
	

	void addNotificationText(Integer fromID,Integer toID);
	void getNotificationText(Integer UserID);
	
 	

}
