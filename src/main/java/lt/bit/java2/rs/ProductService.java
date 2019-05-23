package lt.bit.java2.rs;

import lt.bit.java2.db.ProductRepository;
import lt.bit.java2.db.entities.Product;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Objects;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @GET
    public Response get(@QueryParam("page-size") @DefaultValue("10") Integer pageSize, @QueryParam("offset")  @DefaultValue("0") Integer offset) {
        return Response.ok(productRepository.list(pageSize, offset)).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        Product product = productRepository.get(id);

        if (product == null) return Response.status(Response.Status.NOT_FOUND).entity("id = " + id).build();

        return Response.ok(product).build();
    }

    @POST
    public Response create(Product product) {
        try {
            Product e = productRepository.create(product);
            return Response.ok(e).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Product product) {
        if (product.getId() != null && !Objects.equals(product.getId(), id)) return Response.status(Response.Status.BAD_REQUEST).build();
        try {
            product.setId(id);
            product = productRepository.update(product);
            return Response.ok(product).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id) {
        //TODO
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    //
    // Demo kaip kazka (Counter objekta) prideti ir paskui gauti is sesijos
    // Panasiai reikia sugalvoti kaip issaugoti krepselyje prekes
    // Daugiau info apie sesijas (ir cookius):
    // https://www.baeldung.com/java-servlet-cookies-session
    //

    @GET
    @Path("/cart/add/{id}")
    public Response addCart(@PathParam("id") Integer id, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();

        Object value = session.getAttribute("cart");
        Counter counter;
        if (value instanceof Counter) {
            counter = (Counter) value;
        } else {
            counter = new Counter();
        }

        counter.setValue(counter.getValue() + id);
        counter.setTime(LocalDateTime.now());

        session.setAttribute("cart", counter);
        return Response.ok(counter).build();
    }

    @GET
    @Path("/cart")
    public Response displayCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute("cart");
        return Response.ok(value).build();
    }

}


class Counter {
    private int value;
    private LocalDateTime time;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
