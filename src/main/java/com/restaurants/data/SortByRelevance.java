package com.restaurants.data;

import java.util.Comparator;

import com.restaurants.restaurantsearch.model.Restaurant;

public class SortByRelevance implements Comparator<Restaurant> {

	@Override
	public int compare(Restaurant o1, Restaurant o2) {
		if (o1.getDistance().compareTo(o2.getDistance()) != 0) {
			return o1.getDistance().subtract(o2.getDistance()).intValue();
		} else if (!(o1.getCustomerRating() == o2.getCustomerRating())) {
			return o2.getCustomerRating() - o1.getCustomerRating();
		} else if (!(o1.getPrice() == o2.getPrice())) {
			return o1.getPrice().intValue() - o2.getPrice().intValue();
		} else {
			return 0;
			// otherwise return either
		}
	}

}
