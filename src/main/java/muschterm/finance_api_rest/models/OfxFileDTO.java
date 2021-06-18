package muschterm.finance_api_rest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OfxFileDTO {

	private List<CreditCardAccountDTO> creditCardAccounts;

	public void addCreditCardAccount(CreditCardAccountDTO creditCardAccount) {
		if (creditCardAccounts == null) {
			creditCardAccounts = new ArrayList<>();
		}

		creditCardAccounts.add(creditCardAccount);
	}

}
