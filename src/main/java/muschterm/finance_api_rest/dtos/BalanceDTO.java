package muschterm.finance_api_rest.dtos;

import muschterm.finance_api_rest.entities.BalanceDetail;

import java.time.LocalDate;

public record BalanceDTO(
	double amount,
	LocalDate asOfDate
) {

	public BalanceDTO(BalanceDetail balanceDetail) {
		this(
			balanceDetail.getAmount(),
			balanceDetail.getAsOfDate()
		);
	}

}
