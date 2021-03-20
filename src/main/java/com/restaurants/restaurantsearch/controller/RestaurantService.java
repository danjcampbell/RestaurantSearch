package com.restaurants.restaurantsearch.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurants.data.DataStore;
import com.restaurants.restaurantsearch.model.Restaurant;

@RestController
@RequestMapping("/restaurant/api")
public class RestaurantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

	@GetMapping(value = "/restaurants", produces = "application/json")
	private ResponseEntity<List<Restaurant>> getBestRestaurantMatch(@RequestParam() String name,
			@RequestParam Integer rating, @RequestParam BigDecimal distance, @RequestParam BigDecimal price,
			@RequestParam String cuisine) {
		try {
			List<Restaurant> restaurants = DataStore.getInstance().getRestaurants();
			List<Restaurant> results = new ArrayList<>();

			for (Restaurant restaurant : restaurants) {
				if (restaurant.getName().toLowerCase().contains(name.toLowerCase())
						&& restaurant.getCustomerRating() >= rating && //
						!(restaurant.getDistance().compareTo(distance) == 1)
						&& restaurant.getCuisine().toLowerCase().contains(cuisine.toLowerCase())
						&& !(restaurant.getPrice().compareTo(price) == 1)) {

					results.add(restaurant);
					if (results.size() == 5) {
						break;
					}
				}

			}
			LOGGER.info(results.size() + " matches found!");
			return ResponseEntity.ok().body(results);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getStackTrace(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
