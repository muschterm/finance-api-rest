package muschterm.finance_api_rest.ofx;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.creditcard.CreditCardResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.signon.SignonResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import lombok.extern.slf4j.Slf4j;
import muschterm.finance_api_rest.ofx.bank.BankProcessor;
import muschterm.finance_api_rest.ofx.creditcard.CreditCardProcessor;
import muschterm.finance_api_rest.ofx.signon.SignonProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;

@Singleton
@Slf4j
public record OfxHelper(
	SignonProcessor signonProcessor,
	BankProcessor bankProcessor,
	CreditCardProcessor creditCardProcessor
) {

	@Inject
	public OfxHelper {
	}

	public void handle(InputStream ofxInputStream) {
		AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<>(ResponseEnvelope.class);

		ResponseEnvelope responseEnvelope;
		try {
			responseEnvelope = unmarshaller.unmarshal(ofxInputStream);
		}
		catch (Exception e) {
			log.error("Couldn't parse OFX!", e);

			throw new UnsupportedOperationException(e);
		}

		var financialInstitution = signonProcessor.process(
			(SignonResponseMessageSet) responseEnvelope.getMessageSet(MessageSetType.signon)
		);

		var bankingResponseMessageSet = (BankingResponseMessageSet) responseEnvelope.getMessageSet(MessageSetType.banking);
		if (bankingResponseMessageSet != null) {
			bankProcessor.process(financialInstitution, bankingResponseMessageSet);
		}

		var creditCardResponseMessageSet = (CreditCardResponseMessageSet) responseEnvelope.getMessageSet(MessageSetType.creditcard);
		if (creditCardResponseMessageSet != null) {
			creditCardProcessor.process(financialInstitution, creditCardResponseMessageSet);
		}
	}

}
