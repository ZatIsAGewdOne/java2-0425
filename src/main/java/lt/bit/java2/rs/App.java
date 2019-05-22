package lt.bit.java2.rs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ObjectMapperContextResolver.class);
        resources.add(EmployeeService.class);

        return resources;
    }
}
