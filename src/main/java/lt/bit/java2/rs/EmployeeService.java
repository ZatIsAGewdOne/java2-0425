package lt.bit.java2.rs;

import lt.bit.java2.db.EmployeeRepository;
import lt.bit.java2.db.entities.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeService {

    @GET
    @Path("/{empNo}")
    public Response get(@PathParam("empNo") int empNo, @QueryParam("salaries") boolean withSalaries) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        Employee e = withSalaries ? employeeRepository.getWithSalary(empNo) : employeeRepository.get(empNo);

        if (e == null) return Response.status(Response.Status.NOT_FOUND).entity("empNo = " + empNo).build();

        return Response.ok(e).build();
    }

    @POST
    @Path("/")
    public Response create(Employee employee) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        try {
            Employee e = employeeRepository.create(employee);
            return Response.ok(e).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{empNo}")
    public Response update(@PathParam("empNo") int empNo, Employee employee) {
        if (employee.getEmpNo() != null && employee.getEmpNo() != empNo) return Response.status(Response.Status.BAD_REQUEST).build();

        EmployeeRepository employeeRepository = new EmployeeRepository();

        try {
            employee.setEmpNo(empNo);
            employee = employeeRepository.update(employee);
            return Response.ok(employee).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
