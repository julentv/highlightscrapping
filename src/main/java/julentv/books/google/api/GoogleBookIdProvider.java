package julentv.books.google.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksScopes;
import com.google.api.services.books.model.Volume;
import org.apache.commons.collections.CollectionUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleBookIdProvider {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File DATA_STORE_DIR = new java.io.File("C:\\datastore");

    public String getIdFromTitle(String bookTitle) throws GeneralSecurityException, IOException {
        Reader in = new FileReader(GoogleBookIdProvider.class.getResource("secrets.json").getFile());
        // Load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory =
                new FileDataStoreFactory(DATA_STORE_DIR);

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(new ApacheHttpTransport(), JSON_FACTORY, clientSecrets, Collections.singleton(BooksScopes.BOOKS))
                        .setDataStoreFactory(fileDataStoreFactory)
                        .setApprovalPrompt("force")
                        .build();
        //Browser open when there is new AuthorizationCodeInstalledApp(...)
        // Authorize
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                .setHttpRequestInitializer(credential)
                .build();

        return getAllBookIdsByTitle(books).get(bookTitle);
    }

    private static Map<String, String> getAllBookIdsByTitle(Books books) throws IOException {
        Map<String, String> idsByTitle = new HashMap<>();
        List<Volume> volumes;
        long index = 0;
        do {
            volumes = books.mylibrary().bookshelves().volumes().list("7").setStartIndex(index).execute().getItems();
            if (volumes != null) {
                for (final Volume volume : volumes) {
                    idsByTitle.put(volume.getVolumeInfo().getTitle(), volume.getId());
                }
                index += 10;
            }
        } while (CollectionUtils.isNotEmpty(volumes));
        return idsByTitle;
    }
}
