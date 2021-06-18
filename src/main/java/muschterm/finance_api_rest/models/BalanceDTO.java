package muschterm.finance_api_rest.models;

import lombok.Getter;
import lombok.Setter;
import muschterm.finance_api_rest.CanMapFrom;
import muschterm.finance_api_rest.CanMapTo;
import muschterm.finance_api_rest.entities.BalanceDetail;

import java.time.Instant;

@Getter
@Setter
public class BalanceDTO implements CanMapTo<BalanceDetail>, CanMapFrom<BalanceDTO, BalanceDetail> {

	private double amount;
	private Instant asOfDate;

	public BalanceDetail to(BalanceDetail to) {
		to.setAmount(amount);
		to.setAsOfDate(asOfDate);

		return to;
	}

	public BalanceDTO from(BalanceDetail from) {
		this.setAmount(from.getAmount());
		this.setAsOfDate(from.getAsOfDate());

		return this;
	}

}
