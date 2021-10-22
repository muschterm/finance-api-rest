package muschterm.finance_api_rest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import muschterm.finance_api_rest.dtos.OfxFileDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@MicronautTest
class FinanceApiRestTest {

	@Inject
	EmbeddedServer server;

	@Inject
	@Client("/")
	HttpClient client;

	@Test
	void testItWorks() throws IOException {
		Assertions.assertTrue(server.isRunning());

		var requestBody = MultipartBody
			.builder()
			.addPart(
				"ofxFile",
				"Chase6126_Activity20190525_20210525_20210525.QFX",
				MediaType.TEXT_PLAIN_TYPE,
				Files.newInputStream(
					// System.getenv("HOME") + "/Dropbox/Documents/finances/applecard/2020-11.ofx"
					Paths.get(
						System.getenv("HOME"),
						"/Dropbox/Documents/finances/chasecard6126/Chase6126_Activity20190525_20210525_20210525.QFX"
					)
				),
				8476
			).build();

		var response = client.toBlocking().exchange(
			HttpRequest
				.POST(
					"/ofx/file",
					requestBody
				)
				.contentType(MediaType.MULTIPART_FORM_DATA_TYPE),
			OfxFileDTO.class
		);

		System.out.println(response);
	}

}
