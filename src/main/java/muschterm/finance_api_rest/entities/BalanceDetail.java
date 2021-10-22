package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.common.BalanceInfo;
import lombok.Getter;
import lombok.Setter;
import muschterm.finance_api_rest.dtos.BalanceDTO;
import muschterm.finance_api_rest.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
public class BalanceDetail {

	static final String COLUMN_AMOUNT = "amount";
	static final String COLUMN_AS_OF_DATE = "as_of_date";

	@Column(name = COLUMN_AMOUNT, precision = 13, scale = 2, nullable = false)
	private double amount;

	@Column(name = COLUMN_AS_OF_DATE, nullable = false)
	private LocalDate asOfDate;

	public BalanceDetail fromDto(BalanceDTO balanceDTO) {
		amount = balanceDTO.amount();
		asOfDate = balanceDTO.asOfDate();

		return this;
	}

	public BalanceDetail fromOfx(BalanceInfo ofxBalanceInfo) {
		amount = ofxBalanceInfo.getAmount();
		asOfDate = DateUtil.handleStaticDate(ofxBalanceInfo.getAsOfDate());

		return this;
	}

}
