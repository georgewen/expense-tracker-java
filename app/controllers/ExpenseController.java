package controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.inject.Inject;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ExpenseService;

public class ExpenseController extends Controller {

	@Inject
	private ExpenseService expenseService;

    public Result index() {
        return ok(jsonResponse("data", expenseService.getAll()));
    }

    public Result getExpense(String id) {
    	try {
    		return ok(jsonResponse("data", expenseService.get(id)));
    	} catch (NoSuchElementException e) {
    		return notFound(jsonResponse("message", "item doesn not exists"));
    	} catch (Exception e) {
    		return badRequest(jsonResponse("message", "error: " + e.getMessage()));
		}
    }
    
    public Result createExpense(Http.Request request) {
    	try {
			Optional<Expense> expense = request.body().parseJson(Expense.class);
			expenseService.save(expense.orElseThrow(IllegalArgumentException::new));
			return created(jsonResponse("data", expense));
		} catch	(IllegalArgumentException e) {
    		return badRequest(jsonResponse("message", "expecting JSON data"));

		} catch (Exception e) {
			return badRequest(jsonResponse("message", "error: " + e.getMessage()));
		}
    	//return expense.map(e -> createExpense(e))
    	//		.orElse(badRequest(jsonResponse("message", "expecting JSON data")));
    }

    public Result modifyExpense(Http.Request request, String id) {
    	try {
			Optional<Expense> expense = request.body().parseJson(Expense.class);
			expenseService.update(id,expense.orElseThrow(IllegalArgumentException::new));
			return ok(jsonResponse("message", "the item is modified"));
		} catch	(NoSuchElementException e) {
			return badRequest(jsonResponse("message", "the item does not exist"));
		} catch	(IllegalArgumentException e) {
			return badRequest(jsonResponse("message", "Expecting JSON data"));
		} catch (NoSuchMethodException e) {
			return badRequest(jsonResponse("message", "THe key does not exist"));
		} catch (Exception e) {
			return badRequest(jsonResponse("message", "ERROR: " + e.getMessage()));
		}
    }
    
    public Result deleteExpense(String id) {
    	try {
			expenseService.delete(id);
    		return ok(jsonResponse("message", "the item is deleted"));
    	} catch(NoSuchElementException e) {
    		return badRequest(jsonResponse("message", "the Item does not exist")); 
    	}
    }
    
    private ObjectNode jsonResponse(String key, Object obj) {
    	ObjectNode objectNode = Json.newObject();
    	objectNode.set(key, Json.toJson(obj));
    	return objectNode;
    }
    
}
