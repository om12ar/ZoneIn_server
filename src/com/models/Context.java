package com.models;

import java.util.ArrayList;

public class Context {
	
	private SortPlaces sortPlaces;
	
	   public Context(SortPlaces sortPlaces){
		      this.sortPlaces = sortPlaces;
		   }

		   public ArrayList<Place> sort(){
		      return sortPlaces.sort();
		   }

}
