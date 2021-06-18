package muschterm.finance_api_rest.entities;

import lombok.Getter;
import lombok.Setter;
import muschterm.finance_api_rest.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity(name = AccountTransaction.TABLE_NAME)
@Table(
	indexes = {
		@Index(name = "IDXAccountTransaction_account_id_transaction_id", columnList = "account_id, transaction_id DESC"),
		@Index(name = "IDXAccountTransaction_transaction_id", columnList = "transaction_id"),
		@Index(name = "IDXAccountTransaction_posted_date", columnList = "posted_date DESC"),
		@Index(name = "IDXAccountTransaction_amount", columnList = "amount DESC"),
		@Index(name = "IDXAccountTransaction_purchase_type_id", columnList = "purchase_type_id"),
		@Index(name = "IDXAccountTransaction_merchant_id", columnList = "merchant_id")
	}
)
@Getter
@Setter
public class AccountTransaction {

	static final String TABLE_NAME = "account_transaction";

	static final String COLUMN_ID = "id";
	static final String COLUMN_TRANSACTION_ID = "transaction_id";
	static final String COLUMN_ACCOUNT_ID = "account_id";
	static final String COLUMN_TYPE = "type";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_MEMO = "memo";
	static final String COLUMN_AMOUNT = "amount";
	static final String COLUMN_POSTED_DATE = "posted_date";

	// non ofx specific
	static final String COLUMN_PURCHASE_TYPE_ID = "purchase_type_id";
	static final String COLUMN_MERCHANT_ID = "merchant_id";
	static final String COLUMN_DESCRIPTION = "description";

	private static final String FK = "FK";
	private static final String FK_ACCOUNT_ID =
		FK +
		TABLE_NAME +
		"__" +
		COLUMN_ACCOUNT_ID;

	private static final String FK_PURCHASE_TYPE_ID =
		FK +
		TABLE_NAME +
		"__" +
		COLUMN_PURCHASE_TYPE_ID;

	private static final String FK_MERCHANT_ID =
		FK +
		TABLE_NAME +
		"__" +
		COLUMN_MERCHANT_ID;


	@Id
	@Column(name = COLUMN_ID, length = 40)
	@Size(max = 40)
	private String id;

	@Column(name = COLUMN_TRANSACTION_ID)
	@Size(max = 255)
	private String transactionId;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = COLUMN_ACCOUNT_ID,
		foreignKey = @ForeignKey(name = FK_ACCOUNT_ID)
	)
	private Account account;

	@Column(name = COLUMN_TYPE, length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Size(max = 20)
	private TransactionType type;

	@Column(name = COLUMN_NAME, length = 32, nullable = false)
	@NotNull
	@Size(max = 32)
	private String name;

	@Column(name = COLUMN_MEMO)
	@Size(max = 255)
	private String memo;

	@Column(name = COLUMN_AMOUNT, precision = 9, scale = 2, nullable = false)
	@NotNull
	private double amount;

	@Column(name = COLUMN_POSTED_DATE, nullable = false)
	@NotNull
	private Instant postedDate;

	// ****************
	// not ofx specific
	// ****************

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
		name = COLUMN_PURCHASE_TYPE_ID,
		foreignKey = @ForeignKey(name = FK_PURCHASE_TYPE_ID)
	)
	private AccountTransactionPurchaseType purchaseType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
		name = COLUMN_MERCHANT_ID,
		foreignKey = @ForeignKey(name = FK_MERCHANT_ID)
	)
	private AccountTransactionMerchant merchant;

	@Column(name = COLUMN_DESCRIPTION)
	@Size(max = 255)
	private String description;

	@Embedded
	private Shared shared;

	@Version
	private Integer version;

	public AccountTransaction from(
		Account account,
		com.webcohesion.ofx4j.domain.data.common.Transaction ofxTransaction
	) {
		this.account = account;

		transactionId = ofxTransaction.getId();
		type = TransactionType.lookup(ofxTransaction.getTransactionType());
		name = ofxTransaction.getName();
		memo = ofxTransaction.getMemo();
		amount = ofxTransaction.getAmount();
		postedDate = ofxTransaction.getDatePosted().toInstant();

		return this;
	}

	public static Specification<AccountTransaction> hasAccountId(String accountId) {
		return (root, cq, cb) -> cb.equal(root.get(COLUMN_ACCOUNT_ID), accountId);
	}

	public static Specification<AccountTransaction> betweenPostedDate(
		Instant start,
		Instant end
	) {
		return (root, cq, cb) -> cb.between(root.get(COLUMN_POSTED_DATE), start, end);
	}

	public static Specification<AccountTransaction> sincePostedDate(Instant since) {
		return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(COLUMN_POSTED_DATE), since);
	}

}
