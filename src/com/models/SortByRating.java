package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByRating implements SortCheckins{


	@Override
	public ArrayList<Checkin> sort(ArrayList<Checkin> checkins) {

		
		Collections.sort(checkins , new RatingComparator());
		return checkins;
	}
		



class RatingComparator implements Comparator<Checkin>{

			@Override
			public int compare(Checkin checkin1, Checkin checkin2) {
				return (int)(Place.getAverageRating(checkin2.getPlaceID()) -Place.getAverageRating(checkin1.getPlaceID()));
			}

		}
		
}
