package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.common.BalanceInfo;
import lombok.Getter;
import lombok.Setter;
import muschterm.finance_api_rest.CanMapFrom;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@Setter
public class BalanceDetail implements CanMapFrom<BalanceDetail, BalanceInfo> {

	static final String COLUMN_AMOUNT = "amount";
	static final String COLUMN_AS_OF_DATE = "as_of_date";

	@Column(name = COLUMN_AMOUNT, precision = 13, scale = 2, nullable = false)
	private double amount;

	@Column(name = COLUMN_AS_OF_DATE, nullable = false)
	private Instant asOfDate;

	@Override
	public BalanceDetail from(BalanceInfo from) {
		amount = from.getAmount();
		asOfDate = from.getAsOfDate().toInstant();

		return this;
	}

}
