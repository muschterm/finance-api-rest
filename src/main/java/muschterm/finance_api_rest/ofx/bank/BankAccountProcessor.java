package muschterm.finance_api_rest.ofx.bank;

import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponse;
import muschterm.finance_api_rest.entities.BankAccount;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.repositories.BankAccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class BankAccountProcessor {

	private final BankAccountRepository bankAccountRepository;

	@Inject
	public BankAccountProcessor(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	public BankAccount process(
		FinancialInstitution financialInstitution,
		BankStatementResponse ofxBankStatementResponse
	) {
		var bankAccountDetail =
			bankAccountRepository.findByNumber(ofxBankStatementResponse.getAccount().getAccountNumber())
			                     .orElse(null);

		if (bankAccountDetail != null) {
			bankAccountDetail = bankAccountDetail.from(financialInstitution, ofxBankStatementResponse);

			bankAccountDetail = bankAccountRepository.update(bankAccountDetail);
		}
		else {
			bankAccountDetail = new BankAccount().from(financialInstitution, ofxBankStatementResponse);
			bankAccountDetail.setId(UUID.randomUUID().toString());

			bankAccountDetail = bankAccountRepository.save(bankAccountDetail);
		}

		return bankAccountDetail;
	}

}
