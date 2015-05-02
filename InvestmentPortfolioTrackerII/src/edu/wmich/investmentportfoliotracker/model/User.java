package edu.wmich.investmentportfoliotracker.model;

public class User{
	
	public static final String TABLE_NAME = "users";
	public static final String ID = "_id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String[] COLUMNS = {ID, USERNAME, PASSWORD };

	private long id;
	private String username;
	private String password;
	
	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
