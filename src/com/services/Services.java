package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import com.models.DBConnection;
//import com.models.UserModel;
import com.models.*; 

@Path("/")
public class Services {



	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("followerID") String followerID, @FormParam("followedID") String followedID){
		
		Boolean status = UserModel.follow(Integer.parseInt(followerID), Integer.parseInt(followedID));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/getUserPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserPosition(@FormParam("id") String userID ){
	
		double lat = UserModel.getLatById(Integer.parseInt(userID));
		double lon = UserModel.getLonById(Integer.parseInt(userID));
		JSONObject json = new JSONObject();
		json.put("lat", lat);
		json.put("long", lon);
		return json.toJSONString();
	}

	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("followerID") String followerID, @FormParam("followedID") String followedID) {
		Boolean status = UserModel.unfollow(Integer.parseInt(followerID), Integer.parseInt(followedID)); 

		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST 
	@Path("/getFollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowers(@FormParam("userID")Integer id)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<UserModel> followers = new ArrayList<>(UserModel.getFollowersIDs(id)) ;
		if(followers.size()!=0){
			/*for(int i=0;i<followedby.length;i++)
			{
			UserModel userfollowedby = UserModel.dataFollower(followedby[i]);
			jsons.put("id["+i+"]", userfollowedby.getId());
			jsons.put("name["+i+"]", userfollowedby.getName());
			jsons.put("email["+i+"]", userfollowedby.getEmail());
			}*/
			System.out.println("Services.getFollowers()" + followers.toString());
			

		    JSONArray jsArray = new JSONArray();
		    JSONObject jObject = new JSONObject();
			    for (UserModel user : followers)
			    {
			         JSONObject userJson = new JSONObject();
			         userJson.put("id", user.getId());
			         userJson.put("name", user.getName());
			         userJson.put("pass", user.getPass());
			         userJson.put("email", user.getEmail());
			         userJson.put("lat", user.getLat());
			         userJson.put("long", user.getLon());
			         
			         jsArray.add(userJson);
			    }
			    jObject.put("followersList", jsArray);
			
			return jObject.toJSONString();
		}
		else {
			jsons.put("String", "nodata");
			return jsons.toJSONString();
		}
	}
	
	@POST 
	@Path("/getAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowers()
	{
		JSONObject jsons=new JSONObject();
		ArrayList<UserModel> users = new ArrayList<>(UserModel.getAllUsers()) ;
		if(users.size()!=0){
			System.out.println("Services.getAllUsers()" + users.toString());
			
		    JSONArray jsArray = new JSONArray();
		    JSONObject jObject = new JSONObject();
			    for (UserModel user : users)
			    {
			         JSONObject userJson = new JSONObject();
			         userJson.put("id", user.getId());
			         userJson.put("name", user.getName());
			         userJson.put("pass", user.getPass());
			         userJson.put("email", user.getEmail());
			         userJson.put("lat", user.getLat());
			         userJson.put("long", user.getLon());
			         
			         jsArray.add(userJson);
			    }
			    jObject.put("userList", jsArray);
			
			return jObject.toJSONString();
		}
		else {
			jsons.put("String", "nodata");
			return jsons.toJSONString();
		}
	}
	
	@POST 
	@Path("/getFollowedBy")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollowedBy(@FormParam("userID")Integer id)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<UserModel> followedByUser = new ArrayList<>(UserModel.getFollowedBy(id)) ;

		if(followedByUser.size() > 0){
			JSONArray jsArray = new JSONArray();
			JSONObject jObject = new JSONObject();
			for (UserModel user : followedByUser)
			{
				JSONObject userJson = new JSONObject();
				userJson.put("id", user.getId());
				userJson.put("name", user.getName());
				userJson.put("pass", user.getPass());
				userJson.put("email", user.getEmail());
				userJson.put("lat", user.getLat());
				userJson.put("lon", user.getLon());

				jsArray.add(userJson);
			}
			jObject.put("followedByUser" , jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("String", "empty set");
			return jsons.toJSONString();
		}
	}



	@POST
	@Path("/addplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("name") String name,
			@FormParam("description") String description, @FormParam("lon") double longitude, @FormParam("lat") double latitude) {
		Place place = Place.addPlace(name, description, longitude, latitude); 
		
		JSONObject json = new JSONObject();
		json.put("id", place.getID() );
		json.put("name", place.getName());
		json.put("description", place.getDescription());
		json.put("lat", place.getLatitude());
		json.put("long", place.getLongitude());
		return json.toJSONString();
	}
	

	
	@POST 
	@Path("/getAllPlaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPlaces()
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Place> places = new ArrayList<>(Place.getAllPlaces()) ;
		if(places.size()!=0){			
		    JSONArray jsArray = new JSONArray();
		    JSONObject jObject = new JSONObject();
			    for (Place place : places)
			    {
			         JSONObject placeJson = new JSONObject();
			         placeJson.put("id", place.getID());
			         placeJson.put("name", place.getName());
			         placeJson.put("description", place.getDescription());
			         placeJson.put("lat", place.getLatitude());
			         placeJson.put("long", place.getLongitude());
			         
			         jsArray.add(placeJson);
			    }
			    jObject.put("placeList", jsArray);
			
			return jObject.toJSONString();
		}
		else {
			jsons.put("String", "nodata");
			return jsons.toJSONString();
		}
	}
	
	@POST
	@Path("/checkIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkIn(@FormParam("placeID") int placeID,
			@FormParam("userID") int userID) {
		Boolean status = Place.checkIn(placeID, userID); 
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	
	@POST
	@Path("/comment")
	@Produces(MediaType.TEXT_PLAIN)
	public String comment(@FormParam("checkinID") int checkinID,
			@FormParam("comment") String comment) {
		Boolean status = Place.comment(checkinID, comment);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/like")
	@Produces(MediaType.TEXT_PLAIN)
	public String like(@FormParam("checkinID") int checkinID) {
		Boolean status = Place.like(checkinID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello";

	}
	


	

}

