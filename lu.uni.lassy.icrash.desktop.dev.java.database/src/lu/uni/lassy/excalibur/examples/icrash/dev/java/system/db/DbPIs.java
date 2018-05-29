package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;

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
				String sql = "SELECT COUNT(id)  AS numberOfPIsl FROM "+ dbName + ".pis" ;

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
				double latitude = ctPI.location.latitude.value.getValue();
				double longitude = ctPI.location.longitude.value.getValue();
				String description = ctPI.description.value.getValue();
				String category = ctPI.category.toString();

				log.debug("[DATABASE]-Insert PI");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".pis"
						+ "(id,name,city,latitude,longitude,description,category)"
						+ "VALUES(" + "'" + id + "'" + ",'" + name + "', '"
						+ city + "', " + latitude + ", " + longitude + ", '"
						+ description + "','" + category + "')");

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
				log.debug("[DATABASE]-Update pis");
				String sql = "UPDATE "
						+ dbName
						+ ".pis SET `name` = ?, `city` = ?, `latitude` = ?, `longitude` = ?,"
						+ " `description` = ?, `category` = ? WHERE id = ?";
				
				String id = ctPI.id.value.getValue();
				String name = ctPI.name.value.getValue();
				String city = ctPI.city.value.getValue();
				double latitude = ctPI.location.latitude.value.getValue();
				double longitude = ctPI.location.longitude.value.getValue();
				String description = ctPI.description.value.getValue();
				String category = ctPI.category.toString();

				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, name);
				statement.setString(2, city);
				statement.setDouble(3, latitude);
				statement.setDouble(4, longitude);
				statement.setString(5, description);
				statement.setString(6, category);
				statement.setString(7, id);
				
				int rows = statement.executeUpdate();
				log.debug(rows + " row affected");
				
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
				String sql = "SELECT * FROM " + dbName + ".pis WHERE id = " + aPIId;

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

					ctPI.init(aId, aName, aCity, aGPSLocation, aDescription, aCategory);
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
				String sql = "SELECT * FROM " + dbName + ".pis ";

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

					ctPI.init(aId, aName, aCity, aGPSLocation, aDescription, aCategory);

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
				String sql = "DELETE FROM " + dbName + ".pis WHERE id = ?";
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
	
	/**
	 * Gets PIs and their associated persons and inserts them into a hashtable, using the PI as a key.
	 *
	 * @return the hashtable of the associated persons and PIs
	 */
	static public Hashtable<CtPI, CtPerson> getAssCtPICtPerson() {

		Hashtable<CtPI, CtPerson> assCtPICtPerson = new Hashtable<CtPI, CtPerson>();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".pis "
						+ "INNER JOIN " + dbName + ".persons ON " + dbName
						+ ".pis.person = " + dbName + ".persons.phone";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtPI ctPI = null;
				CtPerson ctPerson = null;

				while (res.next()) {

					ctPI = new CtPI();
					//PI's id
					DtID aId = new DtID(new PtString(res.getString("pis.id")));

					//PI's name  
					DtName aName = new DtName(new PtString(
							res.getString("pis.name")));
					
					//PI's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("pis.city")));
					
					//PI's category -> [supermarket,market,hobby,petrolstation,university,school]
					String theCategory = res.getString("pis.category");
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
							res.getDouble("pis.latitude")));
					DtLongitude aLongitude = new DtLongitude(new PtReal(
							res.getDouble("pis.longitude")));
					DtGPSLocation aGPSLocation = new DtGPSLocation(
							aLatitude, aLongitude);

					//PI's description
					DtDescription aDescription = new DtDescription(new PtString(
							res.getString("pis.description")));

					//init ctPI instance
					ctPI.init(aId, aName, aCity, aGPSLocation, aDescription, aCategory);

					//*************************************
					
					ctPerson = new CtPerson();
					
					//person's id
					DtPhoneNumber aId1 = new DtPhoneNumber(new PtString(
							res.getString("phone")));
					
					//person's kind  -> [witness,victim,anonym]
					String theType = res.getString("type");
					EtHumanKind aType = null;
					
					if (theType.equals(EtHumanKind.witness.name()))
						aType = EtHumanKind.witness;
					if (theType.equals(EtHumanKind.victim.name()))
						aType = EtHumanKind.victim;
					if (theType.equals(EtHumanKind.anonym.name()))
						aType = EtHumanKind.anonym;

					ctPerson.init(aId1, aType);

					//add instances to the hash
					assCtPICtPerson.put(ctPI, ctPerson);
				}
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return assCtPICtPerson;
	}

static public void bindPIPerson(CtPI ctPI, CtPerson ctPerson) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".pis SET person =? WHERE id = ?";
				
				String id = ctPI.id.value.getValue();
				String personid = ctPerson.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, personid);
				statement.setString(2, id);
				int rows = statement.executeUpdate();
				
				log.debug(rows + " row affected");
				
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}
}
