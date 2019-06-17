package com.iqss;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.TokenBuffer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/hello")
public class HelloWorld {

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        return input;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) throws IOException
    {
        // Take a copy using Jackson
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(input);
        JsonBean copy = mapper.readValue(s, JsonBean.class);

        // Swap the values round :-)
        input.setVal2(input.getVal1());
        input.setVal1(copy.getVal2());
        return Response.ok().entity(input).build();
    }
}

