import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HeaterControlTest {
	
	@Mock
	Sensor s1;
	@Mock
	Sensor s2;
	@Mock
	Sensor s3;	
	HeaterControl hc;
	
/*	@Before
	public void setUp(){
		
		when(s1.getT()).thenReturn(10.00);
		when(s2.getT()).thenReturn(5.00);
		when(s3.getT()).thenReturn(2.00);
		hc = new HeaterControl(25, s1, s2, s3, false);
		hc.step();
	}
	*/
	

	@Test
	public void ca_chauffe_sil_fait_froid() {
		when(s1.getT()).thenReturn(10.00);
		when(s2.getT()).thenReturn(5.00);
		when(s3.getT()).thenReturn(2.00);
		hc = new HeaterControl(25, s1, s2, s3, false);
		hc.step();
		assertEquals(hc.getOn(), true);
		System.out.printf("ca_chauffe_sil_fait_froid: ok.\n");

	}

	@Test
	public void ca_chauffe_pas_sil_fait_chaud() {
		when(s1.getT()).thenReturn(10.00);
		when(s2.getT()).thenReturn(35.00);
		when(s3.getT()).thenReturn(45.00);
		hc = new HeaterControl(25, s1, s2, s3, false);
		hc.step();
		assertEquals(hc.getOn(), false);
		System.out.printf("ca_chauffe_pas_sil_fait_chaud:  ok.\n");

	}
}