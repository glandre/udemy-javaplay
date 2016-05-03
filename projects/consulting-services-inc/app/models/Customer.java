package models;

import com.avaje.ebean.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;


@Entity 
@Table(name="customer")
public class Customer extends Model {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) 
    public Long id;

    @Constraints.Required(message = "required.message")
    public String name;

    public String address;

    @Constraints.Required(message = "required.message")
    public String city;

    @Constraints.Required(message = "required.message")
    public String state;

    @Constraints.Required(message = "required.message")
    public String postcode;

	@Constraints.Required(message = "required.message")
    public String phone;

    @Constraints.Required(message = "required.message")
    public String email;

    public static Finder<Long, Customer> find = new Finder<Long, Customer>(Customer.class);

    private static String EMAIL_PATTERN = ""; // TODO
    private static String PHONE_PATTERN = ""; // TODO

	public static List<Customer> findAll() {
		return find.orderBy("name").findList();
	}

	public static Customer retrieve(Long id) {
    	return find.byId(id.toString()); 
    }

    public static boolean exists(Long id) {
    	return (find.where().eq("id", id).findRowCount() == 1) ? true : false;
    }

    public static Map<String, String> options() {
    	Map<String, String> map = new LinkedHashMap<>();
    	List<Customer> list = findAll();
    	for(Customer customer : list) {
    		map.put(customer.id.toString(), customer.name);
    	}
    	return map;
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