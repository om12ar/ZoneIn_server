package com.models;

import java.util.ArrayList;


//observer pattern
public interface NotificationModel {
	
	public void addUserID(Integer notifierID);
	
	public void deattach(Integer notifierID);
	
	public Integer getnumberofNotification(Integer userID);

	
	public void notifyUser();
	

	void addNotificationText(Integer fromID,Integer toID, Integer postID);
	ArrayList<NotificationModel> getNotificationText(Integer UserID);
	
 	

}
