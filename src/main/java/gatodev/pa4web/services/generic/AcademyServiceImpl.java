package gatodev.pa4web.services.generic;

import gatodev.pa4web.DAO.AcademyDAO;
import gatodev.pa4web.models.Academy;

import java.sql.SQLException;
import java.util.List;

public class AcademyServiceImpl implements GenericService<Academy> {
    private final AcademyDAO academyDAO = AcademyDAO.instance;
    public final static AcademyServiceImpl instance = new AcademyServiceImpl();

    @Override
    public Academy add(Academy entity) {
        try {
            return academyDAO.save(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo agregar la academia."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Academy update(Academy entity) {
        try {
            return academyDAO.update(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo actualizar la academia."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return academyDAO.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Academy get(Integer id) {
        try {
            return academyDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academia no encontrada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Academy> getAll() {
        try {
            return academyDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
