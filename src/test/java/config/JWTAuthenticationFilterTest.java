package config ;

import com.ocal.medhead.MedHeadApplication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MedHeadApplication.class)
@ExtendWith(SpringExtension.class)
public class JWTAuthenticationFilterTest {

    @Mock
    private HttpServletRequest request;

    public JWTAuthenticationFilterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetJWTFromRequest() {
    	
        // Instanciation de la Classe
        JWTAuthenticationFilter jwtAuthentifactionFilter = new JWTAuthenticationFilter();

        // Test pour un Bearer "normal"
        when(request.getHeader("Authorization")).thenReturn("Bearer my-jwt-token");
        String token = jwtAuthentifactionFilter.getJWTFromRequest(request);
        assertEquals("my-jwt-token", token);

        // Test pour un Bearer null
        when(request.getHeader("Authorization")).thenReturn(null);
        token = jwtAuthentifactionFilter.getJWTFromRequest(request);
        assertNull(token);

        // Test pour un Bearer sans la chaine de caractère appropriée
        when(request.getHeader("Authorization")).thenReturn("my-jwt-token");
        token = jwtAuthentifactionFilter.getJWTFromRequest(request);
        assertNull(token);
    }
}