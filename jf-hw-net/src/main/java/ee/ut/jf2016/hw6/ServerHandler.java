package ee.ut.jf2016.hw6;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Erdem on 05-Oct-16.
 */
public class ServerHandler extends AbstractHandler {
    @Override
    public void handle(String path, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        baseRequest.setHandled(true);
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter  writer= response.getWriter();
        ChatServer.activeClients.values().forEach(writer::println);
    }
}