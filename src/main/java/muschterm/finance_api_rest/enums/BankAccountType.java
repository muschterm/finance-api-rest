package muschterm.finance_api_rest.enums;

import com.webcohesion.ofx4j.domain.data.banking.AccountType;

public enum BankAccountType {

	CHECKING(AccountType.CHECKING, "Checking"),
	SAVINGS(AccountType.SAVINGS, "Savings"),
	MONEYMRKT(AccountType.MONEYMRKT, "Money market"),
	CREDITLINE(AccountType.CREDITLINE, "Line of credit");

	// listed in the 2.2 spec 11.3.1.1
//	CD(AccountType.CD, "Certificate of Deposit")

	private final AccountType ofxAccountType;
	private final String name;

	BankAccountType(AccountType ofxAccountType, String name) {
		this.ofxAccountType = ofxAccountType;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static BankAccountType lookup(AccountType ofxAccountType) {
		for (var bankAccountType : values()) {
			if (bankAccountType.ofxAccountType == ofxAccountType) {
				return bankAccountType;
			}
		}

		throw new UnsupportedOperationException("OFX Type unknown: " + ofxAccountType);
	}
}
