package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompanyImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class used to update or retrieve information from the persons tables in the database.
 */
public class DbPersons extends DbAbstract{

	/**
	 * Insert person into the database.
	 *
	 * @param ctPerson The CtPerson of which to use the information to insert into the database
	 * @param comCompany The communication company that is associated with the human
	 */
	static public void insertPerson(CtPerson ctPerson, String comCompany){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
				
				String phone = ctPerson.id.value.getValue();
				String type = ctPerson.type.toString();
	
				log.debug("[DATABASE]-Insert Person");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".persons" +
											"(phone,type,comcompany)" + 
											"VALUES("+"'"+phone+"','"+type+"','"+comCompany+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s) {
				
				log.error("SQL statement is not executed! "+s);
			}
	
			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}
	
	/**
	 * Gets a person details from the database, using the phone number to retrieve the data.
	 *
	 * @param phone The phone number to use to get the data from the database
	 * @return The person data that is retrieved from the database. This could be empty
	 */
	
	static public CtPerson getPerson(String phone){
		
		CtPerson ctPerson = new CtPerson();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".persons WHERE phone = " + phone;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) {				
					
					ctPerson = new CtPerson();
					//Person's id
					DtPhoneNumber aId = new DtPhoneNumber(new PtString(res.getString("phone")));
					//Person's type  -> [witness,victim,anonym]
					String theType = res.getString("type");
					EtHumanKind aType = null;
					if(theType.equals(EtHumanKind.witness.name()))
						aType = EtHumanKind.witness;
					if(theType.equals(EtHumanKind.victim.name()))
						aType = EtHumanKind.victim;
					if(theType.equals(EtHumanKind.anonym.name()))
						aType = EtHumanKind.anonym;

					ctPerson.init(aId,aType);
				}
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
		
		return ctPerson;

	}
	
	/**
	 * Gets all persons from the database.
	 *
	 * @return A hashtable of the persons using their phone number as a key value
	 */
	
	static public Hashtable<String, CtPerson> getSystemPersons(){
	
		Hashtable<String, CtPerson> cmpSystemCtPerson = new Hashtable<String, CtPerson>();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");
		

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".persons ";
				
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				CtPerson ctPerson = null;
				
				while(res.next()) {				
					
					ctPerson = new CtPerson();
					
					//Person's id
					DtPhoneNumber aId = new DtPhoneNumber(new PtString(res.getString("phone")));
					//Person's type  -> [witness,victim,anonym]
					String theType = res.getString("type");
					EtHumanKind aType = null;
					if(theType.equals(EtHumanKind.witness.name()))
						aType = EtHumanKind.witness;
					if(theType.equals(EtHumanKind.victim.name()))
						aType = EtHumanKind.victim;
					if(theType.equals(EtHumanKind.anonym.name()))
						aType = EtHumanKind.anonym;

					ctPerson.init(aId,aType);
					
					//add instance to the hash
					cmpSystemCtPerson.put(ctPerson.id.value.getValue(), ctPerson);
				}								
			}
			catch (SQLException s) {
				
				log.error("SQL statement is not executed! "+s);
			}
			
			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
		
		return cmpSystemCtPerson;
	}
	
	/* Implement associations for Person */
	
	/**
	 * Deletes a person from the database.
	 *
	 * @param ctPerson The person to delete from the database, it will use the ID/phone number to delete the person
	 */
	static public void deletePerson(CtPerson ctPerson) {
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".persons WHERE phone = ?";
				String id = ctPerson.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			
			logException(e);
		}
	}
	
	/* Implement bind association methods */
}