package controller;

import db.DataBase;
import db.SessionDataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import util.SessionUtils;
import util.UrlUtils;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));

        if (user == null) {
            response.sendRedirect(UrlUtils.LOGIN_FAILED_URL);
            return;
        }

        if (user.login(request.getParameter("password"))) {
            HttpSession session = SessionDataBase.getSession(request.getCookieValue(SessionUtils.JSESSIONID));
            session.setAttribute("user", user);

            response.sendRedirect(UrlUtils.INDEX_URL);
        } else {
            response.sendRedirect(UrlUtils.LOGIN_FAILED_URL);
        }
    }
}
