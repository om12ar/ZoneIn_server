package com.models;
import org.testng.*;
import org.testng.AssertJUnit;
import com.services.*;
import org.testng.AssertJUnit;
import java.io.IOException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ServicesTest {


	final String url="http://localhost:8080/ZoneIn_server/rest/getAllPlaces";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getAllPlaces" })

	public void getPlacesTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getPlaces();
		// Compare result
		Assert.assertTrue(response.contains("FCI"));
		System.out.println("DEBUG1:" + response);
	}

	final String url2 ="http://localhost:8080/ZoneIn_server/rest/getAllUsers";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getAllUsers" })

	public void getUsersTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getUsers();
		// Compare result
		Assert.assertTrue(response.contains("Omar"));
		System.out.println("DEBUG2:" + response);
	}


	final String url3 ="http://localhost:8080/ZoneIn_server/rest/getHomePage?userID=5";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getHomePage?userID=5" })

	public void getHomePageTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getHomePage(5);
		// Compare result
		Assert.assertTrue(response.contains("mohamed"));
		System.out.println("DEBUG3:" + response);
	}

	final String url4 ="http://localhost:8080/ZoneIn_server/rest/sortHomePageByRating?userID=5";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/sortHomePageByRating?userID=5" })

	public void sortHomePageByRatingTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.sortHomePageByRating(5);
		// Compare result
		Assert.assertTrue(response.contains("mohamed"));
		System.out.println("DEBUG4:" + response);
	}
	
	final String url5 ="http://localhost:8080/ZoneIn_server/rest/sortHomePageByCheckins?userID=5";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/sortHomePageByCheckins?userID=5" })

	public void sortHomePageByCheckinsTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.sortHomePageByCheckins(5);
		// Compare result
		Assert.assertTrue(response.contains("mohamed"));
		System.out.println("DEBUG5:" + response);
	}
	
	final String url6 ="http://localhost:8080/ZoneIn_server/rest/sortHomePageByDistance?userID=5";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/sortHomePageByCheckins?userID=5" })

	public void sortHomePageByDistanceTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.sortHomePageByCheckins(5);
		// Compare result
		Assert.assertTrue(response.contains("mohamed"));
		System.out.println("DEBUG6:" + response);
	}

	final String url7 ="http://localhost:8080/ZoneIn_server/rest/getCheckinsByPlace?placeID=1";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getCheckinsByPlace?placeID=1" })

	public void getCheckinsByPlaceTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getCheckins(1);
		// Compare result
		Assert.assertTrue(response.contains("FCI"));
		System.out.println("DEBUG7:" + response);
	}
	
	final String url8 ="http://localhost:8080/ZoneIn_server/rest/getCheckinsByUser?userID=3";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getCheckinsByUser?userID=3" })

	public void getCheckinsUserTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getCheckinsByUser(3);
		// Compare result
		Assert.assertTrue(response.contains("Buddha"));
		System.out.println("DEBUG8:" + response);
	}
	
	final String url9 ="http://localhost:8080/ZoneIn_server/rest/getRating?placeID=5";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getRating?placeID=5" })

	public void getRating(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getRating(5);
		// Compare result
		Assert.assertTrue(response.contains("2.0"));
		System.out.println("DEBUG9:" + response);
	}
	
	final String url10 ="http://localhost:8080/ZoneIn_server/rest/checkIn?userID=1&placeID=1&review=good&rating=2";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/checkIn?userID=1&placeID=1&review=good&rating=2" })

	public void checkinTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.checkIn(1, 1, "good", 2);
		// Compare result
		Assert.assertTrue(response.contains("1"));
		System.out.println("DEBUG10:" + response);
	}
	
	final String url11 ="http://localhost:8080/ZoneIn_server/rest/login?email=m.samir&pass=123456789";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/login?email=m.samir&pass=123456789" })

	public void LoginTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.login("m.samir", "123456789");//.checkIn(1, 1, "good", 2);
		// Compare result
		Assert.assertTrue(response.contains("m.samir"));
		System.out.println("DEBUG11:" + response);
	}
	
	final String url12 ="http://localhost:8080/ZoneIn_server/rest/updatePosition?id=1&lat=1&long=1";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/updatePosition?id=1&lat=1&long=1" })

	public void updatePositionTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.updatePosition("1", "1", "1");
		// Compare result
		Assert.assertTrue(response.contains("1"));
		System.out.println("DEBUG12:" + response);
	}
	
	final String url13 ="http://localhost:8080/ZoneIn_server/rest/follow?followerID=1&followedID=2";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/follow?followerID=1&followedID=2" })

	public void followTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.follow("1","2");
		// Compare result
		Assert.assertTrue(response.contains("1"));
		System.out.println("DEBUG13:" + response);
	}
	
	final String url14 ="http://localhost:8080/ZoneIn_server/rest/getUserPosition?id=1";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getUserPosition?id=1" })

	public void getUserPositionTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getUserPosition("1");
		// Compare result
		Assert.assertTrue(response.contains("long"));
		System.out.println("DEBUG14:" + response);
	}
	
	final String url15 ="http://localhost:8080/ZoneIn_server/rest/getFollowers?id=2";
	@Test
	@Parameters({ "http://localhost:8080/ZoneIn_server/rest/getFollowers?id=2" })

	public void getFollowersTest(String url) throws InterruptedException, IOException{	
		// Create json
		String json = "";
		// Get json to api
		Services services = new Services ();
		String response = services.getFollowers(2);//.getUserPosition("1");
		// Compare result
		Assert.assertTrue(response.contains("name"));
		System.out.println("DEBUG15:" + response);
	}
	


}
