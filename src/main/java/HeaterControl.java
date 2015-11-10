
public class HeaterControl {

	private double tc;	
	private Sensor s1;
	private Sensor s2;
	private Sensor s3;
	private Boolean on;
	
	
	public HeaterControl(double tc, Sensor s1, Sensor s2, Sensor s3, Boolean on) {
		super();
		this.tc = tc;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.on = on;
	}
	

	public double getT1() {
		return s1.getT();
		
	}

	public double getT2() {
		return s2.getT();
	}
	
	public double getT3() {
		return s3.getT();
	}


	public double getTc() {
		return tc;
	}


	public void setTcx(double tc) {
		this.tc = tc;
	}


	public Boolean getOn() {
		return on;
	}

	

	public void step() {
		if (getTc() > ((getT1() + getT2() + getT3()) / 3))
			this.on = true;
		else
			this.on = false;
	}
	
	

}
