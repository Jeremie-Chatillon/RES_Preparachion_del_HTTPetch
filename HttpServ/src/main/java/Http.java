import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Http{

    private HttpServer server;
    //private Horloge horloge;
    private Date currentTime;
    private DateFormat format;
    private long delta;

    public Http (){
        currentTime = new Date();
        format = new SimpleDateFormat("HH:mm:ss");
        server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/time", new Http.MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyHandler implements HttpHandler{



        public void handle(HttpExchange t) throws IOException {

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

                // Need to use URLDecoder because the client give us '%a3' in place of ':' in the time string.
                String clearString = URLDecoder.decode( buf.toString(), "UTF-8" );

                // substring because we receive "content=
                clearString = clearString.substring(clearString.lastIndexOf("=") + 1);
                SetDateFormatString(clearString);

                // RESPONSE Headers
                Headers responseHeaders = t.getResponseHeaders();

                // Send RESPONSE Headers
                t.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                // RESPONSE Body
                OutputStream os = t.getResponseBody();

                os.write(GetDateFormatString().getBytes());

                t.close();
            }

            // GET RESQUEST
            else{
                t.sendResponseHeaders(200,GetDateFormatString().length());
                OutputStream os = t.getResponseBody();
                os.write(GetDateFormatString().getBytes());
                os.close();
            }
        }
    }

    private void SetDateFormatString(String newTime){
        try {
            Date hisTime = format.parse(newTime);
            delta = hisTime.getTime()  - new Date().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String GetDateFormatString(){
        long d = currentTime.getTime() + delta;
        Date temp = (Date) currentTime.clone();
        temp.setTime(d);
        return format.format(temp);
    }
}
