package com.posdclient.service.Impl;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.cloudresourcemanager.CloudResourceManager;
import com.google.api.services.cloudresourcemanager.model.Binding;
import com.google.api.services.cloudresourcemanager.model.GetIamPolicyRequest;
import com.google.api.services.cloudresourcemanager.model.Policy;
import com.google.api.services.cloudresourcemanager.model.SetIamPolicyRequest;
import com.posdclient.service.AuthManagerService;
import com.posdclient.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service("policyService")
public class PolicyServiceImpl implements PolicyService{

    @Autowired
    AuthManagerService authManagerService;

    public PolicyServiceImpl() {
    }

    public String createVersion1() throws IOException, GeneralSecurityException{
        String[] myViewers1 = new String[] {"user:cristigvlad@gmail.com"};
        return createPolicy(myViewers1, 0).toString();
    }

    public String createVersion2() throws IOException, GeneralSecurityException{
        String[] myViewers2 = new String[] {"user:vladgcristian@gmail.com"};
        return createPolicy(myViewers2, 1).toString();
    }



    public Policy createPolicy(String[] myViewers, int roleId) throws IOException, GeneralSecurityException {
        // REQUIRED: The resource for which the policy is being specified.
        // See the operation documentation for the appropriate value for this field.
        String resource = "posd-188513"; // TODO: Update placeholder value.

        String targetRole = null;

        if (roleId == 0)
            targetRole = "roles/compute.instanceAdmin";
        else
            targetRole = "roles/compute.viewer";

        // TODO: Assign values to desired fields of `requestBody`:
        GetIamPolicyRequest requestBody = new GetIamPolicyRequest();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        CloudResourceManager cloudResourceManagerService =
                new CloudResourceManager.Builder(httpTransport, jsonFactory, authManagerService.createGoogleCrendetial())
                .setApplicationName("Google-CloudResourceManagerSample/0.1")
                .build();

        CloudResourceManager.Projects.GetIamPolicy request =
                cloudResourceManagerService.projects().getIamPolicy(resource, requestBody);

        Policy response = request.execute();

        Binding targetBinding = null;

        // Make a local copy of the bindings for modifying
        LinkedList<Binding> bindings =
                new LinkedList<Binding>(response.getBindings());

        // Search for the existing binding having role name of
        // targetRole.
        for (Binding binding : bindings) {
            if (binding.getRole().equals(targetRole)) {
                targetBinding = binding;
                break;
            }
        }
        // If no matching targetBinding is found, construct a new Binding object,
        // and add it to the bindings list.
        if (targetBinding == null) {
            targetBinding = new Binding();
            targetBinding.setRole(targetRole);
            targetBinding.setMembers(Arrays.asList(myViewers));
            bindings.add(targetBinding);
        }
        else {
            List<String> users = targetBinding.getMembers();
            for (String user:myViewers){
                if (!users.contains(user))
                    users.add(user);
            }
            targetBinding.setMembers(users);
        }

        // Finally, set the list of members as the members of targetBinding.


        // Write the policy back into the project by calling SetIamPolicy.
        SetIamPolicyRequest setIamPolicyRequest = new SetIamPolicyRequest();
        setIamPolicyRequest.setPolicy(response.setBindings(bindings));
        Policy p = cloudResourceManagerService.projects().setIamPolicy(resource, setIamPolicyRequest).execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(p);

        return p;

    }

    public String deleteNewPolicy(int roleId) throws IOException, GeneralSecurityException {
        // REQUIRED: The resource for which the policy is being specified.
        // See the operation documentation for the appropriate value for this field.
        String resource = "posd-188513"; // TODO: Update placeholder value.

        String targetRole = null;

        if (roleId == 0)
            targetRole = "roles/compute.instanceAdmin";
        else
            targetRole = "roles/posd.roleViewerInstance";

        // TODO: Assign values to desired fields of `requestBody`:
        GetIamPolicyRequest requestBody = new GetIamPolicyRequest();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        CloudResourceManager cloudResourceManagerService =
                new CloudResourceManager.Builder(httpTransport, jsonFactory, authManagerService.createGoogleCrendetial())
                        .setApplicationName("Google-CloudResourceManagerSample/0.1")
                        .build();

        CloudResourceManager.Projects.GetIamPolicy request =
                cloudResourceManagerService.projects().getIamPolicy(resource, requestBody);

        Policy response = request.execute();

        Binding targetBinding = null;

        // Make a local copy of the bindings for modifying
        LinkedList<Binding> bindings =
                new LinkedList<Binding>(response.getBindings());

        // Search for the existing binding having role name of
        // targetRole.
        for (Binding binding : bindings) {
            if (binding.getRole().equals(targetRole)) {
                targetBinding = binding;
                break;
            }
        }
        // If no matching targetBinding is found, construct a new Binding object,
        // and add it to the bindings list.
        if (targetBinding != null) {
            bindings.remove(targetBinding);
        }

        // Finally, set the list of members as the members of targetBinding.


        // Write the policy back into the project by calling SetIamPolicy.
        SetIamPolicyRequest setIamPolicyRequest = new SetIamPolicyRequest();
        setIamPolicyRequest.setPolicy(response.setBindings(bindings));
        Policy p = cloudResourceManagerService.projects().setIamPolicy(resource, setIamPolicyRequest).execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(p);

        return p.toString();

    }

}
