package muschterm.finance_api_rest.entities;

import io.micronaut.data.annotation.DateCreated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class FinancialInstitution {

	@Id
	@Column(length = 32)
	@Size(max = 32)
	private String id;

	@Column(length = 32)
	@Size(max = 32)
	private String organization;

	@DateCreated
	private OffsetDateTime createdTimestamp;

	@Version
	private OffsetDateTime updatedTimestamp;

	public FinancialInstitution fromOfx(
		com.webcohesion.ofx4j.domain.data.signon.FinancialInstitution ofxFinancialInstitution
	) {
		id = ofxFinancialInstitution.getId();
		organization = ofxFinancialInstitution.getOrganization();

		return this;
	}

}
