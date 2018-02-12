package com.posdclient.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthManagerService {
    GoogleCredential createGoogleCrendetial()
            throws IOException, GeneralSecurityException;
}
