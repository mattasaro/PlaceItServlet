package com.google.appengine.codelab;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.Date;

/**
 * This class handles all the CRUD operations related to LocationPlaceIt entity.
 * 
 */
public class LocationPlaceIt {
	private static String KIND = "LocationPlaceIt";
	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param lat
	 * @param lon
	 * @param dueDate
	 * @param schedule
	 */
	public static void createOrUpdate(int id, String title, String description,
			double latitude, double longitude, Date dueDate, int schedule) {
		Entity placeIt = getPlaceIt(id);
		if (placeIt == null) {
			placeIt = new Entity(KIND, id);
		}

		placeIt.setProperty("title", title);
		placeIt.setProperty("description", description);
		placeIt.setProperty("latitude", latitude);
		placeIt.setProperty("longitude", longitude);
		placeIt.setProperty("dueDate", dueDate);
		placeIt.setProperty("schedule", schedule);

		Util.persistEntity(placeIt);
	}

	/**
	 * Return all the placeits
	 * 
	 * @param kind
	 *            : of kind product
	 * @return products
	 */
	public static Iterable<Entity> getAllPlaceIts() {
		return Util.listEntities(KIND, null, null);
	}

	/**
	 * Get PlaceIt entity
	 * 
	 * @param id
	 *            : id of the PlaceIt
	 * @return: PlaceIt entity
	 */
	public static Entity getPlaceIt(int id) {
		Key key = KeyFactory.createKey(KIND, id);
		return Util.findEntity(key);
	}

	/**
	 * Delete placeIt entity
	 * 
	 * @param id
	 *            : id of placeIt to be deleted
	 * @return status string
	 */
	public static String deletePlaceIt(int id) {
		Entity placeIt = getPlaceIt(id);

		if (placeIt != null) {
			Key key = KeyFactory.createKey(KIND, id);
			Util.deleteEntity(key);
			return "LocationPlaceIt deleted successfully";
		}

		return "No LocationPlaceIts found with that id.";
	}
}
