package unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dataAccess.*;
import services.Register;
import services.requests.RegisterRequest;
import services.responses.RegisterResponse;


public class RegisterAPITesting {
    @Test
    public void simpleAssertionTest() {
        assertEquals(200, 100+100);
        assertTrue(100 == 2 * 50);
        assertNotNull(new Object(), "Response did not ");
    }

    @Test
    public void registerSuccessCase() {
        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest(
                "John123", "connect", "john123@gmail.com");
        RegisterResponse registerResponse = registerService.register(registerRequest);

        assertEquals("John123", registerResponse.getUsername());
        assertNotNull(registerResponse.getAuthToken());
        assertNull(registerResponse.getMessage());
    }

    @Test
    public void registerFailureCase() {
        Register registerService = new Register();
        RegisterRequest registerRequest = new RegisterRequest(
                null, "connect", "john123@gmail.com");
        RegisterResponse registerResponse = registerService.register(registerRequest);

        assertNull(registerResponse.getUsername());
        assertNull(registerResponse.getAuthToken());
        assertNotNull(registerResponse.getMessage());
    }

}
