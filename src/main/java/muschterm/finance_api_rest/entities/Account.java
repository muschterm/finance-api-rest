package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.common.StatementResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = Account.TABLE_NAME)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
	indexes = {
		@Index(name = Account.IDX_NUMBER, columnList = Account.COLUMN_NUMBER),
		@Index(name = Account.IDX_FINANCIAL_INSTITUTION_ID, columnList = Account.COLUMN_FINANCIAL_INSTITUTION_ID)
	}
)
@Getter
@Setter
public abstract class Account {

	static final String TABLE_NAME = "account";

	static final String COLUMN_ID = "id";
	static final String COLUMN_FINANCIAL_INSTITUTION_ID = "financial_institution_id";
	static final String COLUMN_NUMBER = "number";
	private static final String LEDGER_BALANCE = "ledger_balance";
	static final String COLUMN_LEDGER_BALANCE_AMOUNT = LEDGER_BALANCE + "_" + BalanceDetail.COLUMN_AMOUNT;
	static final String COLUMN_LEDGER_BALANCE_AS_OF_DATE = LEDGER_BALANCE + "_" + BalanceDetail.COLUMN_AS_OF_DATE;
	private static final String AVAILABLE_BALANCE = "available_balance";
	static final String COLUMN_AVAILABLE_BALANCE_AMOUNT = AVAILABLE_BALANCE + "_" + BalanceDetail.COLUMN_AMOUNT;
	static final String COLUMN_AVAILABLE_BALANCE_AS_OF_DATE = AVAILABLE_BALANCE + "_" + BalanceDetail.COLUMN_AS_OF_DATE;

	private static final String FK = "FK";
	private static final String IDX = "IDX";
	private static final String FK_FINANCIAL_INSTITUTION_ID =
		FK +
		TABLE_NAME +
		"__" +
		COLUMN_FINANCIAL_INSTITUTION_ID;
	static final String IDX_FINANCIAL_INSTITUTION_ID =
		IDX +
		TABLE_NAME +
		"__" +
		COLUMN_FINANCIAL_INSTITUTION_ID;
	static final String IDX_NUMBER =
		IDX +
		TABLE_NAME +
		"__" +
		COLUMN_NUMBER;

	@Id
	@Column(name = COLUMN_ID, length = 40)
	@Size(max = 40)
	protected String id;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = COLUMN_FINANCIAL_INSTITUTION_ID,
		foreignKey = @ForeignKey(name = FK_FINANCIAL_INSTITUTION_ID)
	)
	@NotNull
	private FinancialInstitution financialInstitution;

	@Column(name = COLUMN_NUMBER, length = 22, nullable = false)
	@Size(max = 22)
	protected String number;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(
			name = "amount",
			column = @Column(name = COLUMN_AVAILABLE_BALANCE_AMOUNT)
		),
		@AttributeOverride(
			name = "asOfDate",
			column = @Column(name = COLUMN_AVAILABLE_BALANCE_AS_OF_DATE)
		)
	})
	private BalanceDetail availableBalance;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(
			name = "amount",
			column = @Column(name = COLUMN_LEDGER_BALANCE_AMOUNT)
		),
		@AttributeOverride(
			name = "asOfDate",
			column = @Column(name = COLUMN_LEDGER_BALANCE_AS_OF_DATE)
		)
	})
	private BalanceDetail ledgerBalance;

	@Embedded
	private Shared shared;

	@Version
	private Integer version;

	public Account from(
		FinancialInstitution financialInstitution,
		com.webcohesion.ofx4j.domain.data.common.AccountDetails accountDetails,
		StatementResponse ofxStatementResponse
	) {
		this.financialInstitution = financialInstitution;
		number = accountDetails.getAccountNumber();

		if (availableBalance == null) {
			availableBalance = new BalanceDetail();
		}

		availableBalance.from(ofxStatementResponse.getAvailableBalance());

		if (ledgerBalance == null) {
			ledgerBalance = new BalanceDetail();
		}

		ledgerBalance.from(ofxStatementResponse.getLedgerBalance());

		return this;
	}

}
