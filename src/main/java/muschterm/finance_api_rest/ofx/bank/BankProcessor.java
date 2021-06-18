package muschterm.finance_api_rest.ofx.bank;

import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.ofx.AccountTransactionProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BankProcessor {

	private final BankAccountProcessor bankAccountProcessor;
	private final AccountTransactionProcessor transactionProcessor;

	@Inject
	public BankProcessor(
		BankAccountProcessor bankAccountProcessor,
		AccountTransactionProcessor transactionProcessor
	) {
		this.bankAccountProcessor = bankAccountProcessor;
		this.transactionProcessor = transactionProcessor;
	}

	public void process(
		FinancialInstitution financialInstitution,
		BankingResponseMessageSet bankingResponseMessageSet
	) {
		var bankStatementResponseTransactions = bankingResponseMessageSet.getStatementResponses();
		for (var bankStatementResponseTransaction : bankStatementResponseTransactions) {

			var bankStatementResponse = bankStatementResponseTransaction.getWrappedMessage();

			var bankAccount = bankAccountProcessor.process(financialInstitution, bankStatementResponse);

			transactionProcessor.process(bankAccount, bankStatementResponse);
		}
	}

}
