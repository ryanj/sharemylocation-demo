package com.sharemylocation.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import com.sharemylocation.converters.StatusConverter;
import com.sharemylocation.dao.ApplicationDao;
import com.sharemylocation.domain.Status;
import com.sharemylocation.domain.StatusWithDistance;
import com.twitter.Extractor;

@Path("/statuses")
public class StatusResource {

    @Inject
    private ApplicationDao dao;

    @Inject
    private StatusConverter converter;

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response postStatus(@Valid Status status) {
        if (status.getLocation() != null) {
            Geocoder geocoder = new Geocoder();
            LatLng latLng = new LatLng(BigDecimal.valueOf(status.getLocation().getCoordinates()[1]),
                    BigDecimal.valueOf(status.getLocation().getCoordinates()[0]));
            GeocodeResponse geocodeResponse = geocoder.geocode(new GeocoderRequestBuilder().setLocation(latLng)
                    .getGeocoderRequest());
            if (geocodeResponse != null && !geocodeResponse.getResults().isEmpty()) {
                GeocoderResult geocoderResult = geocodeResponse.getResults().get(0);
                String formattedAddress = geocoderResult.getFormattedAddress();
                status.setAddress(formattedAddress);
            }
        }
        Extractor extractor = new Extractor();
        List<String> hashtags = extractor.extractHashtags(status.getStatus());
        status.setHashTags(hashtags.toArray(new String[0]));

        dao.save(status, converter);
        return Response.ok(status).status(javax.ws.rs.core.Response.Status.CREATED).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<Status> allStatuses() {
        return dao.findAll(converter);
    }
    
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/{lng}/{lat}")
    public List<Status> findNear(@PathParam("lng") double lng, @PathParam("lat") double lat,
            @QueryParam("hashtags") String hashtagStr, @QueryParam("user") String user) {

        String[] hashtags = (hashtagStr == null || hashtagStr == "") ? null : hashtagStr.split(",");
        return dao.findNear(hashtags, user, new double[] { lng, lat }, converter);
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/geonear/{lng}/{lat}")
    public List<StatusWithDistance> findGeoNear(@PathParam("lng") double lng, @PathParam("lat") double lat,
            @QueryParam("hashtags") String hashtagStr, @QueryParam("user") String user) {

        String[] hashtags = (hashtagStr == null || hashtagStr == "") ? null : hashtagStr.split(",");
        return dao.findGeoNear(hashtags, new double[] { lng, lat }, converter);
    }

}
