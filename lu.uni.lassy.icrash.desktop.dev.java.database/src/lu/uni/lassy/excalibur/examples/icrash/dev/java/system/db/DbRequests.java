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
					DtID aId = new DtID(new PtString(res.getString("id")));

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
					DtID aId = new DtID(new PtString(res.getString("id")));

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
	
	/** 
	 * Gets requests and their associated persons and inserts them into a hashtable, using the request as a key.
	 *
	 * @return the hashtable of the associated persons and requests
	 */
	static public Hashtable<CtRequest, CtCoordinator> getAssCtRequestCtCoordinator() {

		Hashtable<CtRequest, CtCoordinator> assCtRequestCtCoordinator = new Hashtable<CtRequest, CtCoordinator>();

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".requests "
						+ "INNER JOIN " + dbName + ".coordinators ON " + dbName
						+ ".requests.coordinator = " + dbName + ".coordinator.id";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtRequest ctRequest = null;
				CtCoordinator ctCoordinator = null;

				while (res.next()) {

					ctRequest = new CtRequest();
					//request's id
					DtID aId = new DtID(new PtString(res.getString("requests.id")));

					//request's name  
					DtName aName = new DtName(new PtString(
							res.getString("requests.name")));
					
					//request's city 
					DtCity aCity = new DtCity(new PtString(
							res.getString("requests.city")));
					
					//request's category -> [supermarket,market,hobby,petrolstation,university,school]
					String theCategory = res.getString("requests.category");
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
					String theStatus = res.getString("requests.status");
					EtRequestStatus aStatus = null;
					if (theStatus.equals(EtRequestStatus.pending.name()))
						aStatus = EtRequestStatus.pending;
					if (theStatus.equals(EtRequestStatus.treated.name()))
						aStatus = EtRequestStatus.treated;
					if (theStatus.equals(EtRequestStatus.solved.name()))
						aStatus = EtRequestStatus.solved;
					
					//PI's description
					DtIgnored anIgnored = new DtIgnored(new Boolean(
							res.getBoolean("requests.ignored")));

					//init ctRequest instance
					ctRequest.init(aId, aName, aCity, aCategory, aStatus, anIgnored);

					//*************************************
					
					ctCoordinator = new CtCoordinator();
					
					//coordinator's id
					DtCoordinatorID aId1 = new DtCoordinatorID(new PtString(
							res.getString("id")));
					
					//coordinator's login
					DtLogin aLogin = new DtLogin(new PtString(
							res.getString("login")));
					
					//coordinator's pwd
					DtPassword aPwd = new DtPassword(new PtString(
							res.getString("pwd")));
					
					//coordinator's accessRights
					EtCrisisType anAccessRights = EtCrisisType.small;
					switch(res.getString("accessRights")) {
						case "medium": anAccessRights = EtCrisisType.medium; break;
						case "high": anAccessRights = EtCrisisType.huge; break;
						default: break;
					}
					ctCoordinator.init(aId1, aLogin, aPwd, anAccessRights);

					//add instances to the hash
					assCtRequestCtCoordinator.put(ctRequest, ctCoordinator);
				}
			} catch (SQLException s) {
				
				log.error("SQL statement is not executed! " + s);
			}
			
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			
			logException(e);
		}

		return assCtRequestCtCoordinator;
	}

	/**
	 * Binds a person onto a PI in the database.
	 *
	 * @param ctPI The PI to bind the person to
	 * @param ctPerson The person to bind to the PI
	 */
	static public void bindPIPerson(CtPI ctPI, CtPerson ctPerson) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".PIs SET person =? WHERE id = ?";
				
				String id = ctPI.id.value.getValue();
				String personPhone = ctPerson.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, personPhone);
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
	
	/**
	 * Binds a coordinator onto an request in the database.
	 *
	 * @param ctRequest The request to bind the coordinator to
	 * @param ctCoordinator The coordinator to bind to the request
	 */
	static public void bindRequestCoordinator(CtRequest ctRequest, CtCoordinator ctCoordinator) {
		
		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Update

			try {
				String sql = "UPDATE " + dbName
						+ ".requests SET coordinator =? WHERE id = ?";
				
				String id = ctRequest.id.value.getValue();
				String coordid = ctCoordinator.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setString(1, coordid);
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
