import com.typesafe.config.Config;

import play.*;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.mvc.Http.*;
import play.mvc.*;

import javax.inject.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(
            Config config,
            Environment environment,
            OptionalSourceMapper sourceMapper,
            Provider<Router> routes) {
        super(config, environment, sourceMapper, routes);
    }

    protected CompletionStage<Result> onProdServerError(
            RequestHeader request, UsefulException exception) {
        return CompletableFuture.completedFuture(
                Results.internalServerError("A server error occurred: " + exception.getMessage()));
    }

    protected CompletionStage<Result> onForbidden(RequestHeader request, String message) {
        return CompletableFuture.completedFuture(
                Results.forbidden("You're not allowed to access this resource."));
    }

    @Override
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return super.onClientError(request, statusCode, message);
    }

    @Override
    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
//        return CompletableFuture.completedFuture(Results.internalServerError("onServerError" + getTopException(exception)));
        return super.onServerError(request, exception);
    }

    private Throwable getTopException(Throwable e) {
        if (null == e.getCause()) {
            return e;
        }
        return getTopException(e.getCause());
    }
}