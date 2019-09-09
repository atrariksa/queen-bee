package controllers.dashboards;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;

public class DashboardController extends Controller {

    public CompletableFuture<Result> getDashboard() {
        CompletableFuture<Result> dashboardView = new CompletableFuture<>();
        dashboardView.complete(Results.ok(views.html.dashboards.maindashboard.render("Queen Bee")));
        return dashboardView;
    }
}
