package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public enum EtCategory implements JIntIs {

	supermarket,
	market,
	hobby,
	petrolstation,
	university,
	school;
	
	public PtBoolean is() {
		
		return new PtBoolean(this.name() == EtCategory.supermarket.name() ||
				this.name() == EtCategory.market.name() ||
				this.name() == EtCategory.hobby.name() ||
				this.name() == EtCategory.petrolstation.name() ||
				this.name() == EtCategory.university.name() ||
				this.name() == EtCategory.school.name());
	}
}
