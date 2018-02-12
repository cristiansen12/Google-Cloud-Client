package com.posdclient.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.posdclient.service.AuthManagerService;
import com.posdclient.service.CustomRoleService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.*;


@Service("customRoleServide")
public class CustomRoleServiceImpl implements CustomRoleService {

    @Autowired
    AuthManagerService authManagerService;


    public List<String> readFromFilePermissions(String fileName){
        List<String> includedPermissions = new ArrayList<>();
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream("C:\\Users\\gevlad\\Documents\\Stuff\\University\\posdclient\\src\\main\\resources\\" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        try {
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                includedPermissions.add(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return includedPermissions;

    }

    public Map<String, Object> createCustom1(){
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> role = new HashMap<String, Object>();
        List<String> includedPermissions = new ArrayList<>();

        includedPermissions = readFromFilePermissions("computeInstanceAdminV1");
        map.put("role", role);
        role.put("description", "This role grants computer access to Compute Engine");
        role.put("includedPermissions", includedPermissions);
        role.put("title", "POSD-RoleAdminInstance");
        role.put("name", "");
        map.put("roleId", "posd.roleAdminInstance");

        return map;
    }


    public Map<String, Object> createCustom2(){
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> role = new HashMap<String, Object>();
        List<String> includedPermissions = new ArrayList<>();
        includedPermissions = readFromFilePermissions("viewer");

        map.put("role", role);
        role.put("description", "This role grants owner access to Compute Engine");
        role.put("includedPermissions", includedPermissions);
        role.put("title", "POSD-RoleViewerInstance");
        role.put("name", "");
        map.put("roleId", "posd.roleViewerInstance");

        return map;
    }


    public void createCustomRole (int roleId) throws IOException, GeneralSecurityException {
        String resource = "posd-188513"; // TODO: Update placeholder value.
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        String addr = "https://iam.googleapis.com/v1/projects/" + resource + "/roles";
        GenericUrl url = new GenericUrl(addr);
        GoogleCredential credential = authManagerService.createGoogleCrendetial();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        Map<String, Object> map = new HashMap<>();

        if(roleId == 0)
            map = createCustom1();
        else
            map = createCustom2();

        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildPostRequest(url, new JsonHttpContent(new JacksonFactory(), map)).execute();
        String result = IOUtils.toString(response.getContent(), StandardCharsets.UTF_8.name());
        System.out.println(result);
    }



    public void undeleteCustomRole(int roleId) throws  IOException, GeneralSecurityException {
        String resource = "posd-188513"; // TODO: Update placeholder value.
        String role = null;

        if( roleId == 0)
            role = "posd.roleAdminInstance";
        else
            role = "posd.roleViewerInstance";

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        String addr = "https://iam.googleapis.com/v1/projects/" + resource + "/roles/" + role + ":undelete";
        GenericUrl url = new GenericUrl(addr);
        GoogleCredential credential = authManagerService.createGoogleCrendetial();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        Map<String, String> map = new HashMap<>();
        map.put("etag", "");
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildPostRequest(url, new JsonHttpContent(new JacksonFactory(), map)).execute();
        String result = IOUtils.toString(response.getContent(), StandardCharsets.UTF_8.name());
        System.out.println(result);

    }


    public void deleteCustomRole (int roleId) throws IOException, GeneralSecurityException {
        String resource = "posd-188513"; // TODO: Update placeholder value.
        String role = null;

        if( roleId == 0)
            role = "posd.roleAdminInstance";
        else
            role = "posd.roleViewerInstance";

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        String addr = "https://iam.googleapis.com/v1/projects/" + resource + "/roles/" + role;
        GenericUrl url = new GenericUrl(addr);
        GoogleCredential credential = authManagerService.createGoogleCrendetial();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildDeleteRequest(url).execute();
        String result = IOUtils.toString(response.getContent(), StandardCharsets.UTF_8.name());
        System.out.println(result);
    }


}
