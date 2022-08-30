package controller;

import db.DataBase;
import db.SessionDataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import util.SessionUtils;
import util.UrlUtils;

import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogin(request.getCookieValue(SessionUtils.JSESSIONID))) {
            response.sendRedirect(UrlUtils.LOGIN_URL);
            return;
        }

        Collection<User> users = DataBase.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<td>" + user.getUserId() + "</td>");
            sb.append("<td>" + user.getName() + "</td>");
            sb.append("<td>" + user.getEmail() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        response.forwardBody(sb.toString());
    }

    private boolean isLogin(String sessionId) {
        HttpSession session = SessionDataBase.getSession(sessionId);
        User user = (User) session.getAttribute("user");

        return user != null;
    }
}
