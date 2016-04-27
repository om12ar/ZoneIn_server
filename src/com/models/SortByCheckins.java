package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByCheckins implements SortCheckins{

	@Override
	public ArrayList<Checkin> sort(ArrayList<Checkin> checkins) {
		Collections.sort(checkins, new CheckinComparator());
		return checkins;
	}

	
	class CheckinComparator implements Comparator<Checkin>{

		@Override
		public int compare(Checkin checkin1, Checkin checkin2) {
			return (int)(Place.getPlaceByID(checkin2.getPlaceID()).getNumberOfCheckins() - Place.getPlaceByID(checkin1.getPlaceID()).getNumberOfCheckins());
		}

	}
	
}