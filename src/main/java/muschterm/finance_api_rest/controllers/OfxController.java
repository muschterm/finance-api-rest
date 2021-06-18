package muschterm.finance_api_rest.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import muschterm.finance_api_rest.ofx.OfxHelper;
import muschterm.finance_api_rest.models.OfxFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller("/ofx")
public class OfxController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfxController.class);

	private final OfxHelper ofxHelper;

	@Inject
	public OfxController(OfxHelper ofxHelper) {
		this.ofxHelper = ofxHelper;
	}

	@Get("/file")
	public OfxFileDTO executeFromFile() throws IOException {
		ofxHelper.handle(Files.newInputStream(
			// System.getenv("HOME") + "/Dropbox/Documents/finances/applecard/2020-11.ofx"
			Paths.get(
				System.getenv("HOME"),
				"/Dropbox/Documents/finances/chasecard6126/Chase6126_Activity20190525_20210525_20210525.QFX"
			)));

		var ofxFile = new OfxFileDTO();


		return ofxFile;
	}

}
