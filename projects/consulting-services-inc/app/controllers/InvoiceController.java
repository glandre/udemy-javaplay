package controllers;

import com.google.inject.Inject;
import constants.ModeConst;
import java.util.List;
import models.Invoice;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;
import java.util.List;

import views.html.invoices.*;

public class InvoiceController extends Controller {
	private static Logger.ALogger logger = Logger.of(InvoiceController.class);

	@Inject
	FormFactory formFactory;

	public Result list() {
		List<Invoice> invoiceList = Invoice.findAll();
		Form<InvoiceSearchForm> myForm = formFactory.form(InvoiceSearchForm.class);
		return ok(
		    list.render(invoiceList, myForm)
		);
	}

	public Result search() {
		Form<InvoiceSearchForm> myForm = formFactory.form(InvoiceSearchForm.class).bindFromRequest();
		if(form.hasErrors()) {
		    List<Invoice> invoiceList = Invoice.findAll();
		    return badRequest(
		    	list.render(invoiceList, myForm)
		    );
		}

		InvoiceSearchForm search = myForm.get();
		if(search.name != null && search.name.length() > 0) {
			List<Invoice> invoiceList = Invoice.findAll(search.name);
			return ok(
				list.render(invoiceList, myForm)
			);
		}
		List<Invoice> invoiceList = Invoice.findAll();
		return ok(
			list.render(invoiceList, myForm)
		);
	}

	public Result addInvoice() {
		Form<Invoice> form = formFactory.form(Invoice.class);

	    return ok(
	        info.render(form, ModeConst.ADD)
	    );
	}

	public Result save(String mode) {

		Form<Invoice> form = formFactory.form(Invoice.class).bindFromRequest();
		if(form.hasErrors()) {
		    return badRequest(
		    	info.render(form, mode)
		    );
		}	

		Invoice invoice = form.get();
		if(invoice != null) {
			if(mode.equals(ModeConst.ADD)) {
				invoice.save();
				// TODO: testar se o setId está sendo chamado e preenchendo esse campo automaticamente:
				//invoice.invoicdId = invoice.id.toString(); 

				// once the invoice has the Id, edit the invoiceId field and persist it
				invoice.update();

				return redirect( routes.InvoiceController.editInvoice(invoice.id) );
			}
			else if(mode.startsWith(ModeConst.EDIT)) {
				invoice.update();
				// TODO: testar se o setId está sendo chamado e preenchendo esse campo automaticamente:
				//invoice.invoicdId = invoice.id.toString(); 
				return redirect( routes.InvoiceController.editInvoice(invoice.id) );
			}
		}

	    return badRequest(
	        info.render(form, mode)
	    );
	}

	public Result editInvoice(Long id) {
		Invoice invoice = Invoice.retrieve(id);
		Form<Invoice> form = formFactory.form(Invoice.class).fill(invoice);
	    return ok(
	        info.render(form, ModeConst.EDIT)
	    );
	}

	public Result delete(Long id) {
		Invoice invoice = Invoice.retrieve(id);
		if(invoice == null) {
			return notFound(id + " não consta no banco de dados.");
		}
		invoice.delete();
		return redirect( routes.InvoiceController.list() );
	}

}