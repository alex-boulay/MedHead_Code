package dto;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterDTOTest {

	@Test
	public void testRegisterDTO() {
		RegisterDTO rd = new RegisterDTO("test","class");
		Assertions.assertEquals(rd.getUsername(), "test","Username doit être identique");
		Assertions.assertEquals(rd.getPassword(), "class","Password doit être identique");
		rd.setUsername("2ndtest");
		rd.setPassword("2ndpassword");
		Assertions.assertNotEquals(rd.getUsername(), "test","Username doit être différent");
		Assertions.assertNotEquals(rd.getPassword(), "class","Password doit être différent");
		Assertions.assertEquals(rd.getUsername(),"2ndtest","Username doit être identique n°2");
		Assertions.assertEquals(rd.getPassword(), "2ndpassword","Password doit être identique n°2");
		

		RegisterDTO rd2 = new RegisterDTO();
		rd2.setUsername("2ndtest");
		rd2.setPassword("2ndpassword");
		Assertions.assertNotEquals(rd2.getUsername(), "test","Username doit être différent");
		Assertions.assertNotEquals(rd2.getPassword(), "class","Password doit être différent");
		Assertions.assertEquals(rd2.getUsername(),"2ndtest","Username doit être identique n°2");
		Assertions.assertEquals(rd2.getPassword(), "2ndpassword","Password doit être identique n°2");
	}
}