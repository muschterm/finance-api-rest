package muschterm.finance_api_rest.dtos;

import java.util.ArrayList;
import java.util.List;

public record OfxFileDTO(List<CreditCardAccountDTO> creditCardAccounts) {

	public OfxFileDTO() {
		this(new ArrayList<>());
	}

}