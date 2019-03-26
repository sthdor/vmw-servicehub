package jim.vmware.servicesubscribe.service;

import java.util.List;

import jim.vmware.servicesubscribe.model.Servize;
import jim.vmware.servicesubscribe.model.Subscription;
import jim.vmware.servicesubscribe.model.User;

public interface SPservice {
    void addUser(String username);
    
    void addService(String servicename, String url);
    
    void userSubscribeService(String username, String servicename);
    
    List<User> getAllUsers();
    
    List<Servize> getAllServices();
    
    List<Subscription> getAllSubscriptions();
    
    List<Servize> getUserSubscribedService(String username);

}
