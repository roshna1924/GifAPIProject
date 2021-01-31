package com.giffy.api;

import java.util.ArrayList;

/**
 * This is a wrapper class that contains list of Gif objects
 * 
 * @author Roshna Toke
 *
 */
public class Result {

	private ArrayList<Gif> data;

	public Result() {
		data = new ArrayList<>();
	}

	/**
	 * Getter method to return the list of the Gif object
	 * 
	 * @return List of Gif objects
	 */
	public ArrayList<Gif> getData() {
		return data;
	}

	/**
	 * Setter method to set the list of the Gif object(data)
	 * 
	 * @param data: list of the Gif object
	 */
	public void setData(ArrayList<Gif> data) {
		this.data = data;
	}
}
