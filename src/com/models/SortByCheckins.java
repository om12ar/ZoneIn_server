package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByCheckins implements SortPlaces{

	@Override
	public ArrayList<Place> sort() {
		ArrayList<Place> places = Place.getAllPlaces();
		Collections.sort(places, new CheckinComparator());
		return places;
	}

	
	class CheckinComparator implements Comparator<Place>{

		@Override
		public int compare(Place place1, Place place2) {
			return place2.getNumberOfCheckins() - place1.getNumberOfCheckins();
		}

	}
}
