package muschterm.finance_api_rest.entities;

import io.micronaut.data.annotation.AutoPopulated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(
	indexes = {
		@Index(name = "IDXAccountTransactionCategory_name", columnList = "name"),
	}
)
@Getter
@Setter
public class AccountTransactionCategory {

	@Id
	@AutoPopulated
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	private AccountTransactionCategory parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<AccountTransactionCategory> children;

	@Column(length = 100)
	@Size(max = 100)
	private String name;

	@Size(max = 255)
	private String description;

	@Embedded
	private Shared shared;

	@Version
	private Integer version;

	public void setParent(AccountTransactionCategory parent) {
		if (this.parent != null) {
			// remove from children of old parent
			this.parent.children.removeIf(accountTransactionCategory ->
				accountTransactionCategory.id == this.id
			);
		}

		this.parent = parent;
		this.parent.children.add(this);
	}

	public static Specification<AccountTransactionCategory> containsName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%");
	}

	public static Specification<AccountTransactionCategory> startsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), name + "%");
	}

	public static Specification<AccountTransactionCategory> endsWithName(String name) {
		return (root, cq, cb) -> cb.like(root.get("name"), "%" + name);
	}

}
