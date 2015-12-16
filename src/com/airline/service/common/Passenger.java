/**
 * 
 */
package com.airline.service.common;

import java.io.Serializable;

/**
 * @author
 *
 */
public class Passenger implements Serializable{

	String firstName;
	String lastName;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
