package lt.bit.java2.rs;

import lt.bit.java2.db.SalaryRepository;
import lt.bit.java2.db.entities.Salary;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/salary")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaryService {

    @Inject
    SalaryRepository salaryRepository;

    @GET
    @Path("/{empNo}")
    public List<Salary> getSalaryList(@PathParam("empNo") int empNo) {
        return salaryRepository.listByEmpNo(empNo);
    }

    @POST
    public Salary create(Salary salary) {
        return salaryRepository.create(salary);
    }


}
