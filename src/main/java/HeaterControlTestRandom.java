import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class HeaterControlTestRandom {
	private Random r;


	@Mock
	Sensor s1;
	@Mock
	Sensor s2;
	@Mock
	Sensor s3;	
	HeaterControl hc;
	
	@Before
	public void setUp() {
		this.r = new Random();
		hc = new HeaterControl(21, s1, s2, s3, true);
	}

	//@Test
	public void test_alea1() {
		double t1, t2, t3;
		double oldTemp=0;
		double newTemp;
		boolean oldState;
		double[] t_list = new double[] { 16, 17, 18, 19, 20, 21, 22, 21,20,19,18 };

		int i=0;
		for (double t : t_list) {
			if(i==0)
				oldTemp = t;
			newTemp = t;
			oldState = hc.getOn();

			t1 = t + r.nextDouble() - 0.5;
			t2 = t + r.nextDouble() - 0.5;
			t3 = t + r.nextDouble() - 0.5;
			when(s1.getT()).thenReturn(t1);
			when(s2.getT()).thenReturn(t2);
			when(s3.getT()).thenReturn(t3);
			hc.step();

			if ((newTemp > oldTemp) && (newTemp < hc.getTc()))
				assertEquals(hc.getOn(), oldState);
			
			else if(newTemp > hc.getTc())
				assertEquals(hc.getOn(), !oldState); 

			System.out.printf("test alea 1: t=%f  \n", t);
			oldTemp = newTemp;
			i++;
		}
	}
	
	@Test
	public void test_alea2() {
		double tc = r.nextDouble() * 20.0 + 10.0; // tc est choisit dans [10;30[
		double t1, t2, t3,t;
		double oldTemp=0;
		double newTemp;
		double tempCapteur;
		hc = new HeaterControl(tc, s1, s2, s3, false);
		boolean oldState = true;
		
		for (int i = 0; i < 200; i++) {
			
			t = tc + r.nextDouble() - 0.5;
			t1 = t + r.nextDouble() - 0.5;
			t2 = t + r.nextDouble() - 0.5;
			t3 = t + r.nextDouble() - 0.5;
			when(s1.getT()).thenReturn(t1);
			when(s2.getT()).thenReturn(t2);
			when(s3.getT()).thenReturn(t3);
			tempCapteur = (s1.getT()+s2.getT()+s3.getT())/3;
			if(i==0)       
				oldTemp = tempCapteur;
			
			newTemp = tempCapteur;
			oldState = hc.getOn();
			
			hc.step();
			// si la temperature augmente mais reste inferieur à la temperature de consigne
			// le chauffage reste allumer
			if ((newTemp > oldTemp) && (newTemp < hc.getTc()) && oldState==true){    
				assertEquals(hc.getOn(), true);
			}
			// si la temperature augmente jusqu'a dépasser la temperature de consigne
			// alors le chauffage doit s'etteindre
			else if((newTemp > oldTemp) && (newTemp > hc.getTc()) && oldState==true){
			    
				assertEquals(hc.getOn(), false);
			}
			// si la temperature diminue mais reste superieure à la temperature de consigne
			// le chauffage reste etteinte
			else if((newTemp < oldTemp) && (newTemp > hc.getTc()) && oldState==false){
				assertEquals(hc.getOn(), false); 
			}
			// si la temperature diminue jusqu'a etre inferieure à la temperature de consigne
			// le chauffage se rallume
			else if((newTemp < oldTemp) && (newTemp < hc.getTc()) && oldState==false){
				assertEquals(hc.getOn(), true);  
			}
						
			System.out.printf("test alea 2: t=%f  \n", t);
			oldTemp = t; 
		}
	}   
}
