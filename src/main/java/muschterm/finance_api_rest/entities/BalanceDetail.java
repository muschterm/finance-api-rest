package muschterm.finance_api_rest.entities;

import com.webcohesion.ofx4j.domain.data.common.BalanceInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import muschterm.finance_api_rest.dtos.BalanceDTO;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.ZoneId;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BalanceDetail {

	static final String COLUMN_AMOUNT = "amount";
	static final String COLUMN_AS_OF_DATE = "as_of_date";

	public BalanceDetail(BalanceInfo ofxBalanceInfo) {
		fromOfx(ofxBalanceInfo);
	}

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
		asOfDate = LocalDate.ofInstant(ofxBalanceInfo.getAsOfDate().toInstant(), ZoneId.systemDefault());

		return this;
	}

}
