package getithere;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class WebServer{


    public static void main(String[] args) throws Exception{
        Integer port = 8080;

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[] { "index.html" });
        resource_handler.setResourceBase("./static/");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler });
        server.setHandler(handlers);


        server.start();
        server.join();
    }
}
