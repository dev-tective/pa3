package gatodev.pa3web.services;

import gatodev.pa3web.DAO.ContactDAO;
import gatodev.pa3web.models.Contact;

import java.sql.SQLException;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private final ContactDAO contactDAO = ContactDAO.instance;
    public static ContactServiceImpl instance = new ContactServiceImpl();

    @Override
    public Contact addContact(Contact contact) {
        try {
            return contactDAO.save(contact)
                    .orElseThrow(() -> new RuntimeException("No se pudo guardar el contacto"));
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el contacto", e);
        }
    }

    @Override
    public Contact updateContact(Contact contact) {
        try {
            return contactDAO.update(contact)
                    .orElseThrow(() -> new RuntimeException("No se encontró el contacto para actualizar"));
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el contacto", e);
        }
    }

    @Override
    public String deleteContact(long id) {
        try {
            boolean deleted = contactDAO.deleteById(id);
            return deleted ? "Contacto eliminado exitosamente" :
                    "No se encontró el contacto para eliminar";
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el contacto", e);
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        try {
            return contactDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los contactos", e);
        }
    }

    @Override
    public Contact getContactById(int id) {
        try {
            return contactDAO.findById((long) id)
                    .orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el contacto", e);
        }
    }
}
