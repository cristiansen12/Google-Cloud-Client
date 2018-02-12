package com.posdclient.service;

import com.google.api.services.cloudresourcemanager.model.Policy;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface PolicyService {
    String createVersion1() throws IOException, GeneralSecurityException;
    String createVersion2() throws IOException, GeneralSecurityException;
    Policy createPolicy(String[] myViewers, int roleId) throws IOException, GeneralSecurityException;
    String deleteNewPolicy(int Roleid) throws IOException, GeneralSecurityException;
}

