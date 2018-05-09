package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.*;

public class CtRequest implements Serializable {
	
	private static final long serialVersionUID = 227L;

	public DtID id;
	
	public DtName name;
	
	public DtCity city;
	
	public EtCategory category;
	
	public EtRequestStatus status;
	
	public DtIgnored ignored;

	/* Initialises the request */
	public PtBoolean init(DtID aId, DtName aName, DtCity aCity, EtCategory aCategory, EtRequestStatus aStatus, DtIgnored aIgnored) {
			
		id = aId;
		name = aName;
		city = aCity;
		category = aCategory;
		status = aStatus;
		ignored = aIgnored;
		
		return new PtBoolean(true);
	}
	
	@Override
	public String toString() {
		
		return this.id.value.getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof CtRequest))
			return false;
		
		CtRequest ctRequest = (CtRequest) obj;
		
		if (!(ctRequest.id.value.getValue().equals(this.id.value.getValue())))
			return false;
		if (!(ctRequest.name.toString().equals(this.name.toString())))
			return false;
		if (!(ctRequest.city.toString().equals(this.city.toString())))
			return false;
		if (!(ctRequest.category.equals(this.category)))
			return false;
		if (!(ctRequest.status.equals(this.status)))
			return false;
		if (ctRequest.ignored.getValue() != this.ignored.getValue()) 
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return this.id.value.getValue().length();
	}
}