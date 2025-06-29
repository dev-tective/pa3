package gatodev.pa3web.services;

import gatodev.pa3web.models.Contact;

import java.util.List;

public interface ContactService {
    Contact addContact(Contact contact);
    Contact updateContact(Contact contact);
    String deleteContact(long id);
    List<Contact> getAllContacts();
    Contact getContactById(int id);
}
