package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.*;

/**
 * The Class DbPIs for updating and retrieving information from the table PIs in the database.
 */

public class DbPIs extends DbAbstract {

	/**
	 * Count the number of PIs registered into the database.
	 *
	 */
	static public int countPIs() {

		int answer = 0; 
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			try {
				String sql = "SELECT COUNT(id)  AS numberOfPIsl FROM "+ dbName + ".PIs" ;

				Statement statement = conn.createStatement();
				ResultSet  res = statement.executeQuery(sql);

				if (res.next())
					answer = res.getInt("numberOfPIsl");	

			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! "+s);
			}
			
			conn.close();
			
		} catch (Exception e) {
			
			logException(e);
		}
		
		return answer;
	}

	/**
	 * Insert a PI into the database.
	 *
	 * @param ctPI the class of the PI to insert
	 */
	static public void insertPI(CtPI ctPI) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Insert

			try {
				Statement st = conn.createStatement();

				String id = ctPI.id.value.getValue();
				String name = ctPI.name.value.getValue();
				String city = ctPI.city.value.getValue();
				String category = ctPI.category.toString();
				String location = ctPI.location.toString();
				String description = ctPI.description.value.getValue();

				log.debug("[DATABASE]-Insert PI");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".PIs"
						+ "(id,name,city,category,location,description)"
						+ "VALUES(" + "'" + id + "'" + ",'" + name + "', "
						+ city + ", " + category + ", '" + location + "','"
						+ description + "')");

				log.debug(val + " row affected");
				
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}
	
	/**
	 * Update a PI in the database.
	 *
	 * @param ctPI the class of the PI to update
	 */
	
	static public void updatePI(CtPI ctPI) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				Statement st = conn.createStatement();

				String id = ctPI.id.value.getValue();
				String name = ctPI.name.value.getValue();
				String city = ctPI.city.value.getValue();
				String category = ctPI.category.toString();
				String location = ctPI.location.toString();
				String description = ctPI.description.value.getValue();

				log.debug("[DATABASE]-Update PI");
				int val = st.executeUpdate("Update INTO " + dbName + ".PIs"
						+ "(id,name,city,category,location,description)"
						+ "VALUES(" + "'" + id + "'" + ",'" + name + "', "
						+ city + ", " + category + ", '" + location + "','"
						+ description + "')");

				log.debug(val + " row updated");
				
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}

	/**
	 * Gets a PI from the database by the PI ID.
	 *
	 * @param aPIId The ID of the PI to retrieve from the database
	 * @return ctPI the class of the PI to retrieve
	 */
	static public CtPI getPI(String aPIId) {

		CtPI ctPI = new CtPI();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".requests WHERE id = " + aPIId;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {

					ctPI = new CtPI();
					//PI's id
					DtID aId = new DtID(new PtString(res.getString("id")));

					//PI's name  
					DtName aName = new DtName(new PtString(
							res.getString("name")));
					
					//PI's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("city")));
					
					//PI's category -> [supermarket,market,hobby,petrolstation,university,school]
					String theCategory = res.getString("category");
					EtCategory aCategory = null;
					if (theCategory.equals(EtCategory.supermarket.name()))
						aCategory = EtCategory.supermarket;
					if (theCategory.equals(EtCategory.market.name()))
						aCategory = EtCategory.market;
					if (theCategory.equals(EtCategory.hobby.name()))
						aCategory = EtCategory.hobby;
					if (theCategory.equals(EtCategory.petrolstation.name()))
						aCategory = EtCategory.petrolstation;
					if (theCategory.equals(EtCategory.university.name()))
						aCategory = EtCategory.university;
					if (theCategory.equals(EtCategory.school.name()))
						aCategory = EtCategory.school;
					
					//PI's location
					DtLatitude aLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aGPSLocation = new DtGPSLocation(
							aLatitude, aLongitude);

					//PI's description
					DtDescription aDescription = new DtDescription(new PtString(
							res.getString("description")));

					ctPI.init(aId, aName, aCity, aCategory, aGPSLocation, aDescription);
				}

			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return ctPI;
	}

	/**
	 * Gets all of the PIs currently in the database.
	 *
	 * @return a hashtable of the PIs using the ID of the PI as a key
	 */
	static public Hashtable<String, CtPI> getSystemPIs() {

		Hashtable<String, CtPI> cmpSystemCtPI = new Hashtable<String, CtPI>();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".PIs ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtPI ctPI = null;

				while (res.next()) {

					ctPI = new CtPI();
					//PI's id
					DtID aId = new DtID(new PtString(res.getString("id")));

					//PI's name  
					DtName aName = new DtName(new PtString(
							res.getString("name")));
					
					//PI's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("city")));
					
					//PI's category -> [supermarket,market,hobby,petrolstation,university,school]
					String theCategory = res.getString("category");
					EtCategory aCategory = null;
					if (theCategory.equals(EtCategory.supermarket.name()))
						aCategory = EtCategory.supermarket;
					if (theCategory.equals(EtCategory.market.name()))
						aCategory = EtCategory.market;
					if (theCategory.equals(EtCategory.hobby.name()))
						aCategory = EtCategory.hobby;
					if (theCategory.equals(EtCategory.petrolstation.name()))
						aCategory = EtCategory.petrolstation;
					if (theCategory.equals(EtCategory.university.name()))
						aCategory = EtCategory.university;
					if (theCategory.equals(EtCategory.school.name()))
						aCategory = EtCategory.school;
					
					//PI's location
					DtLatitude aLatitude = new DtLatitude(new PtReal(
							res.getDouble("latitude")));
					DtLongitude aLongitude = new DtLongitude(new PtReal(
							res.getDouble("longitude")));
					DtGPSLocation aGPSLocation = new DtGPSLocation(
							aLatitude, aLongitude);

					//PI's description
					DtDescription aDescription = new DtDescription(new PtString(
							res.getString("description")));

					ctPI.init(aId, aName, aCity, aCategory, aGPSLocation, aDescription);

					//add instance to the hash
					cmpSystemCtPI.put(ctPI.id.value.getValue(), ctPI);

				}

			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return cmpSystemCtPI;
	}

	/* Implement association methods */

	/**
	 * Deletes a PI from the database, it will use the ID from the CtPI to delete it.
	 *
	 * @param ctPI The PI to delete from the database
	 */
	static public void deletePI(CtPI ctPI) {

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Delete

			try {
				String sql = "DELETE FROM " + dbName + ".PIs WHERE id = ?";
				String id = ctPI.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row deleted");
				
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}

	/* Implement bind association methods */
}
