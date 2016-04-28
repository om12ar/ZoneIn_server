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
		JSONArray jsArray = new JSONArray();
		if(followers.size()!=0){		
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
			jsons.put("followersList", jsArray);
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/getAllUsers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers()
	{
		JSONObject jsons=new JSONObject();
		ArrayList<UserModel> users = new ArrayList<>(UserModel.getAllUsers()) ;
		JSONArray jsArray = new JSONArray();
		if(users.size()!=0){
			System.out.println("Services.getAllUsers()" + users.toString());


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
			jsons.put("userList", jsArray);
			return jsons.toJSONString();
		}
	}
	
	
	@POST 
	@Path("/getLikers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLikers(@FormParam ("checkinID") int checkinID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<UserModel> users = new ArrayList<UserModel>();
		users = Checkin.getLikers(checkinID);
		JSONArray jsArray = new JSONArray();
		if(users.size()!=0){
			System.out.println("Services.getAllUsers()" + users.toString());


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
			jsons.put("userList", jsArray);
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
		JSONArray jsArray = new JSONArray();
		if(followedByUser.size() > 0){			
			JSONObject jObject = new JSONObject();
			for (UserModel user : followedByUser)
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
			jObject.put("followedByUser" , jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("followedByUser", jsArray);
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/getsavedplaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSavedPlace(@FormParam("userID")Integer id)
	{

		JSONObject jsons=new JSONObject();
		ArrayList<Integer> UserSavedPlaces = new ArrayList<>(UserModel.getsavePlace(id)) ;
		JSONArray jsArray = new JSONArray();
		if(UserSavedPlaces.size() > 0){

			JSONObject jObject = new JSONObject();
			for (Integer user : UserSavedPlaces)
			{
				JSONObject userJson = new JSONObject();
				Place place=Place.getPlaceByID(user);
				userJson.put("id", user);
				userJson.put("name",place.getName());
				userJson.put("description", place.getDescription());
				userJson.put("rating", place.getRating());
				userJson.put("lat",place.getLatitude());
				userJson.put("long", place.getLongitude());

				jsArray.add(userJson);
			}
			jObject.put("SavedPlaces", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("SavedPlaces", jsArray);
			return jsons.toJSONString();
		}
	}



	@POST
	@Path("/addplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("name") String name,
			@FormParam("description") String description, @FormParam("long") double longitude, @FormParam("lat") double latitude) {
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
	@Path("/saveplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String SavePlace(@FormParam("userID")Integer id,@FormParam("placeID") Integer placeid)
	{
		Boolean status = UserModel.savePlace(id,placeid);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();

	}

	@POST 
	@Path("/resetpassword")
	@Produces(MediaType.TEXT_PLAIN)
	public String getpassword(@FormParam("userEmail")String email)
	{
		JSONObject jsons=new JSONObject();

		String password=UserModel.restorePassword(email);
		if(password!=null){

			jsons.put("password", password);
			return jsons.toJSONString();
		}
		else {
			jsons.put("password", "empty set");
			return jsons.toJSONString();
		}
	}





	@POST 
	@Path("/getAllPlaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPlaces()
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Place> places = Place.getAllPlaces() ;
		JSONArray jsArray = new JSONArray();
		if(places.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Place place : places)
			{
				JSONObject placeJson = new JSONObject();
				placeJson.put("id", place.getID());
				placeJson.put("name", place.getName());
				placeJson.put("description", place.getDescription());
				placeJson.put("lat", place.getLatitude());
				placeJson.put("long", place.getLongitude());
				placeJson.put("checkins", place.getNumberOfCheckins());
				jsArray.add(placeJson);
			}
			jObject.put("placeList", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("placeList" , jsArray);
			return jsons.toJSONString();
		}
	}

	@POST
	@Path("/checkIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkIn(@FormParam("placeID") int placeID,
			@FormParam("userID") int userID, @FormParam("review") String review, @FormParam("rating") double rating) {
		Boolean status = Checkin.checkIn(placeID, userID, review, rating); 
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/getallnotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String getallnotification(@FormParam("ID")Integer ID)
	{
		JSONObject jsons=new JSONObject();

		ArrayList<NotificationModel> userNotification =
				new ArrayList<>(NotificationModel.getNotificationText(ID)) ;

		JSONArray jsArray = new JSONArray();
		if(userNotification.size() > 0){

			JSONObject jObject = new JSONObject();
			for (NotificationModel user:userNotification)
			{
				JSONObject userJson = new JSONObject();


				userJson.put("notfid", user.getNotfID());

				userJson.put("FromID",user.getUser());

				userJson.put("txt", user.getNotificationText());

				jsArray.add(userJson);

			}
			jObject.put("notification" , jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("notication", jsArray);
			return jsons.toJSONString();
		}
	}
	@POST
	@Path("/sendLike")
	@Produces(MediaType.TEXT_PLAIN)
	public String makeLikeNotification(@FormParam("fromID")Integer fromID,@FormParam("toID")Integer toID,@FormParam("post") Integer postID )
	{

		JSONObject jsons=new JSONObject();
		NotificationModel notification1=new Like ();

		int number=notification1.getnumberofNotification(toID);
		notification1.addUserID(toID);

		notification1.addNotificationText(fromID, toID,postID);

		jsons.put("NumberOfnotification", number);

		return jsons.toJSONString();	
	}

	@POST
	@Path("/getCommentnotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCommentnote(@FormParam("ID")Integer ID)
	{

		//JSONObject jsons=new JSONObject();
		JSONObject jObject = new JSONObject();

		ArrayList<NotificationModel> userNotification =
				new ArrayList<>(NotificationModel.getNotificationText(ID)) ;

		JSONArray jsArray = new JSONArray();
		if(userNotification.size() > 0){



			for (int i=0;i<userNotification.size();i++)
			{
				Comment user=(Comment) userNotification.get(i);
				JSONObject userJson = new JSONObject();
				userJson.put("notfID", user.NotfID);
				userJson.put("FromID", user.user);
				userJson.put("txt", user.notificationText);
				jsArray.add(userJson);

			}
			jObject.put("notication",jsArray);

			return jObject.toJSONString();
		}
		else {
			jObject.put("notication", jsArray);
			return jObject.toJSONString();
		}
	}

	@POST
	@Path("/getLikenotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLikenote(@FormParam("ID")Integer ID)
	{
		//System.out.println("At the server : "+ID);
		JSONObject jsons=new JSONObject();
		NotificationModel notification1=new Like();

		ArrayList<NotificationModel> userNotification =
				new ArrayList<>(notification1.getNotificationText(ID)) ;
		//System.out.println(userNotification.size());
		if(userNotification.size() > 0){
			JSONArray jsArray = new JSONArray();
			JSONObject jObject = new JSONObject();
			System.out.println("Service okay");
			for (int i=0;i<userNotification.size();i++)
			{
				Comment user=(Comment) userNotification.get(i);
				JSONObject userJson = new JSONObject();
				userJson.put("notfID", user.NotfID);
				userJson.put("FromID", user.user);
				userJson.put("txt", user.notificationText);
				jsArray.add(userJson);

			}
			jObject.put("notication" , jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("notification", "empty set");
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/numberofnotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String getnumberofnotification(@FormParam("userID")Integer ID)
	{
		JSONObject jsons=new JSONObject();
		NotificationModel not1=new Comment("");
		int number=not1.getnumberofNotification(ID);


		jsons.put("numberOfNotification", number);

		return jsons.toJSONString();


	}
	@POST
	@Path("/sendcomment")
	@Produces(MediaType.TEXT_PLAIN)
	public String makenote(@FormParam("fromID")Integer fromID,@FormParam("toID")Integer toID,@FormParam("post")Integer postID,@FormParam("txt")String commnt)
	{
		JSONObject jsons=new JSONObject();
		NotificationModel notification1=new Comment(commnt);

		int number=notification1.getnumberofNotification(toID);
		notification1.addUserID(toID);

		notification1.addNotificationText(fromID, toID, postID);

		jsons.put("numberOFNotification", number);

		return jsons.toJSONString();	
	}


	@POST
	@Path("/comment")
	@Produces(MediaType.TEXT_PLAIN)
	public String comment(@FormParam("checkinID") int checkinID,
			@FormParam("comment") String comment, @FormParam("userID")int userID) {
		Boolean status = Checkin.comment(checkinID, comment,userID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/like")
	@Produces(MediaType.TEXT_PLAIN)
	public String like(@FormParam("checkinID") int checkinID , @FormParam("userID") int userID) {
		Boolean status = Checkin.like(checkinID , userID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/unlike")
	@Produces(MediaType.TEXT_PLAIN)
	public String unlike(@FormParam("checkinID") int checkinID , @FormParam("userID") int userID) {
		Boolean status = Checkin.unlike(checkinID , userID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}


	@POST
	@Path("/uncomment")
	@Produces(MediaType.TEXT_PLAIN)
	public String uncomment(@FormParam("commentID") int commentID) {
		Boolean status = Checkin.uncomment(commentID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/removeCheckin")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCheckin(@FormParam("checkinID") int checkinID) {
		Boolean status = Checkin.removeCheckin(checkinID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/removePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String removePlace(@FormParam("placeID") int placeID) {
		Boolean status = Place.removePlace(placeID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/unsavePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String unsavePlace(@FormParam("placeID") int placeID,@FormParam("userID") int id) {
		NotificationHandler notificationHandler=new UserModel();
		Boolean status = Undo.exec(placeID, notificationHandler,id);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/rate")
	@Produces(MediaType.TEXT_PLAIN)
	public String rate(@FormParam("userID") int userID , @FormParam("placeID") int placeID , @FormParam("rating") double rating) {
		Boolean status = Place.setAverageRating(userID, placeID, rating);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@POST
	@Path("/getRating")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRating(@FormParam("placeID") int placeID) {
		double rating = Place.getAverageRating(placeID);
		JSONObject json = new JSONObject();
		json.put("rating" , rating);
		return json.toJSONString();
	}
	
	@POST
	@Path("/getCheckinID")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCheckinID(@FormParam("placeID") int placeID , @FormParam("userID") int userID  ) {
		int checkinID = Checkin.returnCheckinID(placeID, userID);
		JSONObject json = new JSONObject();
		json.put("checkinD" , checkinID);
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello";

	}


	@POST 
	@Path("/getComments")
	@Produces(MediaType.TEXT_PLAIN)
	public String getComments(@FormParam("checkinID") int checkinID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<CheckinComment> comments = new ArrayList<>(Checkin.getComments(checkinID));
		JSONArray jsArray = new JSONArray();
		if(comments.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (CheckinComment comment : comments)
			{
				JSONObject commentJson = new JSONObject();
				commentJson.put("comment", comment.getComment());
				commentJson.put("comment ID", comment.getID());
				commentJson.put("check in ID", comment.getCheckinID());


				jsArray.add(commentJson);
			}
			jObject.put("commentList", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("commentList", jsArray);
			return jsons.toJSONString();
		}
	}


	@POST 
	@Path("/getCheckinsByPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCheckins(@FormParam("placeID") int placeID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getCheckinsByPlace(placeID);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("checkinList", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("checkinList", jsArray);
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/getCheckinsByUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCheckinsByUser(@FormParam("userID") int userID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getCheckinsByUser(userID);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("placeList", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("placeList", jsArray);
			return jsons.toJSONString();
		}
	}


	@POST 
	@Path("/getHomePage")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHomePage(@FormParam("userID") int userID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getHomePage(userID);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("checkins", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("checkins", jsArray);
			return jsons.toJSONString();
		}
	}


	@POST 
	@Path("/sortHomePageByRating")
	@Produces(MediaType.TEXT_PLAIN)
	public String sortHomePageByRating(@FormParam("userID") int userID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getHomePage(userID);
		SortByRating sorter = new SortByRating();
		checkins = sorter.sort(checkins);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("checkins", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("checkins", jsArray);
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/sortHomePageByCheckins")
	@Produces(MediaType.TEXT_PLAIN)
	public String sortHomePageByCheckins(@FormParam("userID") int userID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getHomePage(userID);
		Context context = new Context(new SortByCheckins()); 
		checkins = context.sort(checkins);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("checkins", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("checkins", jsArray);
			return jsons.toJSONString();
		}
	}

	@POST 
	@Path("/sortHomePageByDistance")
	@Produces(MediaType.TEXT_PLAIN)
	public String sortHomePageByDistance(@FormParam("userID") int userID)
	{
		JSONObject jsons=new JSONObject();
		ArrayList<Checkin> checkins = Checkin.getHomePage(userID);
		Context context = new Context(new SortByDistance()); 
		checkins = context.sort(checkins);
		JSONArray jsArray = new JSONArray();
		if(checkins.size()!=0){			
			JSONObject jObject = new JSONObject();
			for (Checkin checkin : checkins)
			{
				JSONObject checkinJson = new JSONObject();

				checkinJson.put("id" , checkin.getCheckinID() );
				checkinJson.put("username", checkin.getUserName() );
				checkinJson.put("review", checkin.getReview() );
				checkinJson.put("rating", checkin.getRating() );
				checkinJson.put("likes", checkin.getLikes());

				jsArray.add(checkinJson);
			}
			jObject.put("checkins", jsArray);

			return jObject.toJSONString();
		}
		else {
			jsons.put("checkins", jsArray);
			return jsons.toJSONString();
		}
	}






	@POST 
	@Path("/getactions")
	@Produces(MediaType.TEXT_PLAIN)
	public String getActions(@FormParam("userID") int userID)
	{
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray = new JSONArray();
		ArrayList<Action> actions = Action.getActions(userID);

		for (Action action : actions)
		{
			JSONObject temp = new JSONObject();

			temp.put("actionID", action.getActionID());
			temp.put("userID", action.getUserID());
			temp.put("actionType", action.getActionType());
			temp.put("description", action.getDescription());
			temp.put("parameterID", action.getActionParameterID());

			jsonArray.add(temp);
		}

		jsonObject.put("actionsList", jsonArray);

		return jsonObject.toJSONString();
	}


	@POST 
	@Path("/addaction")
	@Produces(MediaType.TEXT_PLAIN)
	public String addAction(@FormParam("userID") int userID, @FormParam("actionType") String actionType,
			@FormParam("description") String description, @FormParam("parameterID") int parameterID) 
	{
		System.out.println(description);
		Boolean status = Action.addAction(userID, actionType, description,parameterID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status ? 1 : 0);
		return jsonObject.toJSONString();
	}


	@POST
	@Path("/removeaction")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeAction(@FormParam("actionID") int actionID){
		Boolean status = Action.removeAction(actionID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status ? 1 : 0);
		return jsonObject.toJSONString();
	}


}

