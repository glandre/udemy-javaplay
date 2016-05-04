package models;

import javax.persistence.MappedSuperClass;

@MappedSuperClass
public class InvoiceSearchForm {
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}