package com.giffy.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class contains all the information about Gif (Id, Url) that our api need
 * to return
 * 
 * @author Roshna Toke
 *
 */
@Entity
@Table(name = "gif")
public class Gif {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "gif_id")
	private final String gif_id;

	@Column(name = "gif_url")
	private final String url;

	public Gif(String id, String url) {
		this.gif_id = id;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
