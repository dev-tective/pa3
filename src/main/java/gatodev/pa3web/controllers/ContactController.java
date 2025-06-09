package gatodev.pa3web.controllers;

import gatodev.pa3web.models.Contact;
import gatodev.pa3web.services.ContactService;
import gatodev.pa3web.services.ContactServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "ContactsServlet", urlPatterns = {"", "/contacts"})
public class ContactController extends HttpServlet {
    private final ContactService contactService = ContactServiceImpl.instance;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contacts", contactService.getAllContacts());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Contact contact = parseContact(req);
        contactService.addContact(contact);
        resp.sendRedirect(req.getContextPath() + "/contacts");
    }

    private Contact parseContact(HttpServletRequest req) {
        Contact c = new Contact();
        c.setFirstname(req.getParameter("firstname"));
        c.setLastname(req.getParameter("lastname"));
        c.setCompany(req.getParameter("company"));
        c.setPhoneNumber(req.getParameter("phone"));
        c.setEmail(req.getParameter("email"));
        String birthDateStr = req.getParameter("birthDate");
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            c.setBirthDate(LocalDate.parse(birthDateStr));
        }
        c.setAddress(req.getParameter("address"));
        return c;
    }
}
