package org.acme.controllers;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.acme.entity.Person;

import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {
    @GET
    public Uni<List<Person>> list() {
        return Person.listAll();
    }

    @POST
    @ReactiveTransactional
    public Uni<PanacheEntityBase> create(Person person) {
        return person.persistAndFlush()
                .onFailure(PersistenceException.class)
                .recoverWithItem((pe) -> {
                    System.err.println("Unable to create the parameter" + pe.getMessage());
                    //in case of error, I save it to disk
                   // diskPersister.save(person);
                    return null;
                });

    }


}
