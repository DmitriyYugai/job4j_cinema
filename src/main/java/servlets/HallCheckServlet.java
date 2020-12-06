package servlets;

import com.google.gson.Gson;
import models.Hall;
import store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class HallCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Hall> hall = PsqlStore.instOf().findHallByRowCol(
                Integer.parseInt(req.getParameter("rowCol")));
        String result = "";
        if (hall.isPresent()) {
            Gson gson = new Gson();
            result = gson.toJson(hall.get());
        }
        resp.getWriter().print(result);
    }
}
