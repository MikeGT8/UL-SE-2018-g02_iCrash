package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public class CtPI implements Serializable {
	
	private static final long serialVersionUID = 227L;

	public DtPIID id;
	
	public DtName name;
	
	public DtCity city;
	
	public EtCategory category;
	
	public DtGPSLocation location;
	
	public DtDescription description;

	/* Initialises the PI */
	public PtBoolean init(DtPIID aId, DtName aName, DtCity aCity, EtCategory aCategory, DtGPSLocation aLocation, DtDescription aDescription) {
			
		id = aId;
		name = aName;
		city = aCity;
		category = aCategory;
		location = aLocation;
		description = aDescription;
		
		return new PtBoolean(true);
	}
	
	@Override
	public String toString() {
		
		return this.id.value.getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof CtPI))
			return false;
		
		CtPI ctPI = (CtPI) obj;
		
		if (!(ctPI.id.value.getValue().equals(this.id.value.getValue())))
			return false;
		if (!(ctPI.name.toString().equals(this.name.toString())))
			return false;
		if (!(ctPI.city.toString().equals(this.city.toString())))
			return false;
		if (!(ctPI.category.equals(this.category)))
			return false;
		if (ctPI.location.latitude.value.getValue() != this.location.latitude.value.getValue()) 
			return false;
		if (ctPI.location.longitude.value.getValue() != this.location.longitude.value.getValue()) 
			return false;
		if (ctPI.description.value.getValue() != this.description.value.getValue()) 
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return this.id.value.getValue().length();
	}
}