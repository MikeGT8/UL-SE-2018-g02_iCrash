package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyCoordinatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyPersonImpl;

/**
 * The Person controller deals with any functions to do with persons in the system. This entails retrieval,
 * of data. It extends the AbstractController to handle checking of Dt information is correct
 */
public class PersonController extends AbstractUserController {
	
	/** Parameter that allows the system to gain server access, the server function lives in the model of the client and  has RMI calls to access the server. */
	private Server server = Server.getInstance();
	
	/**
	 * Instantiates a new person controller.
	 *
	 * @param aActPerson the a act person
	 * @throws RemoteException the remote exception
	 * @throws NotBoundException the not bound exception
	 */
	public PersonController(ActPerson aActPerson ) throws RemoteException, NotBoundException {
		
		super(new ActProxyPersonImpl(aActPerson));
	}
	
	/**
	 * Returns a list of all persons in the system.
	 *
	 * @return Returns an ArrayList of type CtPerson, which contains all persons currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	
	public ArrayList<CtPerson> getAllPersons() throws ServerOfflineException, ServerNotBoundException {
		
		try {
			return server.sys().getAllCtPersons();
			
		} catch (RemoteException e) {
			
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
			
		} catch (NotBoundException e) {
			
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Searches for a points of interest and shows it to the user. If not found, returns a message.
	 *
	 * @param name the PI name
	 * @param city the PI city
	 * @param category the PI category
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws IncorrectFormatException is thrown when a Dt information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeSearchPI(String name, String city, EtCategory category) throws ServerNotBoundException, ServerOfflineException, IncorrectFormatException {
		
		DtName aPIName = new DtName(new PtString(name));
		DtCity aPICity = new DtCity(new PtString(city));
		EtCategory aPICategory = category;
		
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aPIName, aPIName.value.getValue());
		ht.put(aPICity, aPICity.value.getValue());
		ht.put(aPICategory, aPICategory.toString());
		
		if (this.getUserType() == UserType.Person) {
			
			ActProxyPerson actPerson = (ActProxyPerson)this.getAuth();
			
			try {
				return actPerson.oeSearchPI(aPIName, aPICategory, aPICity);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * Sends a request for a new PI.
	 *
	 * @param PIID the PI id
	 * @param name the PI name
	 * @param city the PI city
	 * @param category the PI category
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws IncorrectFormatException is thrown when a Dt information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeSendNewRequest(String PIID, String name, String city, EtCategory category) throws ServerNotBoundException, ServerOfflineException, IncorrectFormatException {
		
		DtID aPIID = new DtID(new PtString(PIID));
		DtName aPIName = new DtName(new PtString(name));
		DtCity aPICity = new DtCity(new PtString(city));
		EtCategory aPICategory = category;
		
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aPIID, aPIID.value.getValue());
		ht.put(aPIName, aPIName.value.getValue());
		ht.put(aPICity, aPICity.value.getValue());
		ht.put(aPICategory, aPICategory.toString());
		
		if (this.getUserType() == UserType.Person) {
			
			ActProxyPerson actPerson = (ActProxyPerson)this.getAuth();
			
			try {
				return actPerson.oeSendNewRequest(aPIID, aPIName, aPICategory, aPICity);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * Asks for a GPS location of a specific point of interest.
	 *
	 * @param PIID the PI id
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws IncorrectFormatException is thrown when a Dt information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeGetGPSLocation(String PIID) throws ServerNotBoundException, ServerOfflineException, IncorrectFormatException {
		
		DtID aPIID = new DtID(new PtString(PIID));
		
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aPIID, aPIID.value.getValue());
		
		if (this.getUserType() == UserType.Person) {
			
			ActProxyPerson actPerson = (ActProxyPerson)this.getAuth();
			
			try {
				return actPerson.oeGetGPSLocation(aPIID);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * Asks for a description of a specific point of interest.
	 *
	 * @param PIID the PI id
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws IncorrectFormatException is thrown when a Dt information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeGetDescription(String PIID) throws ServerNotBoundException, ServerOfflineException, IncorrectFormatException {
		
		DtID aPIID = new DtID(new PtString(PIID));
		
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aPIID, aPIID.value.getValue());
		
		if (this.getUserType() == UserType.Person) {
			
			ActProxyPerson actPerson = (ActProxyPerson)this.getAuth();
			
			try {
				return actPerson.oeGetPIDescription(aPIID);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * Closes the server connection that is open at the moment.
	 */
	public void closeServerConnection(){
		server.disconnectServer();
	}
}
