package muschterm.finance_api_rest.dtos;

import muschterm.finance_api_rest.entities.BankAccount;
import muschterm.finance_api_rest.enums.BankAccountType;

import java.util.UUID;

public record BankAccountDTO(
	UUID id,
	BankAccountType type,
	String name,
	BalanceDTO availableBalance,
	BalanceDTO ledgerBalance
) {

	public BankAccountDTO(BankAccount bankAccount) {
		this(
			UUID.fromString(bankAccount.getId()),
			bankAccount.getType(),
			bankAccount.getName(),
			new BalanceDTO(bankAccount.getAvailableBalance()),
			new BalanceDTO(bankAccount.getLedgerBalance())
		);
	}

}
