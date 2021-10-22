package muschterm.finance_api_rest.services;

import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardStatementResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import muschterm.finance_api_rest.entities.CreditCardAccount;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.repositories.CreditCardAccountRepository;

import javax.transaction.Transactional;

@Singleton
public class CreditCardAccountService {

	private final CreditCardAccountRepository creditCardAccountRepository;

	@Inject
	public CreditCardAccountService(CreditCardAccountRepository creditCardAccountRepository) {
		this.creditCardAccountRepository = creditCardAccountRepository;
	}

	@Transactional
	public CreditCardAccount process(
		FinancialInstitution financialInstitution,
		CreditCardStatementResponse ofxCreditCardStatementResponse
	) {
		var creditCardAccount = creditCardAccountRepository
			.findByNumber(ofxCreditCardStatementResponse.getAccount().getAccountNumber())
			.orElse(null);
		if (creditCardAccount != null) {
			creditCardAccount = creditCardAccountRepository.update(
				creditCardAccount.fromOfx(financialInstitution, ofxCreditCardStatementResponse)
			);
		}
		else {
			creditCardAccount = creditCardAccountRepository.save(
				new CreditCardAccount().fromOfx(financialInstitution, ofxCreditCardStatementResponse)
			);
		}

		return creditCardAccount;
	}

}
