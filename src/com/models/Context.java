package com.models;

import java.util.ArrayList;

public class Context {
	
	private SortCheckins sortCheckins;
	
	   public Context(SortCheckins sortCheckins){
		      this.sortCheckins = sortCheckins;
		   }

		   public ArrayList<Checkin> sort(ArrayList<Checkin> checkins){
		      return sortCheckins.sort(checkins);
		   }

}
