package muschterm.finance_api_rest.repositories;

import io.micronaut.data.annotation.Repository;
import muschterm.finance_api_rest.entities.CreditCardAccount;

@Repository
public interface CreditCardAccountRepository extends AccountRepository<CreditCardAccount> {
}
