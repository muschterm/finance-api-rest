package muschterm.finance_api_rest.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum TransactionType {

	CREDIT(
		"Credit",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.CREDIT
	),
	DEBIT(
		"Debit",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.DEBIT
	),
	PAYMENT(
		"Electronic payment",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.PAYMENT
	),
	REPEATING_PAYMENT(
		"Repeating payment",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.REPEATPMT
	),
	TRANSFER(
		"Transfer",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.XFER
	),
	CHECK(
		"Check",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.CHECK
	),
	CASH(
		"Cash",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.CASH
	),
	DIRECT_DEPOSIT(
		"Direct deposit",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.DIRECTDEP
	),
	INTEREST_EARNED(
		"Interest earned",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.INT
	),
	ATM(
		"ATM transaction",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.ATM
	),
	POS(
		"Point of sale",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.POS
	),
	FEE(
		"Fee",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.FEE
	),
	SERVICE_CHARGE(
		"Service charge",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.SRVCHG
	),
	OTHER(
		"Other",
		com.webcohesion.ofx4j.domain.data.common.TransactionType.OTHER
	),
	UNKNOWN(
		"Unknown",
		null
	);

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionType.class);

	private final String name;
	private final com.webcohesion.ofx4j.domain.data.common.TransactionType ofxType;

	TransactionType(
		String name,
		com.webcohesion.ofx4j.domain.data.common.TransactionType ofxType
	) {
		this.name = name;
		this.ofxType = ofxType;
	}

	public String getName() {
		return name;
	}

	public com.webcohesion.ofx4j.domain.data.common.TransactionType getOfxTransactionType() {
		return ofxType;
	}

	public static TransactionType lookup(com.webcohesion.ofx4j.domain.data.common.TransactionType ofxType) {
		for (TransactionType transactionType : values()) {
			if (transactionType.ofxType == ofxType) {
				return transactionType;
			}
		}

		LOGGER.error("Unknown Credit Card Transaction Type: " + ofxType);

		return UNKNOWN;
	}
}
