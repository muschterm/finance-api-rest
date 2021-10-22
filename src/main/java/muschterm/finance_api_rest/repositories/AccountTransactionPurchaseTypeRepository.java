package muschterm.finance_api_rest.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import muschterm.finance_api_rest.entities.AccountTransactionPurchaseType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

@Repository
public interface AccountTransactionPurchaseTypeRepository
	extends JpaRepository<AccountTransactionPurchaseType, Integer>,
	        JpaSpecificationExecutor<AccountTransactionPurchaseType> {
}
