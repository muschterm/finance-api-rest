package muschterm.finance_api_rest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import muschterm.finance_api_rest.models.OfxFileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

@MicronautTest
class FinanceApiRestTest {

	@Inject
	EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(server.isRunning());

        var response = client.toBlocking().retrieve(HttpRequest.GET("/ofx/file"), OfxFileDTO.class);

        System.out.println(response);
    }

}
