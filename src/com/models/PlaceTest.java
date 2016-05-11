package com.models;
import org.testng.*;
import org.testng.annotations.Test;

public class PlaceTest {

	Place place = new Place ();
	@Test
	public void addPlaceTest (){
		
		Place place2 = new Place("Costa", 12, 13, "coffe shop"); 
		Assert.assertEquals(place2.name, Place.addPlace("Costa", "coffe shop",12,13).name);
		
	}
	
	@Test 
	public void getPlaceByIDTest (){
		Assert.assertEquals("FCI", Place.getPlaceByID(1).name);

	}
	
	@Test 
	public void getDistanceTest (){
		UserModel user = new UserModel(); 
		user.setLat(2.0);
		user.setLon(3.0);
		
		place.setLatitude(2.0);
		place.setLongitude(3.0);
		
		Assert.assertEquals(0.0, place.getDistance(user));
	}
	
	@Test
	public void getRatingTest(){
		Assert.assertEquals(4.285714285714286, Place.getAverageRating(3));
	}
	
	@Test
	public void getCommentTest (){
		Assert.assertEquals("I think so too", Place.getCommentByID(2).getComment());
	}
	
	@Test 
	public void removePlaceTest(){
		
		Assert.assertEquals(true, Place.removePlace(11));
	}
}
