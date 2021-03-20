package com.restaurants.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restaurants.restaurantsearch.controller.RestaurantService;
import com.restaurants.restaurantsearch.model.Restaurant;

public class DataStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataStore.class);

	private static DataStore instance;
	private List<Restaurant> restaurants;

	private DataStore() {
	}

	synchronized public static DataStore getInstance() throws IOException {

		if (instance != null) {
			return instance;
		} else {
			instance = new DataStore();

		}
		return instance;
	}

	synchronized public List<Restaurant> getRestaurants() throws FileNotFoundException, IOException {
		if (restaurants == null) {
			Map<Integer, String> cuisineMap = initCuisineMap();
			restaurants = initRestuarants(cuisineMap);
		}
		return restaurants;
	}

	private List<Restaurant> initRestuarants(Map<Integer, String> cuisineMap)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/restaurants.csv")));
		List<String> restLineList = br.lines().collect(Collectors.toList());
		br.close();

		String header = restLineList.remove(0);
		String[] headers = header.split(",");
		Map<Integer, String> headerIndex = new HashMap<>();
		for (int i = 0; i < headers.length; i++) {
			headerIndex.put(i, headers[i]);
		}

		List<Restaurant> restaurants = new ArrayList<>();
		for (String restLine : restLineList) {
			String[] currentLine = restLine.split(",");
			Restaurant newRest = new Restaurant();
			for (int i = 0; i < currentLine.length; i++) {
				switch (headerIndex.get(i)) {
				case "name": {
					newRest.setName(currentLine[i].trim());
					break;
				}
				case "customer_rating": {
					newRest.setCustomerRating(Integer.parseInt(currentLine[i].trim()));
					break;
				}
				case "distance": {
					newRest.setDistance(new BigDecimal(currentLine[i].trim()));
					break;
				}
				case "price": {
					newRest.setPrice(new BigDecimal(currentLine[i].trim()));
					break;
				}
				case "cuisine_id": {
					newRest.setCuisine(cuisineMap.get(Integer.parseInt(currentLine[i].trim())));
					break;
				}
				}

			}
			restaurants.add(newRest);
		}
		Collections.sort(restaurants, new SortByRelevance());
		return restaurants;
	}

	private Map<Integer, String> initCuisineMap() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/cuisines.csv")));

		List<String> lineList = br.lines().collect(Collectors.toList());
		br.close();
		
		//remove header
		lineList.remove(0);
		
		Map<Integer, String> cuisineMap = new HashMap<>();
		for (String line : lineList) {
			String[] cuisineRef = line.split(",");
			if (cuisineRef.length == 2) {
				
				cuisineMap.put(Integer.parseInt(cuisineRef[0].trim()), cuisineRef[1].trim());
			}
		}
		return cuisineMap;
	}

}
