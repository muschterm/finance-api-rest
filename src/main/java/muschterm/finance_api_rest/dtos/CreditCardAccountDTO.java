package muschterm.finance_api_rest.dtos;

import muschterm.finance_api_rest.entities.CreditCardAccount;

import java.util.UUID;

public record CreditCardAccountDTO(
	UUID id,
	String name,
	BalanceDTO availableBalance,
	BalanceDTO ledgerBalance
) {

	public CreditCardAccountDTO(CreditCardAccount from) {
		this(
			UUID.fromString(from.getId()),
			from.getName(),
			new BalanceDTO(from.getAvailableBalance()),
			new BalanceDTO(from.getLedgerBalance())
		);
	}

}
