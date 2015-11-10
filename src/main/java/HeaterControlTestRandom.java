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

	@Test
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
}