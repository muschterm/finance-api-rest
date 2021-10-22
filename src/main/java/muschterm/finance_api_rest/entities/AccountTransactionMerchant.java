package muschterm.finance_api_rest.entities;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "IDXAccountTransactionMerchant_name", columnList = "name"),
		@Index(name = "IDXAccountTransactionMerchant_category_id", columnList = "category_id"),
	}
)
@Getter
@Setter
public class AccountTransactionMerchant {

	@Id
	@AutoPopulated
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
		name = "category_id",
		foreignKey = @ForeignKey(name = "FKAccountTransactionMerchant_category_id")
	)
	private AccountTransactionCategory category;

	@Column(length = 100)
	@Size(max = 100)
	private String name;

	@Size(max = 255)
	private String description;

	static final String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	static final String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";

	@Column(name = COLUMN_CREATED_TIMESTAMP)
	@DateCreated
	private OffsetDateTime createdTimestamp;

	@Column(name = COLUMN_UPDATED_TIMESTAMP)
	@DateUpdated
	@Version
	private OffsetDateTime updatedTimestamp;

	public static Specification<AccountTransactionMerchant> containsName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<AccountTransactionMerchant> startsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), name + "%");
	}

	public static Specification<AccountTransactionMerchant> endsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name);
	}

}
