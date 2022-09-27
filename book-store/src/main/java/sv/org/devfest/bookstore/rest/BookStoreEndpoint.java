/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.devfest.bookstore.rest;

import java.util.Properties;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import sv.org.devfest.bookstore.entities.Book;
import sv.org.devfest.bookstore.services.BookService;

/**
 *
 * @author xtecuan
 */
@RequestScoped
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookStoreEndpoint {

    @Inject
    BookService bookService;   

    @APIResponses(
            value = {
                @APIResponse(
                        responseCode = "404",
                        description = "We could not find anything",
                        content = @Content(mediaType = "text/plain")
                ),
                @APIResponse(
                        responseCode = "200",
                        description = "We have a list of books",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Properties.class))
                )
            }
    )
    @Operation(summary = "Outputs a list of books", description = "This method outputs a list of books")
    @Timed(name = "get-all-books",
            description = "Monitor the time getAll Method takes",
            unit = MetricUnits.MILLISECONDS,
            absolute = true)
//    @Timeout(0)
//    @Retry(maxRetries = 3, delay = 300)
//    @Fallback(fallbackMethod = "getAllFallbackMethod")
//    @Bulkhead(10)
//    @CircuitBreaker(delay = 2000, requestVolumeThreshold = 2, failureRatio=0.65, successThreshold = 3)
    @GET
    public Response getAll() {
        return Response.ok(bookService.getAll()).build();
    }

    public Response getAllFallbackMethod() {
        return Response.ok(Stream.of("Book One", "Book Two").collect(toList())).build();
    }

    @Counted(unit = MetricUnits.NONE,
            name = "get-book",
            absolute = true,
            monotonic = true,
            displayName = "get single book",
            description = "Monitor how many times getBook method was called")
    @GET
    @Path("{id}")
    public Response getBook(@PathParam("id") Long id) {
        Book book = bookService.findById(id);

        return Response.ok(book).build();
    }

    @Metered(name = "create-books",
            unit = MetricUnits.MILLISECONDS,
            description = "Monitor the rate events occured",
            absolute = true)
    @POST
    public Response create(Book book) {
        bookService.create(book);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Book book) {
        Book updateBook = bookService.findById(id);

        updateBook.setIsbn(book.getIsbn());
        updateBook.setDescription(book.getDescription());
        updateBook.setLanguage(book.getLanguage());
        updateBook.setPages(book.getPages());
        updateBook.setPrice(book.getPrice());
        updateBook.setPublisher(book.getPublisher());
        updateBook.setTitle(book.getTitle());

        bookService.update(updateBook);

        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        Book getBook = bookService.findById(id);
        bookService.delete(getBook);
        return Response.ok().build();
    }
}
