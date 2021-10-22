package muschterm.finance_api_rest.repositories;

import io.micronaut.data.annotation.Repository;
import muschterm.finance_api_rest.entities.BankAccount;

@Repository
public interface BankAccountRepository extends AccountRepository<BankAccount> {
}
