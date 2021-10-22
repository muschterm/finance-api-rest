package muschterm.finance_api_rest.ofx;

import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.services.BankAccountService;

@Singleton
public class BankProcessor {

	private final BankAccountService bankAccountService;
	private final AccountStatementProcessor accountStatementProcessor;

	@Inject
	public BankProcessor(
		BankAccountService bankAccountService,
		AccountStatementProcessor accountStatementProcessor
	) {
		this.bankAccountService = bankAccountService;
		this.accountStatementProcessor = accountStatementProcessor;
	}

	public void process(
		FinancialInstitution financialInstitution,
		BankingResponseMessageSet bankingResponseMessageSet
	) {
		var bankStatementResponseTransactions = bankingResponseMessageSet.getStatementResponses();
		for (var bankStatementResponseTransaction : bankStatementResponseTransactions) {
			var bankStatementResponse = bankStatementResponseTransaction.getWrappedMessage();
			var bankAccount = bankAccountService.process(
				financialInstitution,
				bankStatementResponse
			);

			accountStatementProcessor.process(bankAccount, bankStatementResponse);
		}
	}

}
