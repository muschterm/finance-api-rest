package muschterm.finance_api_rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import muschterm.finance_api_rest.dtos.OfxFileDTO;
import muschterm.finance_api_rest.ofx.OfxHelper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller("/ofx")
@Slf4j
public final class OfxController {

	private final OfxHelper ofxHelper;

	@Inject
	public OfxController(OfxHelper ofxHelper) {
		this.ofxHelper = ofxHelper;
	}

//	@Post(
//		value = "/file",
//		consumes = MediaType.MULTIPART_FORM_DATA,
//		produces = MediaType.APPLICATION_JSON
//	)
//	public HttpResponse<OfxFileDTO> executeFromFile(CompletedFileUpload ofxFile) throws IOException {

	@Get("/file")
	public HttpResponse<OfxFileDTO> executeFromFile() throws IOException {
		ofxHelper.handle(
//			ofxFile.getInputStream()
			Files.newInputStream(
				// System.getenv("HOME") + "/Dropbox/Documents/finances/applecard/2020-11.ofx"
				Paths.get(
					System.getenv("HOME"),
					"/Dropbox/Documents/finances/chasecard6126/Chase6126_Activity20190525_20210525_20210525.QFX"
				)
			)
		);

		var ofxFileDTO = new OfxFileDTO();

		return HttpResponse.created(ofxFileDTO);
	}

}
