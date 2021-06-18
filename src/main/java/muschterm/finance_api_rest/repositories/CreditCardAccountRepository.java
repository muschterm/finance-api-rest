package muschterm.finance_api_rest.repositories;

import io.micronaut.data.annotation.Repository;
import muschterm.finance_api_rest.entities.CreditCardAccount;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CreditCardAccountRepository extends AccountRepository<CreditCardAccount> {
}
