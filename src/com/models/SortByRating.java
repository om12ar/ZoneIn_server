package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByRating implements SortPlaces{


	@Override
	public ArrayList<Place> sort() {

		ArrayList<Place> places = Place.getAllPlaces(); 
		Collections.sort(places , new RatingComparator());
		return places;
	}
		



class RatingComparator implements Comparator<Place>{

			@Override
			public int compare(Place place1, Place place2) {
				return (int)(place2.getRating() - place1.getRating());
			}

		}
		
}
