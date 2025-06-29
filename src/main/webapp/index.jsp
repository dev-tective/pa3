<%@ page import="gatodev.pa4web.models.Contact" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang = "es">
<head>
    <meta charset="UTF-8">
    <title>JSP - Contacts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class = "bg-light">
    <div class="container my-5">
    <h1 class = "mb-4 text-primary">Lista de Contactos</h1>
    <%
        List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
        if(contacts != null && !contacts.isEmpty()){   
        %>
    <table class = "table table-striped table-bordered mt-4 rounded-2 shadow-sm">
        <thead class ="table-dark">
        <tr>
            <th>Nombre</th>
            <th>Teléfono</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        </thead>
        
        <tbody>
        <%
            for (Contact c : contacts) {
                %>
                    <tr>
                        <td><%= c.getFirstname() %> <%= c.getLastname() %></td>
                        <td><%= c.getPhoneNumber() %></td>
                        <td><%= c.getEmail() %></td>
                        <td>
                            <form method="post" action="contacts" onsubmit="return confirm('¿Deseas eliminar este contacto?')">
                                <input type="hidden" name="id" value="<%= c.getId() %>"/>
                                <input type="hidden" name="_method" value="delete"/>
                                <button type="submit" class="btn btn-danger btn-sm">
                                    <i class="bi bi-trash"></i> Eliminar
                                </button>
                            </form>
                        </td>
                    </tr>
                <%
            } %>
        </tbody>
    </table>

        <%} else{ %>
        <div class="alert alert-warning">No hay contactos para mostrar.</div>
        <% } %>
     

        <div class="card mt-5 shadow-sm">
            <div class="card-header bg-primary text-white">
                Agregar nuevo contacto
            </div>
            <div class="card-body">
    <form method="post" action="contacts" class="row g-3 needs-validation" novalidate>
        <div class="col-md-6">
            <input name="firstname" class="form-control" placeholder="Nombre" required/>
        </div>
        
        <div class="col-md-6">
            <input name="lastname" class="form-control" placeholder="Apellido" required/>
        </div>
        
        <div class="col-md-6">
            <input name="company" class="form-control" placeholder="Compañía" required/>
        </div>
        
        <div class="col-md-6">
            <input name="phone" class="form-control" placeholder="Teléfono" required/>
        </div>
        
        <div class="col-md-6">
            <input name="email" type="email" class="form-control" placeholder="Email" required/>
        </div>
        
        <div class="col-md-6">
            <input name="birthDate" type="date" class="form-control" placeholder="YYYY-MM-DD" required/>
        </div>
        
        <div class="col-12">
            <input name="address" class="form-control" placeholder="Dirección" required/>
        </div>
        
        <div class="col-12 mt-3">
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-save"></i> Guardar</button>
        </div>
    </form>
    </div>
        </div>
    </div>
</body>
</html>
