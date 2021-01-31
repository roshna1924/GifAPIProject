package com.giffy.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * This class contains service to get gifs from 3rd party rest API
 * @author Roshna Toke
 *
 */
public class Services {

	/**
	 * This method accepts the search string and gets the related gifs from 3rd party rest API (api.giphy.com)
	 * @param query: 
	 * 			String keyword to search the gifs
	 * @return String response containing information about gifs
	 */
	public String getGiphy(String query) {
		String output = "";
		try {

			URL url = new URL("https://api.giphy.com/v1/gifs/search?api_key=OZ9ZPesEpTvMMY9uRod5cso1nmOmz85s&q=" + query
					+ "&limit=5&offset=0&rating=g&lang=en");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			conn.connect();
			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + responsecode);
			} else {

				Scanner sc = new Scanner(url.openStream());

				while (sc.hasNext()) {
					output += sc.nextLine();
				}
				sc.close();
			}
			conn.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
}
