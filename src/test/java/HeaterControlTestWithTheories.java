import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assume;
import org.junit.Before;
import org.junit.contrib.theories.DataPoints;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@RunWith(Theories.class)
public class HeaterControlTestWithTheories {
	
	Sensor s1 = mock(Sensor.class);
	Sensor s2 = mock(Sensor.class);
	Sensor s3 = mock(Sensor.class);
	
	 
	HeaterControl hc  = new HeaterControl(12, s1, s2, s3, false);	
	
    double tempCapteur;
    double oldTemp=0;
    boolean oldState;
    @Before
    public void setUp(){
    	MockitoAnnotations.initMocks(this);
    }
    
	@DataPoints
	public static double[] some_temp() {
		//return new double[] { 16.1, 16.1, 16.62, 17, 18, 19, 20, 21, 22, 0, -23, 152 };
		return new double[] { 16.1, 26.1, 19.1};
	}
	
	@Theory
	public void hotter_implies_no_rising_edge(double tc, double t1, double t2,
			double t3) {
		oldState = hc.getOn();
		hc.setTcx(tc);
		when(s1.getT()).thenReturn(t1);
		when(s2.getT()).thenReturn(t2);
		when(s3.getT()).thenReturn(t3);
		 
	    tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
		
		hc.step();
		
		// si la temperature augmente mais reste superieure à la temperature de consigne
		// le chauffage reste etteinte
		Assume.assumeTrue(tempCapteur > oldTemp);
		Assume.assumeTrue(tempCapteur>tc);
		Assume.assumeTrue(oldState==false);		
		assertEquals(hc.getOn(), false);
		oldTemp = tempCapteur;
		
		System.out.printf("test theories 1 : tc=%f; t1=%f; t2=%f; t3=%f \n",tc, t1, t2, t3);
	}

	@Theory
	public void sensors_commute(double tc, double t1, double t2, double t3) {
		
		hc.setTcx(tc);
		when(s1.getT()).thenReturn(t1);
		when(s2.getT()).thenReturn(t2);
		when(s3.getT()).thenReturn(t3);
		 
	    tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
		
		hc.step();
		
		// on permutte les valeurs des capteurs pour voir si les test passes
		Assume.assumeTrue(tempCapteur>tc);
		assertEquals(hc.getOn(), false);
	    
		when(s1.getT()).thenReturn(t1);
		when(s2.getT()).thenReturn(t3);
		when(s3.getT()).thenReturn(t2);
		 
	    tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
		
		hc.step();
		
		// si la temperature augmente mais reste superieure à la temperature de consigne
		// le chauffage reste etteinte
		Assume.assumeTrue(tempCapteur>tc);
		assertEquals(hc.getOn(), false);
	
		when(s1.getT()).thenReturn(t2);
		when(s2.getT()).thenReturn(t1);
		when(s3.getT()).thenReturn(t3);
		 
	    tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
		
		hc.step();
		
		// si la temperature augmente mais reste superieure à la temperature de consigne
		// le chauffage reste etteinte
		Assume.assumeTrue(tempCapteur>tc);
		assertEquals(hc.getOn(), false);
		
		when(s1.getT()).thenReturn(t3);
		when(s2.getT()).thenReturn(t2);
		when(s3.getT()).thenReturn(t1);
		 
	    tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
		
		hc.step();
		
		// si la temperature augmente mais reste superieure à la temperature de consigne
		// le chauffage reste etteinte
		Assume.assumeTrue(tempCapteur>tc);
		assertEquals(hc.getOn(), false);
	
	
		System.out.printf("test theories 2: tc=%f; t1=%f; t2=%f; t3=%f \n", tc,
				t1, t2, t3);
	}
}