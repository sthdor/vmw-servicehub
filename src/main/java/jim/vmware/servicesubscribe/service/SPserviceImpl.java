package jim.vmware.servicesubscribe.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jim.vmware.servicesubscribe.dao.ServiceRepository;
import jim.vmware.servicesubscribe.dao.UserRepository;
import jim.vmware.servicesubscribe.model.Servize;
import jim.vmware.servicesubscribe.model.Subscription;
import jim.vmware.servicesubscribe.model.User;

@Service
public class SPserviceImpl implements SPservice {
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
}
    
    
    @Override
    public void addUser(String username) {
        jdbcTemplate.update("INSERT INTO user(name) VALUES (?)", username);
    }
    
    @Override
    public void addService(String servicename, String url) {
        jdbcTemplate.update("INSERT INTO service(name, url) VALUES (?)", servicename, url);
    }
    
    @Override
    public void userSubscribeService(String username, String servicename) {
        if(!getUserSubscribedService(username).stream().
                filter(s -> s.getName().equals(servicename)).findFirst().isPresent()) {
            Integer uid = userRepository.findByName(username).getId();
            Integer sid = serviceRepository.findByName(servicename).getId();
            jdbcTemplate.update("INSERT INTO subscription(userid, serviceid, name) VALUES (?,?,?)", uid, sid,
                    username + servicename);
        }
    }
    
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT id, name FROM user", new Object[0],
                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name")));
    }
    
    @Override
    public List<Servize> getAllServices() {
        return jdbcTemplate.query("SELECT id, name, url FROM service", new Object[0],
                (rs, rowNum) -> new Servize(rs.getInt("id"), rs.getString("name"), rs.getString("url")));
    }
    
    @Override
    public List<Subscription> getAllSubscriptions() {
        return jdbcTemplate.query("SELECT id, userid, serviceid, name FROM subscription", new Object[0],
                (rs, rowNum) -> new Subscription(rs.getInt("id"), rs.getInt("userid"), rs.getInt("serviceid"),
                        rs.getString("name")));
    }
    
    @Override
    public List<Servize> getUserSubscribedService(String username) {
        return jdbcTemplate.query(
                "select sc.id, sc.name, sc.url from service sc, subscription sb, user u "
                        + "where sc.id=sb.serviceid and sb.userid=u.id and u.name=?",
                new Object[] { username },
                (rs, rowNum) -> new Servize(rs.getInt("id"), rs.getString("name"), rs.getString("url")));
    }


    public void userUnsubscribeService(String username, String servicename) {
        Integer uid = userRepository.findByName(username).getId();
        Integer sid = serviceRepository.findByName(servicename).getId();
        
        jdbcTemplate.update("DELETE FROM subscription where userid=? AND serviceid=?", 
                uid, sid);
        
    }

}
