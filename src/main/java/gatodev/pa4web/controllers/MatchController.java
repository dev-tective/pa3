package gatodev.pa4web.controllers;

import gatodev.pa4web.services.MatchService;
import gatodev.pa4web.services.MatchServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "MatchServlet", urlPatterns = "/match")
public class MatchController extends HttpServlet {
    private final MatchService matchService = MatchServiceImpl.instance;


}
