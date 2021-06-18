package muschterm.finance_api_rest.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import muschterm.finance_api_rest.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AccountTransactionRepository
	extends JpaRepository<AccountTransaction, String>,
	        JpaSpecificationExecutor<AccountTransaction> {

	Optional<AccountTransaction> findByTransactionId(String transactionId);

}
