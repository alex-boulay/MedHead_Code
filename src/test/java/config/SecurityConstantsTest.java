package config;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ocal.medhead.MedHeadApplication;

@SpringBootTest(classes = MedHeadApplication.class)
@ExtendWith(SpringExtension.class)
public class SecurityConstantsTest {
	@Test
	public void testSecurityConstants() {
		Assertions.assertTrue(SecurityConstants.JWT_EXPRITATION > 1500,"Low expiration Time" );
	}
}
