package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtRequestID extends DtString implements JIntIs {
	
	private static final long serialVersionUID = 227L;
	
	public DtRequestID(PtString s) {
		
		super(s);
	}
	
	private int _minLength = 0;
	
	private int _maxLength = 10;

	public PtBoolean is() {
		
		return new PtBoolean(this.value.getValue().length() > _minLength && this.value.getValue().length() <= _maxLength);
	}
}
