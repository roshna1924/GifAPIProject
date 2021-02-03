package com.giffy.api;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller that accepts requests for root level "/giphy"
 * 
 * @author Roshna Toke
 *
 */
@RestController
public class GiphyController {
	Services service = new Services();
	
	@Autowired
	private UserRepository repo;

	public GiphyController() {
	}

	public GiphyController(Services service) {
		this.service = service;
	}

	/**
	 * This method accepts the search string and gets the related gifs from gif
	 * service and returns json response if service returns at least 5 results
	 * otherwise returns a empty json response
	 * 
	 * @param query Input search query for which user need to get the gifs
	 * @return The json response object with 0 or 5 gifs objects.
	 */
	@GetMapping("/giphy")
	public Result getGiphyController(String query) {
		System.out.println(query);
		if (query == null)
			return new Result();

		String output = service.getGiphy(query);
		JSONObject obj = null;
		ArrayList<Gif> list = new ArrayList<>();
		Result res = new Result();

		// Using the JSON simple library parse the string into a json object
		JSONParser parse = new JSONParser();

		try {

			obj = (JSONObject) parse.parse(output);

			JSONArray json_arr = (JSONArray) obj.get("data");
			int limit = (json_arr.size() >= 5) ? 5 : 0;

			for (int i = 0; i < limit; i++) {

				JSONObject urlObj = (JSONObject) json_arr.get(i);

				String gif_id = (String) urlObj.get("id");
				String gif_url = (String) urlObj.get("url");
				
				Gif gif = new Gif(gif_id, gif_url);
				this.repo.save(gif);
				list.add(gif);
			}
			res.setData(list);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

}
