package com.giffy.api;

import java.util.List;

import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mock;
import org.mockito.InjectMocks;

import com.giffy.api.Services;
import com.sun.el.parser.ParseException;

/**
 * Test cases for {@link GiphyController}
 * @author Roshna Toke
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GiphyController.class)
public class GifControllerTest {
	
	private String jsonString = "{\r\n" + 
			"    \"data\": [\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"1\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/1\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"2\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/2\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"3\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/3\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"4\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/4\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"5\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/5\"\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"id\": \"6\",\r\n" + 
			"            \"url\": \"https://giphy.com/gifs/6\"\r\n" + 
			"        }\r\n" + 
			"    ]\r\n" + 
			"}";
	
	String emptyString = "{\r\n" + 
			"    \"data\": []\r\n" + 
			"}";
	
	@Mock
	private Services mockServices;
	private JSONParser mockParser;
	
	@InjectMocks
	GiphyController giphyController;
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Define the behavior of common mocked objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockServices = PowerMockito.mock(Services.class);
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(jsonString);
		giphyController = new GiphyController(mockServices);
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns empty 
	 * when input query string is empty.
	 * Expected Result: empty json object
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_EmptyQuery() throws Exception {
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(emptyString);
		Result response = giphyController.getGiphyController("");	
		List<Gif> data = response.getData();
		Assert.assertEquals(0, data.size());
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns empty 
	 * when input query string contains only white spaces.
	 * Expected Result: empty json object
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_SpaceQuery() throws Exception {
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(emptyString);
		Result response = giphyController.getGiphyController(" ");	
		List<Gif> data = response.getData();
		Assert.assertEquals(0, data.size());
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns empty 
	 * when input query string is null.
	 * Expected Result: empty json object
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_NullQuery() throws Exception {
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(emptyString);
		Result response = giphyController.getGiphyController(null);	
		List<Gif> data = response.getData();
		Assert.assertEquals(0, data.size());
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns json response with 
	 * exactly 5 data elements when {@link Services#getGiphy(String)} returns 5 or more results.
	 * Expected Result: json object with exactly 5 data elements
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_5Records() throws Exception {
		Result response = giphyController.getGiphyController("Valid");		
		List<Gif> data = response.getData();
		Assert.assertEquals(5, data.size());

		for(int i=0; i< data.size(); i++) {
			Gif giff = data.get(i);
			Assert.assertEquals(String.valueOf(i+1), giff.getGif_id());
			Assert.assertEquals("https://giphy.com/gifs/" + (i+1), giff.getURL());
		}
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns empty json response 
	 * when {@link Services#getGiphy(String)} returns 0 results.
	 * Expected Result: json object with 0 data elements
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_0Records() throws Exception {
		String emptyString = "{\r\n" + 
				"    \"data\": []\r\n" + 
				"}";
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(emptyString);
		Result response = giphyController.getGiphyController("Valid");	
		List<Gif> data = response.getData();
		Assert.assertEquals(0, data.size());
	}
	
	/**
	 * Checks whether {@link GiphyController#getGiphyController(String)} returns empty json response 
	 * when {@link Services#getGiphy(String)} returns 1-4 results.
	 * Expected Result: json object with 0 data elements
	 * @throws Exception
	 */
	@Test
	public void getGiphyController_success_lessThan_5Records() throws Exception {
		String threeRecordString = "{\r\n" + 
				"    \"data\": [\r\n" + 
				"        {\r\n" + 
				"            \"id\": \"1\",\r\n" + 
				"            \"url\": \"https://giphy.com/gifs/1\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"id\": \"2\",\r\n" + 
				"            \"url\": \"https://giphy.com/gifs/2\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"id\": \"3\",\r\n" + 
				"            \"url\": \"https://giphy.com/gifs/3\"\r\n" + 
				"        },\r\n" + 
				"    ]\r\n" + 
				"}";
		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(threeRecordString);
		Result response = giphyController.getGiphyController("Valid");	
		List<Gif> data = response.getData();
		Assert.assertEquals(0, data.size());
	}
	
	/**
	 * Checks whether  {@link GiphyController#getGiphyController(String)} throws ParseException 
	 * when there is an error in parsing response received from {@link Services#getGiphy(String)}
	 * Expected Result: ParseException is thrown
	 * @throws Exception
	 */
//	@Test
//	public void getGiphyController_error_jsonParse() throws Exception {
//		String emptyString = "{\r\n" + 
//				"    \"data\": []\r\n" + 
//				"}";
//		mockParser = PowerMockito.mock(JSONParser.class);
//		PowerMockito.whenNew(JSONParser.class).withAnyArguments().thenReturn(mockParser);
//		PowerMockito.when(mockServices.getGiphy(any(String.class))).thenReturn(emptyString);
//		PowerMockito.when(mockParser.parse(any(String.class))).thenThrow(ParseException.class);
//		
//		expectedException.expect(ParseException.class);
//		giphyController.getGiphyController("Empty");	
//	}
}
