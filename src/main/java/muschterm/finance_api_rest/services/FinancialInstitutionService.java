package muschterm.finance_api_rest.services;

import com.webcohesion.ofx4j.domain.data.signon.SignonResponseMessageSet;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.repositories.FinancialInstitutionRepository;

import javax.transaction.Transactional;

@Singleton
public class FinancialInstitutionService {

	private static final String UNKNOWN_ID = "000000";
	private static final String UNKNOWN_ORGANIZATION = "Unknown";

	private final FinancialInstitutionRepository financialInstitutionRepository;

	@Inject
	public FinancialInstitutionService(
		FinancialInstitutionRepository financialInstitutionRepository
	) {
		this.financialInstitutionRepository = financialInstitutionRepository;
	}

	@Transactional
	public FinancialInstitution handleFinancialInstitution(
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
				new FinancialInstitution().fromOfx(ofxFinancialInstitution)
			);
		}

		return financialInstitution;
	}

}
