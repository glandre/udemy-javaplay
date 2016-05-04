package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;


@Entity 
@Table(name="invoice")
public class Invoice extends Model {

    @Id
    public Long id;

    public String invoiceId;

    public setId(Long id) {
        this.id = id;
        tihs.invoiceId = id.toString();
    }

    @Constraints.Required(message = "select.message")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn("customer_id")
    public Customer customer;

    @Constraints.Required(message = "required.message")
    @Temporal(TemporalType.DATE)
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date date;

    public static Finder<String, Invoice> find = new Finder<>(Invoice.class);

    public static List<Invoice> findAll() {
		return find.orderBy("name").findList();
	}

	public static Invoice retrieve(Long id) {
    	return find.byId(id.toString()); 
    }

    public static boolean exists(Long id) {
    	return (find.where().eq("id", id).findRowCount() == 1) ? true : false;
    }

    public static List<Invoice> findAll() {
        return find.fetch("customer").orderBy("id").findList();
    }

    public static List<Invoice> findAll(String pattern) {
        return find.fetch("customer").where().or(
            Expr.ilike("invoiceId", "%" + pattern + "%"),
            Expr.ilike("customer.name", "%" + pattern + "%")
        ).findList();
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        if(!phone.matches(PHONE_PATTERN)) {
        	errors.add(new ValidationError("phone", "Número de telefone inválido. Use o formato (999) 999-9999"));
        }

        if(!email.matches(EMAIL_PATTERN)) {
        	errors.add(new ValidationError("email", "E-mail inválido"));
        }

        return errors.isEmpty() ? null : errors;
    }
}