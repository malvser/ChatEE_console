package malov.serg.ChatEE_console_Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class UserServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream is=req.getInputStream();
        //----------------------------------
        byte[] buf=new byte[req.getContentLength()];
        is.read(buf);
        User user=User.fromJSON(new String(buf));
        String param=parseParam(req.getQueryString());
        //--------------------------------------------------
        gResp(UserList.findUserSetStatus(user,param),resp.getOutputStream());
        is.close();
    }

    private String parseParam(String param){
        String[] p=param.split("=");
        return p[1];
    }

    private void gResp(Boolean b,OutputStream os){
        try {
            try {
                os.write(toJson(b).getBytes());
            } finally {
                os.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p=req.getParameter("userList");
        if(p.equalsIgnoreCase("all")){
            OutputStream os=resp.getOutputStream();
            UserList ul=UserList.getInstance();
            try {
                os.write(ul.toJsonNoPass().getBytes());
            }finally {
                os.close();
            }
        }
    }

    private static <E>String toJson(E obj){
        Gson gson=new GsonBuilder().create();
        return gson.toJson(obj);
    }
}
