package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtRequest;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtRequestStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Server;

/**
 * The request controller deals with any functions to do with requests. This includes creation, retrieval,
 * handling and so on. It extends the AbstractController to handle checking of Dt information is correct
 */
public class RequestController {
	
	/** Parameter that allows the system to gain server access, the server function lives in the model of the client and  has RMI calls to access the server. */
	private Server server = Server.getInstance();
	
	/**
	 * Returns a list of all requests in the system, without using a logged in user.
	 *
	 * @return Returns an ArrayList of type CtRequest, which contains all requests currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	
	public ArrayList<CtRequest> getListOfRequests() throws ServerOfflineException, ServerNotBoundException{
		
		try {
			return server.sys().getAllCtRequests();
			
		} catch (RemoteException e) {
			
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerOfflineException();
			
		} catch (NotBoundException e) {
			
			Log4JUtils.getInstance().getLogger().error(e);
			throw new ServerNotBoundException();
		}
	}
	
	/**
	 * Returns a list of all requests in the system that match the status type specified, without using a logged in user.
	 *
	 * @param statusOfRequest the status of request
	 * @return Returns an ArrayList of type CtRequest, which contains all requests currently within the iCrashSystem
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 */
	public ArrayList<CtRequest> getListOfRequests(EtRequestStatus statusOfRequest) throws ServerOfflineException, ServerNotBoundException {
		
		return (ArrayList<CtRequest>)getListOfRequests().stream().filter(t -> t.status == statusOfRequest).collect(Collectors.toList());
	}	
	/**
	 * Closes the server connection that is open at the moment.
	 */
	public void closeServerConnection(){
		server.disconnectServer();
	}
}
