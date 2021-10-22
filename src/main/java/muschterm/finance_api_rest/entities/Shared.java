package muschterm.finance_api_rest.entities;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Version;
import java.time.Instant;

@Embeddable
@Getter
@Setter
public class Shared {

	static final String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	static final String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";

	@Column(name = COLUMN_CREATED_TIMESTAMP)
	@DateCreated
	private Instant createdTimestamp;

	@Column(name = COLUMN_UPDATED_TIMESTAMP)
	@DateUpdated
	private Instant updatedTimestamp;

}
