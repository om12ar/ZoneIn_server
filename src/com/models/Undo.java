package com.models;

public class Undo {

	
	public static boolean exec(int objID,NotificationHandler notf, int id)
	{
		return notf.undo(objID,id);
	}
}
