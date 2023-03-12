package com.tests.simpleCRUDwithTekarch;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.constants.Endpoints;
import com.test.models.LoginObjectPojo;
import com.tests.utils.PropertiesUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class UpdateTestData {
	
String token;
	
	@BeforeClass
	public void init() throws IOException {
		
		String bURI=PropertiesUtility.readPropertyData("uri");

		RestAssured.baseURI=bURI;
	}
	
	
	public String login() throws IOException {
		
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
		//res.prettyPrint();
		token= res.body().jsonPath().getString("[0].token");
		System.out.println("token="+token);
		return token;
		
		
	}
	@Test()
	public void updateUserData() throws IOException {
		
		Header tokenHeader=new Header("token", login());
		Response res= RestAssured
		                .given()
			            .contentType(ContentType.JSON)
			            .body("{\"accountno\":\"DA-eclip23\",\"departmentno\":\"9\",\"salary\":\"1000\",\"pincode\":\"111111\",\"userid\":\"p2AiPHqacISNeeGHyEEb\",\"id\":\"4kpjT7qf9hE09obO0dqc\"}")
			            .header(tokenHeader)
		                .when()
		                .put(Endpoints.UPDATE_DATA);
		res.then().statusCode(200);
		res.prettyPrint();
		
		//res.then().body("status",is("success"));
		
		// get the total num of records
		// get all account no for the userid= JBFBUgrJ3VQaudaTXM0r
		// validate size=4 for the previous validation
			
		
	}

}
