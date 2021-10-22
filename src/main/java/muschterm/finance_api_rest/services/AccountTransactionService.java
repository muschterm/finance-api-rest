package muschterm.finance_api_rest.services;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import muschterm.finance_api_rest.entities.Account;
import muschterm.finance_api_rest.entities.AccountTransaction;
import muschterm.finance_api_rest.repositories.AccountTransactionRepository;

import javax.transaction.Transactional;

@Singleton
public class AccountTransactionService {

	private final AccountTransactionRepository transactionRepository;

	@Inject
	public AccountTransactionService(AccountTransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public void process(Transaction ofxTransaction, Account account) {
		var transaction = transactionRepository
			.findByTransactionId(ofxTransaction.getId())
			.orElse(null);
		if (transaction != null) {
			transaction = transaction.fromOfx(ofxTransaction);
			transaction.setAccount(account);

			transactionRepository.update(transaction);
		}
		else {
			transaction = new AccountTransaction().fromOfx(ofxTransaction);
			transaction.setAccount(account);

			transactionRepository.save(transaction);
		}
	}

}
