package gatodev.pa4web.services.generic;

import gatodev.pa4web.DAO.LeagueDAO;
import gatodev.pa4web.models.League;

import java.sql.SQLException;
import java.util.List;

public class LeagueServiceImpl implements GenericService<League> {
    private final LeagueDAO leagueDAO = LeagueDAO.instance;
    public final static LeagueServiceImpl instance = new LeagueServiceImpl();

    @Override
    public League add(League entity) {
        try {
            return leagueDAO.save(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo agregar la liga"));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public League update(League entity) {
        try {
            return leagueDAO.update(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo actualizar la liga"));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return leagueDAO.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public League get(Integer id) {
        try {
            return leagueDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Liga no encontrada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<League> getAll() {
        try {
            return leagueDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
