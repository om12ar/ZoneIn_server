package com.models;
import org.testng.*;
import org.testng.AssertJUnit;
import com.services.*;
import org.testng.AssertJUnit;
import java.io.IOException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NewTest {
  
	
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
	

	
}
