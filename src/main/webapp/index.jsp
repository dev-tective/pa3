<%@ page import="gatodev.pa3web.models.Contact" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Contacts</title>
</head>
<body>
    <h1>Lista de Contactos</h1>
    <table>
        <tr>
            <th>Nombre</th><th>Teléfono</th><th>Email</th><th>Acciones</th>
        </tr><%
            List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
            for (Contact c : contacts) {
                %>
                    <tr>
                        <td><%= c.getFirstname() %> <%= c.getLastname() %></td>
                        <td><%= c.getPhoneNumber() %></td>
                        <td><%= c.getEmail() %></td>
                        <td>
                            <form method="post" action="contacts">
                                <input type="hidden" name="id" value="<%= c.getId() %>"/>
                                <input type="hidden" name="_method" value="delete"/>
                                <input type="submit" value="Eliminar"/>
                            </form>
                        </td>
                    </tr>
                <%
            } %>
    </table>

    <h2>Agregar nuevo contacto</h2>
    <form method="post" action="contacts">
        <input name="firstname" placeholder="Nombre"/><br/>
        <input name="lastname" placeholder="Apellido"/><br/>
        <input name="company" placeholder="Compañía"/><br/>
        <input name="phone" placeholder="Teléfono"/><br/>
        <input name="email" placeholder="Email"/><br/>
        <input name="birthDate" placeholder="YYYY-MM-DD"/><br/>
        <input name="address" placeholder="Dirección"/><br/>
        <button type="submit">Guardar</button>
    </form>
</body>
</html>