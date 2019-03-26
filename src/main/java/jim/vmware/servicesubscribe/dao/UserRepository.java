package jim.vmware.servicesubscribe.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jim.vmware.servicesubscribe.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}
