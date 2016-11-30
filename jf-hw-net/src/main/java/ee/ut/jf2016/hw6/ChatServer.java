package ee.ut.jf2016.hw6;

import org.eclipse.jetty.server.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {
    static ExecutorService executor = Executors.newCachedThreadPool();
    static ServerSocketChannel server;
    static final Integer SOCKET_ADDRESS = 7070;
    static final Integer HTTP_PORT = 8080;
    static ConcurrentHashMap<SocketChannel, String> activeClients = new ConcurrentHashMap<>();
    static AtomicInteger atomicInteger = new AtomicInteger();
    public static void main(String[] args) throws IOException {
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(SOCKET_ADDRESS));
        server.configureBlocking(true);
        executor.execute(ChatServer::createHttpPort);
        executor.execute(ChatServer::acceptNewClients);
    }

    private static void createHttpPort() {

        ServerHandler httpHandler = new ServerHandler();
        Server server = new Server(HTTP_PORT);
        server.setHandler(httpHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void acceptNewClients(){
        while(true){
            try{
                SocketChannel newClient = server.accept();
                BufferedReader reader = new BufferedReader(Channels.newReader(newClient, "UTF-8"));
                String clientName = reader.readLine();
                if(activeClients.values().contains(clientName)) clientName = clientName + atomicInteger.incrementAndGet(); // to avoid duplicate names in the server
                String message = clientName+" has joined\n";
                executor.execute(()-> notifyEveryBody(message, newClient));
                executor.execute(() -> waitForMessages(newClient));
                activeClients.put(newClient, clientName);
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void notifyEveryBody(String msg, SocketChannel otherThan) {
        activeClients.keySet()
                .stream()
                .filter(cl -> !cl.equals(otherThan))
                .forEach(activeClients-> notifyAClient(activeClients,msg));
    }

    public static void notifyAClient(SocketChannel activeClient, String msg){

        Writer writer = Channels.newWriter(activeClient, "UTF-8");
        try {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * will be run for every single client in a different thread
     * @param client
     */
    public static void waitForMessages(SocketChannel client) {
        BufferedReader reader = new BufferedReader(Channels.newReader(client, "UTF-8"));
        String clientMessage;
        try {
            clientMessage = reader.readLine(); // blocked here till we get a message or socket is closed
            while (true && clientMessage != null){ // while they did not close the socket, and input a string
                String wholeMessage = activeClients.get(client)+": " + clientMessage+"\n";
                executor.execute(()->notifyEveryBody(wholeMessage, client));
                reader = new BufferedReader(Channels.newReader(client, "UTF-8"));
                clientMessage = reader.readLine();

            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyEveryBody(activeClients.get(client)+"  has left\n", client);
        activeClients.remove(client);
        System.out.println(activeClients.values());
    }




}
