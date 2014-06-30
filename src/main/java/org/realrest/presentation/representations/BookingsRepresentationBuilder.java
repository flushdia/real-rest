package org.realrest.presentation.representations;

import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.component.Link;
import com.google.code.siren4j.component.builder.EntityBuilder;
import com.google.code.siren4j.component.builder.LinkBuilder;
import org.realrest.domain.Booking;
import org.realrest.presentation.resources.BookingsResource;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author volodymyr.tsukur
 */
public class BookingsRepresentationBuilder {

    private final Collection<Booking> bookings;

    private final UriInfo uriInfo;

    public BookingsRepresentationBuilder(final Collection<Booking> bookings, final UriInfo uriInfo) {
        this.bookings = bookings;
        this.uriInfo = uriInfo;
    }

    public Entity build() {
        return EntityBuilder.newInstance().
                setComponentClass("bookings").
                addSubEntities(bookings()).
                addLink(LinkBuilder.newInstance().
                        setHref(selfHref()).
                        setRelationship(Link.RELATIONSHIP_SELF).
                        build()).build();
    }

    private List<Entity> bookings() {
        return bookings.stream().
                map(booking -> new BookingEmbeddedRepresentationBuilder(booking, uriInfo).build()).
                collect(Collectors.toList());
    }

    private String selfHref() {
        return selfURI().toString();
    }

    private URI selfURI() {
        return selfURI(uriInfo);
    }

    static URI selfURI(final UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().
                path(BookingsResource.class).
                build();
    }

}
