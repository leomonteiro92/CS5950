package edu.wmich.investmentportfoliotracker.model;

public class Portfolio {

	public static final String TABLE_NAME = "portfolios";
	public static final String ID = "_id";
	public static final String SYMBOL = "symbol";
	public static final String NUMBER_OF_SHARES = "number_of_shares";
	public static final String USER = "user_id";
	public static final String PRICE = "price";
	public static final String SUBTOTAL = "subtotal";
	public static final String[] COLUMNS = { ID, SYMBOL, NUMBER_OF_SHARES,
			PRICE, SUBTOTAL, USER };

	private long id;
	private String symbol;
	private Integer numberOfShares;
	private Double price;
	private Double subtotal;
	private long user;
	
	public Portfolio() {
		numberOfShares = 0;
		price = 0.0;
		subtotal = 0.0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(Integer numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

}
