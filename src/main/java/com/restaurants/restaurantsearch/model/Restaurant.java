package com.restaurants.restaurantsearch.model;

import java.math.BigDecimal;
import java.util.Comparator;

public class Restaurant implements Comparator<Restaurant> {
	private String name;
	private Integer customerRating;
	private BigDecimal distance;
	private BigDecimal price;
	private String cuisine;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCustomerRating() {
		return customerRating;
	}

	public void setCustomerRating(Integer customerRating) {
		this.customerRating = customerRating;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	@Override
	public int compare(Restaurant o1, Restaurant o2) {
		// TODO Auto-generated method stub
		return 0;
	}


}
