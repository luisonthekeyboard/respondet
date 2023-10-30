package rs.luis.respondet.lib;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskTest {

    private final Logger logger = LoggerFactory.getLogger(Respondet.class);
    private String randomString;
    private Request req;
    private Response response;
    private HttpResponse httpResponse;
    private MockedStatic<Request> requestMockedStatic;

    @BeforeEach
    void setUp() {
        randomString = UUID.randomUUID().toString();
        req = Mockito.mock(Request.class);
        response = Mockito.mock(Response.class);
        httpResponse = Mockito.mock(HttpResponse.class);
        requestMockedStatic = Mockito.mockStatic(Request.class);
    }

    @Test
    void HttpTaskHappyPath() {
        try {
            // Expectations
            requestMockedStatic.when(() -> Request.get(randomString)).thenReturn(req);
            Mockito.when(req.execute()).thenReturn(response);
            Mockito.when(response.returnResponse()).thenReturn(httpResponse);

            // Assertions
            HttpTask httpTask = new HttpTask(randomString);
            assertEquals(httpTask.call(), httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
            fail("Should not have thrown an exception at this point.");
        }
    }

    @Test
    void HttpTaskExecuteThrowsIOException() {

        try {
            // Expectations
            requestMockedStatic.when(() -> Request.get(randomString)).thenReturn(req);
            Mockito.when(req.execute()).thenThrow(new IOException());

            // Assertions
            HttpTask httpTask = new HttpTask(randomString);
            assertThrows(IOException.class, httpTask::call);
        } catch (IOException e) {
            logger.error(e.getMessage());
            fail("Exception should've been caught at this point.");
        }
    }

    @Test
    void HttpTaskReturnResponseThrowsIOException() {

        try {
            // Expectations
            requestMockedStatic.when(() -> Request.get(randomString)).thenReturn(req);
            Mockito.when(req.execute()).thenReturn(response);
            Mockito.when(response.returnResponse()).thenThrow(new IOException());

            // Assertions
            HttpTask httpTask = new HttpTask(randomString);
            assertThrows(IOException.class, httpTask::call);
        } catch (IOException e) {
            logger.error(e.getMessage());
            fail("Exception should've been caught at this point.");
        }
    }

    @AfterEach
    void tearDown() {
        requestMockedStatic.close();
    }
}