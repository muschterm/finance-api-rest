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

@Entity
@Table(
	indexes = {
		@Index(name = "IDXBankAccount_type", columnList = "type")
	}
)
@Getter
@Setter
public class BankAccount extends Account {

	@Column(length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String name;

	/**
	 * USA: Routing and transit number.
	 */
	@Column(length = 9, nullable = false)
	@NotNull
	@Size(max = 9)
	private String bankId;

	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Size(max = 20)
	private BankAccountType type;

	public BankAccount from(
		FinancialInstitution financialInstitution,
		BankStatementResponse ofxBankStatementResponse
	) {
		var accountDetails = ofxBankStatementResponse.getAccount();

		super.from(
			financialInstitution,
			accountDetails,
			ofxBankStatementResponse
		);

		bankId = accountDetails.getBankId();
		type = BankAccountType.lookup(accountDetails.getAccountType());

		name = String.format(
			"%s ...%s",
			type.getName(),
			number.substring(number.length() - 4)
		);

		return this;
	}

}
