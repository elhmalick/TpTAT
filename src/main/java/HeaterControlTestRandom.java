import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
		// XXX
	}

	@Test
	public void test_alea1() {
		double t1, t2, t3;
		// XXX
		double[] t_list = new double[] { 16, 17, 18, 19, 20, 21, 22, 21, 20,19, 18, 17, 16 };
		for (double t : t_list) {
			t1 = t + r.nextDouble() - 0.5;
			t2 = t + r.nextDouble() - 0.5;
			t3 = t + r.nextDouble() - 0.5;
			when(s1.getT()).thenReturn(t1);
			when(s2.getT()).thenReturn(t2);
			when(s3.getT()).thenReturn(t3);
			hc = new HeaterControl(25, s1, s2, s3, false);
			hc.step();
			assertEquals(hc.getOn(), true);
			System.out.printf("test alea 1: t=%f  \n", t);
		}
	}
}