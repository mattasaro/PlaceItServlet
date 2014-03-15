package com.google.appengine.codelab;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * This class handles all the CRUD operations related to CategoryPlaceIt entity.
 * 
 */
public class CategoryPlaceIt {
	private static String KIND = "CategoryPlaceIt";
	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param cat1
	 * @param cat2
	 * @param cat3
	 */
	public static void createOrUpdate(int id, String title, String description,
			String cat1, String cat2, String cat3, boolean isCompleted, String user) {
		Entity placeIt = getPlaceIt(id);
		if (placeIt == null) {
			placeIt = new Entity(KIND, id);
		}

		placeIt.setProperty("title", title);
		placeIt.setProperty("description", description);
		placeIt.setProperty("cat1", cat1);
		placeIt.setProperty("cat2", cat2);
		placeIt.setProperty("cat3", cat3);
		placeIt.setProperty("isCompleted", isCompleted);
		placeIt.setProperty("user", user);

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
			return "CategoryPlaceIt deleted successfully";
		}

		return "No CategoryPlaceIts found with that id.";
	}
}
