package muschterm.finance_api_rest.ofx;

import com.webcohesion.ofx4j.domain.data.signon.SignonResponseMessageSet;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.services.FinancialInstitutionService;

@Singleton
public class SignonProcessor {

	private static final String UNKNOWN_ID = "000000";
	private static final String UNKNOWN_ORGANIZATION = "Unknown";

	private final FinancialInstitutionService financialInstitutionService;

	@Inject
	public SignonProcessor(
		FinancialInstitutionService financialInstitutionService
	) {
		this.financialInstitutionService = financialInstitutionService;
	}

	public FinancialInstitution process(SignonResponseMessageSet signonResponseMessageSet) {
		FinancialInstitution financialInstitution = null;

		if (signonResponseMessageSet != null) {
			var signonResponse = signonResponseMessageSet.getSignonResponse();
			if (signonResponse != null) {
				var ofxFinancialInstitution = signonResponse.getFinancialInstitution();
				if (ofxFinancialInstitution != null) {
					financialInstitution = financialInstitutionService.handleFinancialInstitution(
						ofxFinancialInstitution);
				}
			}
		}

		// fallback to unknown
		if (financialInstitution == null) {
			var ofxFinancialInstitution = new com.webcohesion.ofx4j.domain.data.signon.FinancialInstitution();
			ofxFinancialInstitution.setId(UNKNOWN_ID);
			ofxFinancialInstitution.setOrganization(UNKNOWN_ORGANIZATION);

			financialInstitution = financialInstitutionService.handleFinancialInstitution(ofxFinancialInstitution);
		}

		return financialInstitution;
	}

}
