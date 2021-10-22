package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponse;
import lombok.Getter;
import lombok.Setter;
import muschterm.finance_api_rest.enums.BankAccountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = BankAccount.TABLE_NAME)
@Table(
	indexes = {
		@Index(name = "IDXBankAccount_type", columnList = "type")
	}
)
@Getter
@Setter
public class BankAccount extends Account {

	static final String TABLE_NAME = "bank_account";

	static final String COLUMN_BANK_ID = "bank_id";
	static final String COLUMN_TYPE = "type";

	/**
	 * USA: Routing and transit number.
	 */
	@Column(name = COLUMN_BANK_ID, length = 9, nullable = false)
	@NotNull
	@Size(max = 9)
	private String bankId;

	@Column(name = COLUMN_TYPE, length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Size(max = 20)
	private BankAccountType type;

	@Override
	protected String shortName() {
		return type.getName();
	}

	public BankAccount fromOfx(
		FinancialInstitution financialInstitution,
		BankStatementResponse ofxBankStatementResponse
	) {
		var ofxBankAccountDetails = ofxBankStatementResponse.getAccount();

		super.fromOfx(
			financialInstitution,
			ofxBankAccountDetails,
			ofxBankStatementResponse
		);

		bankId = ofxBankAccountDetails.getBankId();
		type = BankAccountType.lookup(ofxBankAccountDetails.getAccountType());

		return this;
	}

}
