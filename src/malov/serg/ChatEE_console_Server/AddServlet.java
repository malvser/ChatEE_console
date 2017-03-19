package malov.serg.ChatEE_console_Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "add", urlPatterns = "/add")
public class AddServlet extends HttpServlet {


    private MessageList msgList=MessageList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message message = Message.fromJSON(bufStr);
        if(message != null){
            msgList.add(message);
        }else{
            resp.setStatus(400);
        }
    }

    public byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}

