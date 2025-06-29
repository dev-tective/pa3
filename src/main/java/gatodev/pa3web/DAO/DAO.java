package gatodev.pa3web.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<M, I> {
    Optional<M> save(M m) throws SQLException;
    Optional<M> update(M m) throws SQLException;
    boolean deleteById(I id) throws SQLException;
    List<M> findAll() throws SQLException;
    Optional<M> findById(I id) throws SQLException;
}
