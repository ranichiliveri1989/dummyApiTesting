package com.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginObjectPojo {

	
		@JsonProperty("username")
		private String username1;
		
		@JsonProperty("password")
		private String password1;

		public String getUsername1() {
			return username1;
		}

		public void setUsername1(String username1) {
			this.username1 = username1;
		}

		public String getPassword1() {
			return password1;
		}

		public void setPassword1(String password1) {
			this.password1 = password1;
		}
		
		
		

	}

