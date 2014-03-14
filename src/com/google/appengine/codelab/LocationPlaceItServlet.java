package com.google.appengine.codelab;

/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

/**
 * This servlet responds to the request corresponding to LocationPlaceIt
 * entities. The servlet manages the LocatioPlaceIt Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class LocationPlaceItServlet extends BaseServlet {

	private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
	private static final Logger logger = Logger
			.getLogger(LocationPlaceItServlet.class.getCanonicalName());

	/**
	 * Get the entities in JSON format.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		logger.log(Level.INFO, "Obtaining LocationPlaceIt listing");
		String searchFor = req.getParameter("q");
		PrintWriter out = resp.getWriter();
		Iterable<Entity> entities = null;
		if (searchFor == null || searchFor.equals("") || searchFor == "*") {
			entities = LocationPlaceIt.getAllPlaceIts();
			out.println(Util.writeJSON(entities));
		} else {
			Entity placeIt = LocationPlaceIt.getPlaceIt(Integer
					.parseInt(searchFor));
			if (placeIt != null) {
				Set<Entity> result = new HashSet<Entity>();
				result.add(placeIt);
				out.println(Util.writeJSON(result));
			}
		}
	}

	/**
	 * Create the entity and persist it.
	 */
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.log(Level.INFO, "Creating LocaitonPlaceIt");
		PrintWriter out = resp.getWriter();

		String pId = req.getParameter("id");
		String pTitle = req.getParameter("title");
		String pDescription = req.getParameter("description");
		String pDueDate = req.getParameter("dueDate");
		String pLatitude = req.getParameter("latitude");
		String pLongitude = req.getParameter("longitude");
		String pSchedule = req.getParameter("schedule");

		try {
			// Convert parameters to native types
			int id = Integer.parseInt(pId);
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
			Date dueDate = df.parse(pDueDate);
			Double lat = Double.parseDouble(pLatitude);
			Double lon = Double.parseDouble(pLongitude);
			int schedule = Integer.parseInt(pSchedule);

			LocationPlaceIt.createOrUpdate(id, pTitle, pDescription, lat, lon,
					dueDate, schedule);
		} catch (Exception e) {
			String msg = Util.getErrorMessage(e);
			out.print(msg);
		}
	}

	/**
	 * Delete the LocationPlaceIt entity
	 */
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String placeItKey = req.getParameter("id");
		PrintWriter out = resp.getWriter();
		try {
			int id = Integer.parseInt(placeItKey);
			out.println(LocationPlaceIt.deletePlaceIt(id));
		} catch (Exception e) {
			out.println(Util.getErrorMessage(e));
		}
	}

	/**
	 * Redirect the call to doDelete or doPut method
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equalsIgnoreCase("delete")) {
			doDelete(req, resp);
			return;
		} else if (action.equalsIgnoreCase("put")) {
			doPut(req, resp);
			return;
		}
	}
}
