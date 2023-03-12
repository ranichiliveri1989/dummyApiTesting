package com.tests.simpleCRUDwithTekarch;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.constants.Endpoints;
import com.test.models.LoginObjectPojo;
import com.test.models.UserPOJO;
import com.tests.utils.PropertiesUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetDataTest {
//String token;
	
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
		String token= res.body().jsonPath().getString("[0].token");
		System.out.println("token="+token);
		return token;
		
		
	}
	
	@Test
	public void getUserData() throws IOException {
		
		Header tokenHeader=new Header("token",login());
		
		System.out.println("token header is:"+tokenHeader);
		System.out.println("endpoint " + Endpoints.GET_DATA);
		Response res= RestAssured
		             .given()
			         .header(tokenHeader)
		             .when()
		             .get(Endpoints.GET_DATA);
		
		
		// res.prettyPrint();
		//System.out.println("token2 header is:"+tokenHeader);
		
		UserPOJO[] data=res.as(UserPOJO[].class);
		System.out.println("number of records="+data.length);
		for (UserPOJO da : data) {
			System.out.println(da.getDepartmentno());
		}
      
       res.then().statusCode(200);
       
      // res.prettyPrint();
		
     //1. Find the total number of user records existing in the response(print to console)
       
	    System.out.println("total records="+res.body().jsonPath().get("size()"));
	
     //4. Find the all records for the given dept no 9
	    
	    ArrayList<String> listOfrecords=res.body().jsonPath().get("findAll{it->it.departmentno=='9'}");
    	System.out.println("all records =="+listOfrecords);
	    
      //2. Find the total number of records existing for the given user id laCGA21I8UVpjNbEFcP4
    	
    	ArrayList<String> totalRecords=res.body().jsonPath().get("findAll{it->it.userid=='laCGA21I8UVpjNbEFcP4'}.accountno");
    	System.out.println("all records =="+totalRecords.size());
    	
    	//assertEquals(listOfaccounts.size(),5);
    	
    	// get all account no for the userid= JBFBUgrJ3VQaudaTXM0r
    	// validate size=4 for the previous validation
    	ArrayList<String> listOfaccounts=res.body().jsonPath().get("findAll{it->it.userid=='JBFBUgrJ3VQaudaTXM0r'}.accountno");
    	System.out.println("all ids =="+listOfaccounts);
    	assertEquals(listOfaccounts.size(),5);
        	
      //5. validate all the id for the given user id lzQHg4ywe0MI87vM7fpF as {sZo9DIeowhov16XdJ1k8,xcUd3LpoVlEqhYsJ4Iea,gpq2G8QLEFmf611v9NpB.KplwvzQFWJd97VeB5atu,bIrDlvlxI1sAAcgon5SR}

    	ArrayList<String> allIds=res.body().jsonPath().get("findAll{it->it.userid=='lzQHg4ywe0MI87vM7fpF'}.id");
		
    	List<String> expected_items = Arrays.asList("sZo9DIeowhov16XdJ1k8","xcUd3LpoVlEqhYsJ4Iea","gpq2G8QLEFmf611v9NpB","KplwvzQFWJd97VeB5atu","bIrDlvlxI1sAAcgon5SR");
    	
    	for (String ids:allIds)
    	    
	    {
	    	Assert.assertTrue(expected_items.contains(ids)); 
	    }
    	
    	//3. Find the sum of all salary(note: salary is in string) of the given user id laCGA21I8UVpjNbEFcP4
		
    	ArrayList<String> allsalary=res.body().jsonPath().get("findAll{it->it.userid=='laCGA21I8UVpjNbEFcP4'}.salary");
		
    	System.out.println("all salary =="+allsalary);
    	//ArrayList<Integer> salaries = new ArrayList<Integer>();
    	int temp=0;
    	for(String sal:allsalary) 
    	{
    		int x=Integer.parseInt(sal);
    		temp=temp+x;
    		//salaries.add(x);
    	}
    	
    	
    	
    	System.out.println("sum of salaries="+temp);
    	
//    	6. Find the avg salary for the given dept no 3 (note: salary is in string)

    	ArrayList<String> allsalaryfordept3=res.body().jsonPath().get("findAll{it->it.departmentno=='3'}.salary");
    	System.out.println("all salary =="+allsalaryfordept3);
    	long temp1=0;
    	int size1=allsalaryfordept3.size();
    	System.out.println("size =="+size1);
    	try
    	{
    		
    	for(String sal:allsalaryfordept3) 
    	{
    		try
    		{
    			int x=Integer.parseInt(sal);
        		temp1=temp1+x;
    		}
    		catch(Exception e)
    		{
    			System.out.println("invalid salary:"+sal);
    		}
    
    	}
    	}
		catch(ClassCastException e)
    	{
			System.out.println("invalid salary:");
    	}
    	double avg=temp1/size1;
    	System.out.println("avg salary for the given dept no 3:  "+avg);
    	
	}

}



