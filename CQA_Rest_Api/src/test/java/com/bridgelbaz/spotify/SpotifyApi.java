package com.bridgelbaz.spotify;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class SpotifyApi {
	
	public String token;
	String user_id = "317v5jucqs74swivw6377z2k6f5q";
	String playlist_id = "6qXWdTrY5KLF2pwQCRzqWp";
	String track;
	@BeforeTest
	public void getToken() {
		token = "Bearer BQCb97sJPc_9Ya9ovgTswYU10U2XgV8BTsVzR8fyTYd4ctQPGNRkUlTwaRYWhDmUa9U9LcvPGILnud3EoMC04pT3TgPfg6asOmN6ByS1Ovd_8SyvnbeqJXt_G6aJKPgPOCtXYzxfX5-7B7bOiUJVv0OtVAqEsBJ2OdvMgLRc4KREQj964iZNghQ7rOf18pKv_SZ9jvbpTjZ5cg20FKjqS54KJm7JWyaysM9gxTSlI-oUl91SzBKxDk_hob2UsdhpiohkuglE2Q_vvb7CEgVQryyNm8-X7uX-6y1q8JznoR6QMoW8ypWf4DBRAPPUF6xx90yiJm2FhypQ";
		}
	
	@BeforeTest
	public void getTrack() {
		track = "spotify:track:0dnDTvdUco2UbaBjUtPxNS";
	}
	
	@Test(priority = 1)
	public void testGet_CurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 2)
	public void testGet_Users_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/users/" + user_id +"/");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3)
	public void testCreate_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Mahesh Playlist\",\r\n"
									+ "  \"description\": \"New playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.post("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
		//playListId = response.path("id");
	}
	
	@Test
	public void testGet_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testGet_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testGetCurrent_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testChange_Playlist_Details() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"JR NTR Playlist \",\r\n"
									+ "  \"description\": \"Updated playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.put("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testAdd_Items_to_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParams("uris", track)
							.when()
							.post("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	
	@Test
	public void testGet_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	

	
	@Test
	public void  searchForItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("q","sid sriram")
				.queryParam("type","track")
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);
		
	}
	@Test
	public void UpdatePlaylistItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\n"+
						
						"  \"range_start\": 1,\n" +
						"	 \"insert_before\": 0,\n" +
						"	  \"range_length\": 2\n" +
							
						"}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);

	
	} 
//	@Test
//	public void RemovePlaylistItem() {
//		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
//				.accept(ContentType.JSON)
//				.header("Authorization", token)
//				.body("{\"tracks\":[{\"uri\":\"spotify:track:0dnDTvdUco2UbaBjUtPxNS\"}]}")
//				.when()
//				.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
//response.prettyPrint();
//response.then().assertThat().statusCode(200);
//	}
	@Test
	public void TrackId() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks/0dnDTvdUco2UbaBjUtPxNS");
response.prettyPrint();
response.then().assertThat().statusCode(200);
		
	}
	@Test
	public void TrackGet() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
	@Test
	public void TrackAudioGet()
	{
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/0dnDTvdUco2UbaBjUtPxNS");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
	@Test
	public void TrackAudioGetFeature() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
	@Test
	public void TrackAudioGetFeatureId() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features/0dnDTvdUco2UbaBjUtPxNS");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
}
