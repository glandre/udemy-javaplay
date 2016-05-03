package controllers;

import com.google.inject.Inject;
import constants.ModeConst;
import java.util.List;
import models.Service;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ServiceController extends Controller {
	private static Logger.ALogger logger = Logger.of(ServiceController.class);

	@Inject
	FormFactory formFactory;

	public Result list() {
		List<Service> allServices = Service.findAll();

	    // return ok(Json.toJson(allServices));
		return ok(views.html.services.list.render(allServices));
	}

	public Result addService() {
		Form<Service> serviceForm = formFactory.form(Service.class);
		return ok(
			views.html.services.info.render(serviceForm, ModeConst.ADD)
			);
	}

	public Result editService(String code) {
		Service service = Service.retrieve(code);
		Form<Service> filledForm = formFactory.form(Service.class).fill(service);
		return ok(
		    views.html.services.info.render(filledForm, ModeConst.EDIT + ": " + service.code)
		);
	}

	public Result save(String mode) {
		Form<Service> filledForm = formFactory.form(Service.class).bindFromRequest();
		if(filledForm.hasErrors()) {
			logger.debug("Erro em uma requisição [save]");
			return badRequest(views.html.services.info.render(filledForm, mode));
		}
		Service service = filledForm.get();
		if(service != null) {
			if(mode.equalsIgnoreCase(ModeConst.ADD)) {
				if(Service.exists(service.code)) {
					flash("errorsFound", "Código de serviço já existe");
					return badRequest(views.html.services.info.render(filledForm, mode));
				}
				service.save();
				return redirect( routes.ServiceController.addService() );
			}
			else if(mode.startsWith(ModeConst.EDIT)) {
				service.update();
				return redirect( routes.ServiceController.editService(service.code) );
			}
		}
		return badRequest(views.html.services.info.render(filledForm, mode));
	}

	public Result delete(String code) {
		Service service = Service.retrieve(code);
		if(service == null) {
			return notFound(code + " não consta no banco de dados.");
		}
		service.delete();
		return redirect( routes.ServiceController.list() );
	}

}