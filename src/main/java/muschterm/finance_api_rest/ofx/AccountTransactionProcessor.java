package muschterm.finance_api_rest.ofx;

import com.webcohesion.ofx4j.domain.data.common.StatementResponse;
import muschterm.finance_api_rest.entities.Account;
import muschterm.finance_api_rest.entities.AccountTransaction;
import muschterm.finance_api_rest.repositories.AccountTransactionRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class AccountTransactionProcessor {

	private final AccountTransactionRepository transactionRepository;

	@Inject
	public AccountTransactionProcessor(AccountTransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void process(
		Account account,
		StatementResponse ofxStatementResponse
	) {
		var ofxTransactionList = ofxStatementResponse.getTransactionList();
		if (ofxTransactionList != null) {
			var ofxTransactions = ofxTransactionList.getTransactions();
			if (ofxTransactions != null) {
				for (var ofxTransaction : ofxTransactions) {
					var transaction =
						transactionRepository.findByTransactionId(ofxTransaction.getId())
						                     .orElse(null);
					if (transaction != null) {
						transaction = transaction.from(account, ofxTransaction);
						transactionRepository.update(transaction);
					}
					else {
						transaction = new AccountTransaction().from(account, ofxTransaction);
						transaction.setId(UUID.randomUUID().toString());
						transactionRepository.save(transaction);
					}
				}
			}
		}
	}

}
