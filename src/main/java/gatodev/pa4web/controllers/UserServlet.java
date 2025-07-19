package gatodev.pa4web.controllers;

import gatodev.pa4web.models.User;
import gatodev.pa4web.services.generic.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private final UserServiceImpl userService = UserServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");

        if ("update".equalsIgnoreCase(method)) {
            updateUser(request);
        } else if ("delete".equalsIgnoreCase(method)) {
            deleteUser(request);
        } else {
            createUser(request);
        }

        response.sendRedirect("user");
    }

    private void createUser(HttpServletRequest request) {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setNombres(request.getParameter("nombres"));
        user.setRol(request.getParameter("rol"));

        userService.add(user);
    }

    private void updateUser(HttpServletRequest request) {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setNombres(request.getParameter("nombres"));
        user.setRol(request.getParameter("rol"));

        userService.update(user);
    }

    private void deleteUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.delete(id);
    }
}
