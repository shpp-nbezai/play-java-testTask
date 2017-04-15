package controllers;

import javax.inject.*;

import com.fasterxml.jackson.databind.node.*;
import errors.BadRequestException;
import http.BaseHttpComponent;
import errors.InternalServerErrorException;
import requests.UserGetRequest;
import com.fasterxml.jackson.databind.*;
import play.libs.F.Function;
import play.libs.F.RedeemablePromise;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.*;
import play.mvc.Result;
import json.JsonComponent;
import play.data.Form;
import play.Logger;

@Singleton
public class AsyncController extends Controller {

    @Inject
    BaseHttpComponent httpComponent;
    @Inject
    JsonComponent jsonComponent;

    private Function<Throwable, Result> recoverFunction = new Function<Throwable, Result>() {
        @Override
        public Result apply(Throwable throwable) throws Throwable {
            if (throwable instanceof BadRequestException) {
                return badRequest(throwable.getMessage());
            }
            if (throwable instanceof InternalServerErrorException) {
                return internalServerError(throwable.getMessage());
            }
            Logger.error("ERROR ! - " + throwable.getMessage());
            ObjectNode jsonResult = jsonComponent.getFBServerErrorJson(throwable);
            return internalServerError(jsonResult);
        }
    };
    /*
    * Action for FB
    */
    public Promise<Result> faceBookMeAction() throws Throwable {
        Form<UserGetRequest> userForm = Form.form(UserGetRequest.class).bindFromRequest();
        if (userForm.hasErrors()) {
            RedeemablePromise<Result> promise = RedeemablePromise.empty();
            JsonNode json = Json.toJson(userForm.errorsAsJson());
            Status result = badRequest(json);
            promise.success(result);
            return promise;
        }
        UserGetRequest data = userForm.get();
        Promise<String> promis = httpComponent.facebookMeRouting(data);
        return promis.map(new Function<String, Result>() {
            public Result apply(String response) {
                return ok(response);
            }
        }).recover(recoverFunction);
    }
}
