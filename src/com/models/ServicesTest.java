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


}
