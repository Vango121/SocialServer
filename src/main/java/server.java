import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class server {
    static final int PORT = 2020;
    

    private static void authentication (){
        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream(key_adress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {

            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }
private static void allusers(){
    ListUsersPage page = null;
    try {
        page = FirebaseAuth.getInstance().listUsers(null);
    } catch (FirebaseAuthException e) {
        e.printStackTrace();
    }
    while (page != null) {
        for (ExportedUserRecord user : page.getValues()) {

        }
        page = page.getNextPage();
    }

// Iterate through all users. This will still retrieve users in batches,
// buffering no more than 1000 users in memory at a time.
    try {
        page = FirebaseAuth.getInstance().listUsers(null);
    } catch (FirebaseAuthException e) {
        e.printStackTrace();
    }
    for (ExportedUserRecord user : page.iterateAll()) {
        System.out.println("User: " + user.getUid()+"name: "+user.getDisplayName());
    }
}
    public static void main(String[] args) {
        authentication();
        allusers();
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            }catch (SocketException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            new ClientThread(socket).start();
        }
    }
}
