package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByDistance implements SortCheckins {

	@Override
	public ArrayList<Checkin> sort(ArrayList<Checkin> checkins) {
 
		Collections.sort(checkins, new DistanceSorter());
		return checkins; 
		
	}



	public class DistanceSorter implements Comparator<Checkin> {

		@Override
		public int compare(Checkin checkin1, Checkin checkin2) {
			
			return (int) (Place.getPlaceByID(checkin1.getPlaceID()).getDistance(UserModel.getUserById(checkin1.getUserID()))
					   - Place.getPlaceByID(checkin2.getPlaceID()).getDistance(UserModel.getUserById(checkin2.getUserID())));
		}


	}

}