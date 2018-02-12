package com.posdclient.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.posdclient.service.AuthManagerService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Service("authManagerService")
public class AuthManagerServiceImpl implements AuthManagerService {

    public AuthManagerServiceImpl() {
    }

    public GoogleCredential createGoogleCrendetial()
            throws IOException, GeneralSecurityException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("C:\\Users\\gevlad\\Documents\\Stuff\\University\\posdclient\\src\\main\\resources\\client_secret.json"));
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        return credential;
    }
}
