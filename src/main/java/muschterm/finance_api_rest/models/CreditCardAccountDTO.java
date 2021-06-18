package muschterm.finance_api_rest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import muschterm.finance_api_rest.CanMapFrom;
import muschterm.finance_api_rest.CanMapTo;
import muschterm.finance_api_rest.entities.BalanceDetail;
import muschterm.finance_api_rest.entities.CreditCardAccount;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class CreditCardAccountDTO
	implements CanMapTo<CreditCardAccount>, CanMapFrom<CreditCardAccountDTO, CreditCardAccount> {

	private UUID id;
	private String name;
	private BalanceDTO availableBalance;
	private BalanceDTO ledgerBalance;

	public CreditCardAccount to(CreditCardAccount to) {
		to.setName(name);

		to.setId(id.toString());
		to.setAvailableBalance(availableBalance.to(new BalanceDetail()));
		to.setLedgerBalance(ledgerBalance.to(new BalanceDetail()));

		return to;
	}

	@Override
	public CreditCardAccountDTO from(CreditCardAccount from) {
		name = from.getName();

		id = UUID.fromString(from.getId());
		availableBalance = new BalanceDTO().from(from.getAvailableBalance());
		ledgerBalance = new BalanceDTO().from(from.getLedgerBalance());

		return this;
	}

}
