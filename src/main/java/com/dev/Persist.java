package com.dev;

import com.dev.objects.*;
import com.dev.utils.Utils;
import org.apache.catalina.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.regionmetadata.SaEast1;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Persist {
    private Connection connection;

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist (SessionFactory sf) {
        this.sessionFactory = sf;
    }



    @PostConstruct
    public void createConnectionToDatabase () {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/discountssite", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getTokenByUsernameAndPassword(String username, String password){
        String token;
        Session session = sessionFactory.openSession();
        if (!this.doesUsernameExist(username))
            token = "usernameDoesntExist";
        else{
            UserObject userObject = (UserObject)
                    session
                            .createQuery("FROM UserObject u WHERE u.username = :username AND u.password = :password")
                            .setParameter("username",username)
                            .setParameter("password",password)
                            .uniqueResult();
            if (userObject == null)
                token = "passwordIncorrect";
            else
                token = userObject.getToken();
         }
        return token;
    }

    public boolean doesUsernameExist(String username){
        boolean isExist = false;
        Session session = sessionFactory.openSession();
        UserObject userObject =  (UserObject)
                session
                        .createQuery("FROM UserObject u WHERE u.username = :username")
                        .setParameter("username",username)
                        .uniqueResult();
        if (userObject != null)
            isExist = true;
        return isExist;
    }

    public String createAccount(String username,String password){
        String success = "failed";
        Session session = sessionFactory.openSession();
        //check if username exist or not. success id failed if it inserted into the if and something got wrong
        if (!this.doesUsernameExist(username)){
            Transaction transaction = session.beginTransaction();
            UserObject userObject = new UserObject();
            userObject.setUsername(username);
            userObject.setPassword(password);
            userObject.setToken(Utils.createHash(username,password));
            session.saveOrUpdate(userObject);
            success = userObject.getToken();
            transaction.commit();
            session.close();
        }
        else if (this.doesUsernameExist(username))
            success = "usernameExist";
        //return "failed" if something got wrong, token if succeed and "usernameExist" if username is taken
        return success;
    }

    public List<Organization> getAllOrganizations(){
        Session session = sessionFactory.openSession();
        List<Organization> organizations = session
                .createQuery("FROM Organization")
                .list();
        return organizations;
    }

    public List<Organization> getUserOrganization(String token){
        Session session = sessionFactory.openSession();
        List<UserToOrganization> userToOrganizations = session
                .createQuery("FROM UserToOrganization us WHERE us.userObject.token = :token")
                .setParameter("token",token)
                .list();
        List<Organization> organizations = new ArrayList<Organization>();
        for (UserToOrganization userToOrganization : userToOrganizations )
            organizations.add(userToOrganization.getOrganization());
        return organizations;
    }

    public List<Shop> getAllShops(){
        Session session = sessionFactory.openSession();
        List<Shop> shops = session
                .createQuery("FROM Shop")
                .list();
        return shops;
    }

    public boolean editUserToOrganization (String token, String organizationName){
        boolean success = false;
        Session session = sessionFactory.openSession();
        UserToOrganization userToOrganization = (UserToOrganization)
                session
                        .createQuery("FROM UserToOrganization us WHERE us.userObject.token = :token AND us.organization.organizationName = :organizationName")
                        .setParameter("token",token)
                        .setParameter("organizationName",organizationName)
                        .uniqueResult();
        UserObject userObject = (UserObject)
                session
                        .createQuery("FROM UserObject u WHERE u.token = :token")
                        .setParameter("token",token)
                        .uniqueResult();
        Organization organization = (Organization)
                session
                        .createQuery("FROM Organization o WHERE o.organizationName = :organizationName")
                        .setParameter("organizationName",organizationName)
                        .uniqueResult();
        if (userObject != null && organization != null){
            if (userToOrganization == null){
                Transaction transaction = session.beginTransaction();
                UserToOrganization userToOrganization1 = new UserToOrganization();
                userToOrganization1.setUserObject(userObject);
                userToOrganization1.setOrganization(organization);
                session.saveOrUpdate(userToOrganization1);
                success = true;
                transaction.commit();
                session.close();
            }
            else{
                Transaction transaction = session.beginTransaction();
                session.delete(userToOrganization);
                success = true;
                transaction.commit();
                session.close();
            }
        }
        return success;
    }

    public List<Sale> getAllSales(){
        Session session = sessionFactory.openSession();
        List<Sale> sales = session
                .createQuery("FROM Sale ")
                .list();
        return sales;
    }

    public List<Sale> getUserSales(String token){
        Session session = sessionFactory.openSession();
        List<Organization> organizations = this.getUserOrganization(token);

        List<Sale> sales = new ArrayList<>();
        for (Organization organization : organizations){
            sales.add((Sale)
                    session
                            .createQuery("SELECT Sale FROM SaleToOrganization so WHERE so.organization = :organization")
                            .setParameter("organization", organization)
                            .uniqueResult()
            );
        }

        return sales;
    }

    public List<Sale> getShopSales(int shopId){
        Session session = sessionFactory.openSession();
        List<Sale> sales = session
                .createQuery("FROM Sale s WHERE s.shop.id = :shopId")
                .setParameter("shopId", shopId)
                .list();
        return sales;
    }

    public Shop getShopById(int shopId){
        Session session = sessionFactory.openSession();
        Shop shop = (Shop)
                session
                        .createQuery("FROM Shop s WHERE s.id = :shopId")
                        .setParameter("shopId", shopId)
                        .uniqueResult();
        return shop;
    }

}
