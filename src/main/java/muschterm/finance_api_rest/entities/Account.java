package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.common.AccountDetails;
import com.webcohesion.ofx4j.domain.data.common.StatementResponse;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity(name = Account.TABLE_NAME)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
	static final String COLUMN_NAME = "name";

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

	protected Account() {
	}

	protected Account(@NotNull String id) {
		this.id = id;
	}

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
	private String number;

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

	@Column(name = COLUMN_NAME, length = 100, nullable = false)
	@NotNull
	@Size(max = 100)
	private String name;

	static final String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	static final String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";

	@Column(name = COLUMN_CREATED_TIMESTAMP)
	@DateCreated
	private OffsetDateTime createdTimestamp;

	@Column(name = COLUMN_UPDATED_TIMESTAMP)
	@DateUpdated
	@Version
	private OffsetDateTime updatedTimestamp;

	protected abstract String shortName();

	public String shortAccountNumber() {
		return number.substring(number.length() - 4);
	}

	protected void fromOfx(
		FinancialInstitution financialInstitution,
		AccountDetails ofxAccountDetails,
		StatementResponse ofxStatementResponse
	) {
		this.financialInstitution = financialInstitution;
		number = ofxAccountDetails.getAccountNumber();

		var ofxAvailableBalance = ofxStatementResponse.getAvailableBalance();
		if (availableBalance == null) {
			availableBalance = new BalanceDetail(ofxAvailableBalance);
		}
		else {
			availableBalance.fromOfx(ofxAvailableBalance);
		}

		var ofxLedgerBalance = ofxStatementResponse.getLedgerBalance();
		if (ledgerBalance == null) {
			ledgerBalance = new BalanceDetail(ofxLedgerBalance);
		}
		else {
			ledgerBalance.fromOfx(ofxStatementResponse.getLedgerBalance());
		}

		name = String.format("%s ...%s", shortName(), shortAccountNumber());
	}

}
