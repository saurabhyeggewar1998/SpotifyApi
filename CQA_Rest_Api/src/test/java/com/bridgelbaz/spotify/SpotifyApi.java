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
		token = "Bearer BQB0M64Kgjhc787m6KlOMidGVO0uDVA2hahFIuP-PKQw6fYMtO5Fq6kyVzdiuyQXAjkRGyzYwDN1sfZwy_Hq9C-8-Ps3Idx8n0GVuU4Wb-Lry7zYSQQ12sqytb7MOnfbGclvWNHE3Oe55NZB1W74YjVKBHvutvjgijMVLsScR1E_PT9HfrvdAum2jdrWFLySknkKWXSIBPzqlT44qn5NUDdlFLVPYoaKLyIXvhCbVOwZQv3MG-EJwtD9Z8M0OHcRXlJGUQNSYxrQgNaL9vlDMIKcrhgVT6JbaV1rdyGHzmDQSwJ0qo8Uh1O2Befr7h4-03QY6py8m7n5";
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
	@Test
	public void RemovePlaylistItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\"tracks\":[{\"uri\":\"spotify:track:0dnDTvdUco2UbaBjUtPxNS\"}]}")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
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
}