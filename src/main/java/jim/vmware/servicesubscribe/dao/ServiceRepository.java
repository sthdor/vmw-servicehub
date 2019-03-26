package jim.vmware.servicesubscribe.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jim.vmware.servicesubscribe.model.Servize;

@Repository
public interface ServiceRepository extends CrudRepository<Servize, Integer> {
    Servize findByName(String name);
}
