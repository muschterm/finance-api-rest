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
		var creditCardAccount = creditCardAccountRepository
			.findByNumber(ofxCreditCardStatementResponse.getAccount().getAccountNumber())
			.orElse(null);
		if (creditCardAccount != null) {
			creditCardAccount = creditCardAccount.fromOfx(financialInstitution, ofxCreditCardStatementResponse);

			creditCardAccount = creditCardAccountRepository.update(creditCardAccount);
		}
		else {
			creditCardAccount = new CreditCardAccount(financialInstitution, ofxCreditCardStatementResponse);

			creditCardAccount = creditCardAccountRepository.save(creditCardAccount);
		}

		return creditCardAccount;
	}

}
