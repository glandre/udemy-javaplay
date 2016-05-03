package controllers;

import com.google.inject.Inject;
import java.util.List;
import models.Service;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Services extends Controller {

	@Inject
	FormFactory formFactory;

	private Form<Service> serviceForm;

	public Result list() {
		List<Service> allServices = Service.findAll();

	    // return ok(Json.toJson(allServices));
	    return ok(views.html.services.list.render(allServices));
	}

	public Result addService() {
	    serviceForm = formFactory.form(Service.class);
	    return ok(
	        views.html.services.info.render(serviceForm, "Adicionando")
	    );
	}

	// public Result save() {
	// 	Form<Service> filledForm = serviceForm.bindFromRequest();
	// 	Logger.debug("ERRORS: " + filledForm.errors().toString());
	// 	if(!filledForm.hasErrors()) {
	// 	    Service service = filledForm.get();
	// 	    if(Service.exists(service.code)) {
	// 	    	service.update();
	// 	    }
	// 	    else {
	// 	    	service.save();
	// 	    }
	//     	return redirect( routes.Services.addService() );
	// 	    // Alternativelly, the following would also work properly:
	// 	    // return redirect( "/services/new" );
	// 	}
	// 	else {
	// 		String mode = filledForm.data().get("mode");
	// 		Logger.debug("DATA: " + Json.toJson(filledForm));
	// 		// we have an error, show it on screen
	// 		flash("errorsFound", "Por favor corrija os erros na página");
	// 		return badRequest(views.html.services.info.render(filledForm, mode));
	// 	}
	// }

	public Result save(String mode) {
		Logger.debug("Mode is: " + mode);
		Form<Service> filledForm = serviceForm.bindFromRequest();
		Logger.debug("Errors: " + filledForm.errors().toString());
		if(!filledForm.hasErrors()) {
		    Service service = filledForm.get();
		    if(mode.equals("Adicionando")) {
		    	Logger.debug("CODE IS [" + service.code + "]");
		    	if(service.code.equals("") || Service.exists(service.code)) {
		    		flash("errorsFound", "Código vazio ou Código já existe");
		    		return badRequest(views.html.services.info.render(filledForm, "Adicionando"));
		    	}
			    // if processing gets here we can save
			    service.save();
			}
			else {
				Logger.debug("code: " + service.code + " amount: " + service.amount + " desc: " + service.description);
				service.update();
			}
	    	return redirect( routes.Services.addService() );
		    // Alternativelly, the following would also work properly:
		    // return redirect( "/services/new" );
		}
		else {
			flash("errorsFound", "Por favor, corrija os erros na página.");
			return badRequest(views.html.services.info.render(filledForm, mode));
		}
	}

	public Result info(String code) {
		Service service = Service.retrieve(code);

		if(service == null) {
			return notFound(code + " não consta no banco de dados.");
		}

		serviceForm = formFactory.form(Service.class);
		Form<Service> filledForm = serviceForm.fill(service);
		String mode = service.description;
	    return ok(
	        views.html.services.info.render(filledForm, mode)
	    );
	}

	public Result delete(String code) {
	    Service service = Service.retrieve(code);
	    if(service == null) {
	    	return notFound(code + " não consta no banco de dados.");
	    }
	    service.delete();
	    return redirect( routes.Services.list() );
	}

}