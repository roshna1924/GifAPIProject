package com.giffy.api;

/**
 * This class contains all the information about Gif (Id, Url) that our api need
 * to return
 * 
 * @author Roshna Toke
 *
 */
public class Gif {

	private final String gif_id;
	private final String url;

	public Gif(String id, String url) {
		this.gif_id = id;
		this.url = url;
	}

	/**
	 * This method return the gif_id of the current Gif object
	 * 
	 * @return String gif id
	 */
	public String getGif_id() {
		return gif_id;
	}

	/**
	 * This method return the url of the current Gif object
	 * 
	 * @return String gif url
	 */
	public String getURL() {
		return url;
	}

}
