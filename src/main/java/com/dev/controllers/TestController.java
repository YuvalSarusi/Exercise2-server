package com.dev.controllers;

import com.dev.Persist;
import com.dev.objects.Organization;
import com.dev.objects.Sale;
import com.dev.objects.Shop;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
public class TestController {


    @Autowired
    private Persist persist;

    @PostConstruct
    private void init () {

    }

    @RequestMapping(value = "create-account", method = RequestMethod.POST)
    public String createAccount (@RequestParam String username, String password) {
        return persist.createAccount(username,password);
    }

    @RequestMapping("get-token")
    public String getTokenByUsernameAndPassword(String username, String password){
        return persist.getTokenByUsernameAndPassword(username,password);
    }

    @RequestMapping(value = "edit-user-to-organization", method = RequestMethod.POST)
    public boolean editUserToOrganization (@RequestParam String token, String organizationName) {
        return persist.editUserToOrganization(token, organizationName);
    }

    @RequestMapping("get-all-shops")
    public List<Shop> getAllShops(){
        return persist.getAllShops();
    }

    @RequestMapping("get-all-organizations")
    public List<Organization> getAllOrganizations(){
        return persist.getAllOrganizations();
    }

    @RequestMapping("get-user-sales")
    public List<Sale> getUserSales(String token){
        return persist.getUserSales(token);
    }

    @RequestMapping("get-all-organizations-of-user")
    public List<Organization> getOrganizationOfUser(String token){
        return persist.getUserOrganization(token);
    }
}
