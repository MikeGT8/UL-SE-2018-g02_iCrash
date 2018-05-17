package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActProxyPerson that allows the client to access the server via RMI.
 */

public interface ActProxyPerson extends ActProxyAuthenticated {

	/**
	 * Search a PI from the system, using the parameters passed.
	 *
	 * @param aName The PI name to use when searching the PI
	 * @param aCategory The category to use when searching the PI
	 * @param aCity The city to use when searching the PI
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeSearchPI(DtName aPIName, EtCategory aPICategory, DtCity aPICity) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Send new request of a PI to the system, using the parameters passed.
	 *
	 * @param aName The PI name to use when requesting the PI
	 * @param aCategory The category to use when requesting the PI
	 * @param aCity The city to use when requesting the PI
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeSendNewRequest(DtName aPIName, EtCategory aPICategory, DtCity aPICity) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Get the gps location of a PI from the system, using the parameters passed.
	 *
	 * @param aPIID The ID to use when getting the gps location
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeGetGPSLocation(DtID aPIID) 
			throws RemoteException, NotBoundException;

	/**
	 * Get the description of a PI from the system, using the parameters passed.
	 *
	 * @param aPIID The ID to use when getting the description
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeGetPIDescription(DtID aPIID) 
			throws RemoteException, NotBoundException;
	
	/**
	 * A message sent from the server side saying the PI was added.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean iePIAdded() 
			throws RemoteException;
}
