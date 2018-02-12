package com.posdclient.controller;

import com.posdclient.service.CustomRoleService;
import com.posdclient.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @Autowired
    PolicyService policyService;

    @Autowired
    CustomRoleService customRoleService;


    /** Policies part **/

    @RequestMapping(value = "/createVersion1", method = RequestMethod.GET)
    public String createVersion1(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        String policy = policyService.createVersion1();
        return "views/version1_create.html";
    }

    @RequestMapping(value = "/createVersion2", method = RequestMethod.GET)
    public String createVersion2(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        String policy = policyService.createVersion2();
        return "views/version2_create.html";
    }

    @RequestMapping(value = "/deleteVersion1", method = RequestMethod.GET)
    public String deleteVersion1(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        String policy = policyService.deleteNewPolicy(0);
        return "views/version1_delete.html";
    }

    @RequestMapping(value = "/deleteVersion2", method = RequestMethod.GET)
    public String deleteVersion2(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        String policy = policyService.deleteNewPolicy(1);
        return "views/version2_delete.html";
    }


    /** End Policies part **/

    /********************* ------------------ *********************/

    /** Custom Roles part **/

    @RequestMapping(value = "/createCustom1", method = RequestMethod.GET)
    public String createCustom1(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        customRoleService.createCustomRole(0);
        return "views/custom1_create.html";
    }

    @RequestMapping(value = "/createCustom2", method = RequestMethod.GET)
    public String createCustom2(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        customRoleService.createCustomRole(1);
        return "views/custom2_create.html";
    }


    @RequestMapping(value = "/deleteCustom1", method = RequestMethod.GET)
    public String deleteCustom1(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        customRoleService.deleteCustomRole(0);
        return "views/custom1_delete.html";
    }

    @RequestMapping(value = "/deleteCustom2", method = RequestMethod.GET)
    public String deleteCustom2(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        customRoleService.deleteCustomRole(1);
        return "views/custom2_delete.html";
    }

    @RequestMapping(value = "/unDeleteCustom1", method = RequestMethod.GET)
    public String unDeleteCustom1(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        customRoleService.undeleteCustomRole(0);
        return "views/custom1_unDelete.html";
    }



    /** End Custom Roles part **/

    /********************* ------------------ *********************/

    /** Homepage **/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage() {
        return "index.html";
    }
}
