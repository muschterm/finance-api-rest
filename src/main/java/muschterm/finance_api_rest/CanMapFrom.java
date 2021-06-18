package muschterm.finance_api_rest;

public interface CanMapFrom<M, Entity> {

	M from(Entity from);

}
