package lt.bit.java2.rs;

import lt.bit.java2.db.ProductRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/api")
public class App extends ResourceConfig {

    public App() {
        // Nerodome standartiniu Tomcat error'u
        Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
        setProperties(properties);

//        // registruojame API servisus
//        register(ObjectMapperContextResolver.class);
//        register(ProductService.class);
        packages("lt.bit.java2.rs");

        // registruojame injektinamus objektus
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(ProductRepository.class);
                //bindAsContract(OtherRepository.class);
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
