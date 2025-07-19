package gatodev.pa4web.services.generic;

import gatodev.pa4web.DAO.UserDAO;
import gatodev.pa4web.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements GenericService<User>{
    private final UserDAO userDAO = UserDAO.instance;
    public static final UserServiceImpl instance = new UserServiceImpl();
    
    @Override
    public User add(User entity){
        try{
            return userDAO.save(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo registrar el usuario"));
            
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public User update(User entity){
        try{
            return userDAO.update(entity)
                    .orElseThrow(() -> new RuntimeException("No se pudo actualizar el usuario"));
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
   
    @Override
    public boolean delete(Integer id){
        try{
            return userDAO.deleteById(id);
            
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
   
    @Override
    public User get(Integer id){
        try{
            return userDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<User> getAll(){
        try{
            return userDAO.findAll();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }      
    }
    
    //Mpetoodo login
    public User login(String username, String password){
        try{
            return userDAO.findByUsernameAndPassword(username, password)
                    .orElse(null);
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
