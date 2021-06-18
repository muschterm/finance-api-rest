package muschterm.finance_api_rest.entities;

import io.micronaut.data.annotation.AutoPopulated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

@Entity
@Table(
	indexes = {
		@Index(name = "IDXAccountTransactionPurchaseType_name", columnList = "name"),
	}
)
@Getter
@Setter
public class AccountTransactionPurchaseType {

	@Id
	@AutoPopulated
	private int id;

	@Column(length = 100)
	@Size(max = 100)
	private String name;

	@Size(max = 255)
	private String description;

	@Embedded
	private Shared shared;

	@Version
	private Integer version;

	public static Specification<AccountTransactionPurchaseType> containsName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<AccountTransactionPurchaseType> startsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), name + "%");
	}

	public static Specification<AccountTransactionPurchaseType> endsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name);
	}

}
