package muschterm.finance_api_rest.ofx.creditcard;

import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardResponseMessageSet;
import muschterm.finance_api_rest.entities.FinancialInstitution;
import muschterm.finance_api_rest.ofx.AccountTransactionProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreditCardProcessor {

	private final CreditCardAccountProcessor creditCardAccountProcessor;
	private final AccountTransactionProcessor transactionProcessor;

	@Inject
	public CreditCardProcessor(
		CreditCardAccountProcessor creditCardAccountProcessor,
		AccountTransactionProcessor transactionProcessor
	) {
		this.creditCardAccountProcessor = creditCardAccountProcessor;
		this.transactionProcessor = transactionProcessor;
	}

	public void process(
		FinancialInstitution financialInstitution,
		CreditCardResponseMessageSet creditCardResponseMessageSet
	) {
		var creditCardStatementResponseTransactions = creditCardResponseMessageSet.getStatementResponses();
		for (var creditCardStatementResponseTransaction : creditCardStatementResponseTransactions) {
			var creditCardStatementResponse = creditCardStatementResponseTransaction.getWrappedMessage();

			var creditCardAccount = creditCardAccountProcessor.process(
				financialInstitution,
				creditCardStatementResponse
			);

			transactionProcessor.process(creditCardAccount, creditCardStatementResponse);
		}
	}

}
