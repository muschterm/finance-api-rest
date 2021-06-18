package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardStatementResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class CreditCardAccount extends Account {

	@Column(length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String name;

	public CreditCardAccount from(
		FinancialInstitution financialInstitution,
		CreditCardStatementResponse ofxCreditCardStatementResponse
	) {
		var accountDetails = ofxCreditCardStatementResponse.getAccount();

		super.from(
			financialInstitution,
			accountDetails,
			ofxCreditCardStatementResponse
		);

		name = String.format("Credit Card ...%s", number.substring(number.length() - 4));

		return this;
	}

}
