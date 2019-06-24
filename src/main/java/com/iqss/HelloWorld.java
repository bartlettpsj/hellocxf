package com.iqss;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.TokenBuffer;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/")
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

    private String getMapDisplay(String title, Set set1) {
        String header = String.format("<h1>%s</h1>", title);
        Set<Map.Entry<String, String>> set = set1;

        return header  +
            set.stream()
                .map( entry -> "<b>" + entry.getKey() + "</b>: " + entry.getValue())
                .collect(Collectors.joining("<br>"));
    }

    // Return Environment Variables
    @GET
    @Path("/env")
    @Produces("text/html")
    public String env() {
        return getMapDisplay("Environment Variables", System.getenv().entrySet());
    }

    @GET
    @Path("/prop    ")
    @Produces("text/html")
    public String prop() {
        return getMapDisplay("System Properties", System.getProperties().entrySet());
    }
}

