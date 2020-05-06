import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread{
    protected Socket socket;
    ClientThread(Socket clientSocket){
        this.socket=clientSocket;
    }
    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                }
                
                if(line.equalsIgnoreCase("users")){
                    FireBaseData data=new FireBaseData();
                    ListUsersPage page=data.allusers();
                    for (ExportedUserRecord user : page.iterateAll()) {
                        out.writeBytes(user.getUid() + "name:" + user.getDisplayName()+System.lineSeparator());
                        out.flush();
                        //System.out.println("User: " + user.getUid() + "name: " + user.getDisplayName());
                    }
                }

            } catch (SocketException e){
                if(socket.isClosed()){
                e.printStackTrace();

                }
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
