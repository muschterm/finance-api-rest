package muschterm.finance_api_rest.ofx.signon;

import com.webcohesion.ofx4j.domain.data.signon.SignonResponseMessageSet;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.repositories.FinancialInstitutionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SignonProcessor {

	private static final String UNKNOWN_ID = "000000";
	private static final String UNKNOWN_ORGANIZATION = "Unknown";

	private final FinancialInstitutionRepository financialInstitutionRepository;

	@Inject
	public SignonProcessor(
		FinancialInstitutionRepository financialInstitutionRepository
	) {
		this.financialInstitutionRepository = financialInstitutionRepository;
	}

	public FinancialInstitution process(SignonResponseMessageSet signonResponseMessageSet) {
		FinancialInstitution financialInstitution = null;

		if (signonResponseMessageSet != null) {
			var signonResponse = signonResponseMessageSet.getSignonResponse();
			if (signonResponse != null) {
				var ofxFinancialInstitution = signonResponse.getFinancialInstitution();
				if (ofxFinancialInstitution != null) {
					financialInstitution = getOrUpdateFinancialInsitution(ofxFinancialInstitution);
				}
			}
		}

		// fallback to unknown
		if (financialInstitution == null) {
			var ofxFinancialInstitution = new com.webcohesion.ofx4j.domain.data.signon.FinancialInstitution();
			ofxFinancialInstitution.setId(UNKNOWN_ID);
			ofxFinancialInstitution.setOrganization(UNKNOWN_ORGANIZATION);

			financialInstitution = getOrUpdateFinancialInsitution(ofxFinancialInstitution);
		}

		return financialInstitution;
	}


	private FinancialInstitution getOrUpdateFinancialInsitution(
		com.webcohesion.ofx4j.domain.data.signon.FinancialInstitution ofxFinancialInstitution
	) {
		var financialInstitution = financialInstitutionRepository
			.findById(ofxFinancialInstitution.getId())
			.orElse(null);
		if (financialInstitution != null) {
			financialInstitution = financialInstitutionRepository.update(
				financialInstitution.fromOfx(ofxFinancialInstitution)
			);
		}
		else {
			financialInstitution = financialInstitutionRepository.save(
				FinancialInstitution.create(ofxFinancialInstitution)
			);
		}

		return financialInstitution;
	}

}
