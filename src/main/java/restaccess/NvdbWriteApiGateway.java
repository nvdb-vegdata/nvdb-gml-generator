package restaccess;

import no.svv.nvdb.api.inn.domain.datacatalog.DataCatalog;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;
import restaccess.auth.AuthTokenProvider;
import restaccess.auth.NvdbApiAuthFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class NvdbWriteApiGateway {
    private Client client;
    private final String nvdbWriteApiHost;
    private static final String CONTEXT_PATH = "nvdb/apiskriv";
    private String loginServerUrl, username, password;

    public NvdbWriteApiGateway(String loginServerUrl, String username, String password, String nvdbWriteApiHost) {
        this.loginServerUrl = loginServerUrl;
        this.username = username;
        this.password = password;
        this.nvdbWriteApiHost = nvdbWriteApiHost;
        this.client = createNewClient(this.loginServerUrl ,this.username, this.password);


    }

    private Client createNewClient(String loginServerUrl, String username, String password){
        return ClientBuilder.newBuilder()
                .register(new NvdbApiAuthFilter(
                        new AuthTokenProvider(loginServerUrl, username, password)))
                .register(new LoggingFilter(java.util.logging.Logger.getLogger(this.getClass().getName()), true))
                .build();
    }

    public Object getDataCatalog() {

        Invocation.Builder builder = createWebTarget(client).path("datakatalog").request("application/json");

        DataCatalog dataCatalog;
        Response response;
        try {
            response = builder.get();
            //dataCatalog = DataCatalog.fromJson((InputStream) response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize DataCatalog from NVDB API Skrive response", e);
        }

        return response.getEntity();
    }

    private WebTarget createWebTarget(Client client) {
        return client.target(nvdbWriteApiHost)
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .path(CONTEXT_PATH);
    }
}
