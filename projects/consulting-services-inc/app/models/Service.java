package models;

import com.avaje.ebean.Model;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;

@Entity
public class Service extends Model {
    @Id
    public String code;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public double amount;

    private static Model.Finder<String, Service> find = new Model.Finder<>(Service.class);

    public static List<Service> findAll() {
    	return Service.find.orderBy("code").findList();
    }

    public static Service retrieve(String code) {
    	// return Service.find.byId(code); // this would retrieve the whole object
    	
    	// this would retrieve only a reference, and than, lazy retrieve the object when needed
    	return Service.find.byId(code); 
    }

    public static boolean exists(String code) {
    	return (find.where().eq("code", code).findRowCount() == 1) ? true : false;
    }

}
