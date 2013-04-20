package com.example.model;

import java.sql.Date;

public class TodoItem {
	private Long id;

	private String name;
	private String url;

	public TodoItem() {
	}

	public TodoItem(String name, String url) {
		super();
		this.name = name;
		this.url = url;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return url;
	}



}
