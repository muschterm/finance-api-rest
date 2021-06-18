package muschterm.finance_api_rest.ofx.creditcard;

import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardStatementResponse;
import muschterm.finance_api_rest.entities.CreditCardAccount;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.repositories.CreditCardAccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class CreditCardAccountProcessor {

	private final CreditCardAccountRepository creditCardAccountRepository;

	@Inject
	public CreditCardAccountProcessor(CreditCardAccountRepository creditCardAccountRepository) {
		this.creditCardAccountRepository = creditCardAccountRepository;
	}

	public CreditCardAccount process(
		FinancialInstitution financialInstitution,
		CreditCardStatementResponse ofxCreditCardStatementResponse
	) {
		var creditCardAccountDetail =
			creditCardAccountRepository.findByNumber(ofxCreditCardStatementResponse.getAccount()
			                                                                       .getAccountNumber())
			                           .orElse(null);
		if (creditCardAccountDetail != null) {
			creditCardAccountDetail = creditCardAccountDetail.from(
				financialInstitution,
				ofxCreditCardStatementResponse
			);

			creditCardAccountDetail = creditCardAccountRepository.update(creditCardAccountDetail);
		}
		else {
			creditCardAccountDetail = new CreditCardAccount().from(
				financialInstitution,
				ofxCreditCardStatementResponse
			);
			creditCardAccountDetail.setId(UUID.randomUUID().toString());

			creditCardAccountDetail = creditCardAccountRepository.save(creditCardAccountDetail);
		}

		return creditCardAccountDetail;
	}

}
