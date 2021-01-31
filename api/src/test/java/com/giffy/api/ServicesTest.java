package com.giffy.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

import org.mockito.Mock;
import org.mockito.InjectMocks;

/**
 * Test cases for {@link Services}
 * @author Roshna Toke
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Services.class)
public class ServicesTest {

	private final String serviceResponse = "{    \"data\": [        {            \"id\": \"1\",            \"url\": \"https://giphy.com/gifs/1\"        },        {            \"id\": \"2\",            \"url\": \"https://giphy.com/gifs/2\"        },        {            \"id\": \"3\",            \"url\": \"https://giphy.com/gifs/3\"        },        {            \"id\": \"4\",            \"url\": \"https://giphy.com/gifs/4\"        },        {            \"id\": \"5\",            \"url\": \"https://giphy.com/gifs/5\"        }    ]}";
	private final String emptyResponse   = "{[    \"data\": []]}";
	
	@Mock
	private JSONParser mockParser;
	
	@Mock
	URL mockUrl;
	
	@Mock
	HttpURLConnection mockConn;
	
	@InjectMocks
	Services services;
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Define the behavior of common mocked objects.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(mockUrl);
		PowerMockito.when(mockUrl.openConnection()).thenReturn(mockConn);
		PowerMockito.when(mockConn.getResponseCode()).thenReturn(200);
		InputStream in = new ByteArrayInputStream(serviceResponse.getBytes());
		PowerMockito.when(mockUrl.openStream()).thenReturn(in);
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} returns empty response string
	 * when input search query is empty.
	 * Expected Result: Empty string
	 * @throws Exception
	 */
	@Test
	public void getGiphy_fail_emptyQuery() throws Exception {
		InputStream in = new ByteArrayInputStream(emptyResponse.getBytes());
		PowerMockito.when(mockUrl.openStream()).thenReturn(in);
		String response = services.getGiphy("");
		Assert.assertEquals(emptyResponse, response);
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} returns empty response string
	 * when input search query contains only white spaces.
	 * Expected Result: Empty string
	 * @throws Exception
	 */
	@Test
	public void getGiphy_fail_spaceQuery() throws Exception {
		InputStream in = new ByteArrayInputStream(emptyResponse.getBytes());
		PowerMockito.when(mockUrl.openStream()).thenReturn(in);
		String response = services.getGiphy(" ");
		Assert.assertEquals(emptyResponse, response);
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} returns empty response string
	 * when input search query is null
	 * Expected Result: Empty string
	 * @throws Exception
	 */
	@Test
	public void getGiphy_fail_nullQuery() throws Exception {
		InputStream in = new ByteArrayInputStream(emptyResponse.getBytes());
		PowerMockito.when(mockUrl.openStream()).thenReturn(in);
		String response = services.getGiphy(null);
		Assert.assertEquals(emptyResponse, response);
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} throws RuntimeException when 'api.giphy.com' return 
	 * response code other than 200 (ok)
	 * Expected Result: RuntimeException is thrown with valid message
	 * @throws Exception
	 */
	@Test
	public void getGiphy_fail_error404() throws Exception {
		PowerMockito.when(mockConn.getResponseCode()).thenReturn(404);
		
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Failed : HTTP error code : 404");
		services.getGiphy("Valid");
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} gets a valid json response from 'api.giohy.com' 
	 * and returns all the received data in string format when a valid search query is passed
	 * Expected Result: A valid string with data received with 'api.giohy.com' is returned
	 * @throws Exception
	 */
	@Test
	public void getGiphy_success() throws Exception {
		String response = services.getGiphy("Valid");
		Assert.assertEquals(serviceResponse, response);
	}
	
	/**
	 * Checks whether {@link Services#getGiphy(String)} throws IOException when there is error in opening
	 * connection with the URL
	 * Expected Result: IOException is thrown
	 * @throws Exception
	 */
//	@Test
//	public void getGiphy_fail_ioException() throws Exception {
//		PowerMockito.when(mockUrl.openConnection()).thenThrow(IOException.class);
//		
//		expectedException.expect(IOException.class);
//		services.getGiphy("Valid");
//	}
}
