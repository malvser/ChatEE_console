package malov.serg.ChatEE_console_Server;

import java.io.IOException;
import java.io.InputStream;

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
        InputStream is=req.getInputStream();
        //--------------------------------
        byte[] buf=new byte[req.getContentLength()];
        is.read(buf);
        //--------------------------------
        Message message=Message.fromJSON(new String(buf));
        if(message!=null){
            msgList.add(message);
        }else{
            resp.setStatus(400);
        }
    }
}

