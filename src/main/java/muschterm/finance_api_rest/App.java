package muschterm.finance_api_rest;

import io.micronaut.runtime.Micronaut;
import org.h2.tools.Server;

public class App {

	public static void main(String[] args) throws Exception {
		Server.createWebServer().start();
		Micronaut.build(args)
		         .packages("muschterm.finance_api_rest2")
		         .mainClass(App.class)
		         .start();

		//run(App.class, args);
	}

}
