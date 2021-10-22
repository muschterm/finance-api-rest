package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardStatementResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = CreditCardAccount.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCardAccount extends Account {

	static final String TABLE_NAME = "credit_card_account";

	@Override
	protected String shortName() {
		return "Credit Card";
	}

	public CreditCardAccount(
		FinancialInstitution financialInstitution,
		CreditCardStatementResponse ofxCreditCardStatementResponse
	) {
		fromOfx(
			financialInstitution,
			ofxCreditCardStatementResponse
		);
	}

	public CreditCardAccount fromOfx(
		FinancialInstitution financialInstitution,
		CreditCardStatementResponse ofxCreditCardStatementResponse
	) {
		super.fromOfx(
			financialInstitution,
			ofxCreditCardStatementResponse.getAccount(),
			ofxCreditCardStatementResponse
		);

		return this;
	}

}
