package gatodev.pa3web.dao;

import gatodev.pa3web.config.DBConnector;
import gatodev.pa3web.models.Contact;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactDAO implements DAO<Contact, Long> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static ContactDAO instance = new ContactDAO();

    //Crea la tabla si no existe
    private ContactDAO() {
        String createTable = """
            CREATE TABLE IF NOT EXISTS contacts (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                firstname VARCHAR(100),
                lastname VARCHAR(100),
                company VARCHAR(100),
                phone_number VARCHAR(20),
                email VARCHAR(100),
                birth_date DATE,
                address VARCHAR(255)
        );""";

        try (Statement stmt = con.createStatement()) {
            stmt.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Devuelve el objeto si es guardado
    @Override
    public Optional<Contact> save(Contact c) throws SQLException {
        String query = """
            INSERT INTO contacts
                (firstname,
                lastname,
                company,
                phone_number,
                email,
                birth_date,
                address)
                VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setContactData(c, ps);

        if(ps.executeUpdate() == 0) return Optional.empty();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) c.setId(keys.getLong(1));

        return Optional.of(c);
    }

    //Actualiza y devuelve si existe
    @Override
    public Optional<Contact> update(Contact contact) throws SQLException {
        String query = """
            UPDATE contacts SET
                firstname = ?,
                lastname = ?,
                company = ?,
                phone_number = ?,
                email = ?,
                birth_date = ?,
                address = ?
            WHERE id = ?
        """;

        PreparedStatement ps = con.prepareStatement(query);
        setContactData(contact, ps);
        ps.setLong(8, contact.getId());
        return ps.executeUpdate() > 0 ? Optional.of(contact) : Optional.empty();
    }

    //Coloca los datos del query SAVE y UPDATE
    private void setContactData(Contact contactUpdate, PreparedStatement ps) throws SQLException {
        ps.setString(1, contactUpdate.getFirstname());
        ps.setString(2, contactUpdate.getLastname());
        ps.setString(3, contactUpdate.getCompany());
        ps.setString(4, contactUpdate.getPhoneNumber());
        ps.setString(5, contactUpdate.getEmail());
        ps.setDate(6, Date.valueOf(contactUpdate.getBirthDate()));
        ps.setString(7, contactUpdate.getAddress());
    }

    //Elimina un contacto por su id
    @Override
    public boolean deleteById(Long id) throws SQLException {
        String query = "DELETE FROM contacts WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, id);
        return ps.executeUpdate() != 0;
    }

    //Lista todos los contactos
    @Override
    public List<Contact> findAll() throws SQLException {
        String query = "SELECT * FROM contacts";
        List<Contact> contacts = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            contacts.add(Contact.builder()
                    .id(rs.getLong("id"))
                    .firstname(rs.getString("firstname"))
                    .lastname(rs.getString("lastname"))
                    .company(rs.getString("company"))
                    .phoneNumber(rs.getString("phone_number"))
                    .email(rs.getString("email"))
                    .birthDate(LocalDate.parse(rs.getString("birth_date")))
                    .address(rs.getString("address"))
                    .build());
        }

        return contacts;
    }

    //Busca un contacto por su id
    @Override
    public Optional<Contact> findById(Long id) throws SQLException {
        String query = "SELECT * FROM contacts WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        return !rs.next() ? Optional.empty() : Optional.of(Contact.builder()
                .id(rs.getLong("id"))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .company(rs.getString("company"))
                .phoneNumber(rs.getString("phone_number"))
                .email(rs.getString("email"))
                .birthDate(LocalDate.parse(rs
                        .getString("birth_date")))
                .address(rs.getString("address"))
                .build());
    }
}
