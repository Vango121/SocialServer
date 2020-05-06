import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;

public class FireBaseData {
    ListUsersPage allusers() {
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

        return page;
    }

}
