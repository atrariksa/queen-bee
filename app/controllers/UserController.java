package controllers;

import models.User;
import models.UserRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to provide access to the
 * {@link Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class UserController extends Controller {

    private final FormFactory formFactory;
    private final UserRepository userRepository;
    private final HttpExecutionContext ec;

    @Inject
    public UserController(FormFactory formFactory, UserRepository userRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.userRepository = userRepository;
        this.ec = ec;
    }

    public Result addUserPage(final Http.Request request) {
        return ok(views.html.user.render(request));
    }

    public CompletionStage<Result> addUser (final Http.Request request) {
        User user = formFactory.form(User.class).bindFromRequest(request).get();
        return userRepository
                .add(user)
                .thenApplyAsync(p -> redirect(routes.UserController.addUserPage()), ec.current());
    }

    public CompletionStage<Result> getUsers() {
        return userRepository
                .list()
                .thenApplyAsync(userStream -> ok(toJson(userStream.collect(Collectors.toList()))), ec.current());
    }
}
