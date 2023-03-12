package com.tests.simpleCRUDwithTekarch;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.constants.Endpoints;
import com.test.models.LoginObjectPojo;
import com.tests.utils.PropertiesUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LogIntoApi {
	String token;
	
	@BeforeClass
	public void init() throws IOException {
		
		String bURI=PropertiesUtility.readPropertyData("uri");

		RestAssured.baseURI=bURI;
	}
	
	@Test
	public void login() throws IOException {
		
		String uName=PropertiesUtility.readPropertyData("username");
		String pWord=PropertiesUtility.readPropertyData("password");
		LoginObjectPojo ob=new LoginObjectPojo();
		ob.setUsername1(uName);
		ob.setPassword1(pWord);
		
		
		Response res= RestAssured
			.given()
				.log().headers()
				.contentType(ContentType.JSON)
				.body(ob)// serialization
			.when()
				.post(Endpoints.LOGIN);

		res.then().log().all();
		res.then().statusCode(201);
		res.then().assertThat().statusCode(201);
		res.prettyPrint();
		token= res.body().jsonPath().getString("[0].token");
		System.out.println("token="+token);
		
	}

}
