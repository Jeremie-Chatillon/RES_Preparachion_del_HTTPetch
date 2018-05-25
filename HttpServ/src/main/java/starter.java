import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class starter {
    public static void main (String[] args){
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new starter.MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            Horloge horloge = new Horloge();

            // POST RESQUEST
            if (t.getRequestMethod().equalsIgnoreCase("POST")) {

                // REQUEST Headers
                Headers requestHeaders = t.getRequestHeaders();
                Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                // REQUEST Body

                InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder(contentLength);
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                horloge.SetDateFormatString(buf.toString());

                // RESPONSE Headers
                Headers responseHeaders = t.getResponseHeaders();

                // Send RESPONSE Headers
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                // RESPONSE Body
                OutputStream os = t.getResponseBody();

                os.write(horloge.GetDateFormatString().getBytes());

                t.close();
            }

            // GET RESQUEST
            else{
            t.sendResponseHeaders(200,horloge.GetDateFormatString().length());
            OutputStream os = t.getResponseBody();
            os.write(horloge.GetDateFormatString().getBytes());
            os.close();
            }
        }
    }
}
