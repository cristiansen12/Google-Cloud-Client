package com.posdclient.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public interface CustomRoleService {

    public List<String> readFromFilePermissions(String fileName);
    public Map<String, Object> createCustom1();
    public Map<String, Object> createCustom2();
    public void undeleteCustomRole(int roleId) throws  IOException, GeneralSecurityException;
    public void createCustomRole (int roleId) throws IOException, GeneralSecurityException;
    public void deleteCustomRole (int roleId) throws IOException, GeneralSecurityException;
}
