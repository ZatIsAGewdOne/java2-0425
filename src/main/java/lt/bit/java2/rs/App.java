package lt.bit.java2.rs;

import lt.bit.java2.db.EmployeeRepository;
import lt.bit.java2.db.SalaryRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/rest")
public class App extends ResourceConfig {

    public App() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
        setProperties(properties);

        register(EmployeeService.class);
        register(SalaryService.class);
        register(ObjectMapperContextResolver.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(EmployeeRepository.class);
                bindAsContract(SalaryRepository.class);
            }
        });
    }

    //    // Nerodome standartiniu Tomcat error'u
//    @Override
//    public Map<String, Object> getProperties() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
//        return properties;
//    }
}
