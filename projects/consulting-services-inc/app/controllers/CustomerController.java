package controllers;

import com.google.inject.Inject;
import constants.ModeConst;
import java.util.List;
import models.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;
import java.util.List;

import views.html.customer.*;

public class CustomerController extends Controller {
	private static Logger.ALogger logger = Logger.of(CustomerController.class);

	@Inject
	FormFactory formFactory;

	public Result list() {
		List<Customer> customerList = Customer.findAll();
		return ok(
		    list.render(customerList)
		);
	}

	public Result addCustomer() {
		Form<Customer> form = formFactory.form(Customer.class);

	    return ok(
	        info.render(form, ModeConst.ADD)
	    );
	}

	public Result editCustomer(Long id) {
		Customer customer = Customer.retrieve(id);
		Form<Customer> form = formFactory.form(MyForm.class).fill(customer);
	    return ok(
	        info.render(form, ModeConst.EDIT + ": " + customer.name)
	    );
	}

	public Result save(String mode) {

		Form<Customer> form = formFactory.form(MyForm.class).bindFromRequest();
		if(form.hasErrors()) {
		    logger.debug("Validação falhou");
		    return badRequest(
		    	info.render(form, mode)
		    );
		} else {
		    
		}		

		Customer customer = form.get();
		if(customer != null) {
			if(mode.equals(ModeConst.ADD)) {
				customer.save();
				return redirect( routes.CustomerController.addCustomer() );
			}
			else if(mode.startsWith(ModeConst.EDIT)) {
				customer.update();
				return redirect( routes.CustomerController.editCustomer(customer.id) );
			}
		}

	    return badRequest(
	        info.render(form, mode)
	    );
	}

	public Result delete(Long id) {
		Customer customer = Customer.retrieve(id);
		if(customer == null) {
			return notFound(id + " não consta no banco de dados.");
		}
		customer.delete();
		return redirect( routes.CustomerController.list() );
	}

}