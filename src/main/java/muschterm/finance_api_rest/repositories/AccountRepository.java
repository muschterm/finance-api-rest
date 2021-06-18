package muschterm.finance_api_rest.repositories;

import io.micronaut.data.jpa.repository.JpaRepository;
import muschterm.finance_api_rest.entities.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository<A extends Account>
	extends JpaRepository<A, String>,
	        JpaSpecificationExecutor<A> {

	Optional<A> findByNumber(String number);

}
