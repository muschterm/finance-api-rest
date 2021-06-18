package muschterm.finance_api_rest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muschterm.finance_api_rest.CanMapFrom;
import muschterm.finance_api_rest.CanMapTo;
import muschterm.finance_api_rest.entities.BalanceDetail;
import muschterm.finance_api_rest.entities.BankAccount;
import muschterm.finance_api_rest.enums.BankAccountType;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class BankAccountDTO
	implements CanMapTo<BankAccount>, CanMapFrom<BankAccountDTO, BankAccount> {

	private UUID id;
	private BankAccountType type;
	private String name;
	private BalanceDTO availableBalance;
	private BalanceDTO ledgerBalance;

	public BankAccount to(BankAccount to) {
		to.setName(name);
		to.setType(type);

		to.setId(id.toString());
		to.setAvailableBalance(availableBalance.to(new BalanceDetail()));
		to.setLedgerBalance(ledgerBalance.to(new BalanceDetail()));

		return to;
	}

	@Override
	public BankAccountDTO from(BankAccount from) {
		name = from.getName();
		type = from.getType();

		id = UUID.fromString(from.getId());
		availableBalance = new BalanceDTO().from(from.getAvailableBalance());
		ledgerBalance = new BalanceDTO().from(from.getLedgerBalance());

		return this;
	}

}
