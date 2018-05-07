package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;

/**
 * The Class DbRequests for updating and retrieving information from the table Requests in the database.
 */

public class DbRequests extends DbAbstract {
	
	/**
	 * Count the number of requests registered into the database.
	 *
	 */
	static public int countRequests() {

		int answer = 0; 
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			try {
				String sql = "SELECT COUNT(id)  AS numberOfRequestsl FROM "+ dbName + ".requests" ;

				Statement statement = conn.createStatement();
				ResultSet  res = statement.executeQuery(sql);

				if (res.next())
					answer = res.getInt("numberOfRequestsl");	

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
	 * Insert a request into the database.
	 *
	 * @param ctRequest the class of the request to insert
	 */
	static public void insertRequest(CtRequest ctRequest) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Insert

			try {
				Statement st = conn.createStatement();

				String id = ctRequest.id.value.getValue();
				String name = ctRequest.name.value.getValue();
				String city = ctRequest.city.value.getValue();
				String category = ctRequest.category.toString();
				String status = ctRequest.status.toString();
				Boolean ignored = ctRequest.ignored.getValue();

				log.debug("[DATABASE]-Insert request");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".requests"
						+ "(id,name,city,category,status,ignored)"
						+ "VALUES(" + "'" + id + "'" + ",'" + name + "', "
						+ city + ", " + category + ", '" + status + "','"
						+ ignored + "')");

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
	 * Gets a request from the database by the request ID.
	 *
	 * @param requestId The ID of the request to retrieve from the database
	 * @return ctRequest the class of the request to retrieve
	 */
	static public CtRequest getCtRequest(String requestId) {

		CtRequest ctRequest = new CtRequest();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".requests WHERE id = " + requestId;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				if (res.next()) {

					ctRequest = new CtRequest();
					//request's id
					DtRequestID aId = new DtRequestID(new PtString(res.getString("id")));

					//request's name  
					DtName aName = new DtName(new PtString(
							res.getString("name")));
					
					//request's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("city")));
					
					//request's category -> [supermarket,market,hobby,petrolstation,university,school]
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
					
					//request's status -> [pending, treated, solved]
					String theStatus = res.getString("status");
					EtRequestStatus aStatus = null;
					if (theStatus.equals(EtRequestStatus.pending.name()))
						aStatus = EtRequestStatus.pending;
					if (theStatus.equals(EtRequestStatus.treated.name()))
						aStatus = EtRequestStatus.treated;
					if (theStatus.equals(EtRequestStatus.solved.name()))
						aStatus = EtRequestStatus.solved;

					//request's ignored field 
					DtIgnored aIgnored = new DtIgnored(new Boolean(
							res.getBoolean("ignored")));

					ctRequest.init(aId, aName, aCity, aCategory, aStatus, aIgnored);
				}

			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return ctRequest;
	}

	/**
	 * Gets all of the requests currently in the database.
	 *
	 * @return a hashtable of the requests using the ID of the request as a key
	 */
	static public Hashtable<String, CtRequest> getSystemRequests() {

		Hashtable<String, CtRequest> cmpSystemCtRequest = new Hashtable<String, CtRequest>();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".requests ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtRequest ctRequest = null;

				while (res.next()) {

					ctRequest = new CtRequest();
					//request's id
					DtRequestID aId = new DtRequestID(new PtString(res.getString("id")));

					//request's name  
					DtName aName = new DtName(new PtString(
							res.getString("name")));
					
					//request's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("city")));
					
					//request's category -> [supermarket,market,hobby,petrolstation,university,school]
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
					
					//request's status -> [pending, treated, solved]
					String theStatus = res.getString("status");
					EtRequestStatus aStatus = null;
					if (theStatus.equals(EtRequestStatus.pending.name()))
						aStatus = EtRequestStatus.pending;
					if (theStatus.equals(EtRequestStatus.treated.name()))
						aStatus = EtRequestStatus.treated;
					if (theStatus.equals(EtRequestStatus.solved.name()))
						aStatus = EtRequestStatus.solved;

					//request's ignored field 
					DtIgnored aIgnored = new DtIgnored(new Boolean(
							res.getBoolean("ignored")));

					ctRequest.init(aId, aName, aCity, aCategory, aStatus, aIgnored);

					//add instance to the hash
					cmpSystemCtRequest.put(ctRequest.id.value.getValue(), ctRequest);

				}

			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return cmpSystemCtRequest;
	}

	/* Implement association methods */

	/**
	 * Deletes a request from the database, it will use the ID from the CtRequest to delete it.
	 *
	 * @param ctRequest The request to delete from the database
	 */
	static public void deleteRequest(CtRequest ctRequest) {

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Delete

			try {
				String sql = "DELETE FROM " + dbName + ".requests WHERE id = ?";
				String id = ctRequest.id.value.getValue();

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
