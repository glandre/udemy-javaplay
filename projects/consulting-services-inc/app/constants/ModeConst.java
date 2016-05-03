package constants;

public enum ModeConst {
	ADD,
	EDIT;

	@Override
	public String toString() {
		switch(this) {
			case ADD: return "Adicionando";
			case EDIT: return "Editando";
			default: return super.toString();
		}
	}
}