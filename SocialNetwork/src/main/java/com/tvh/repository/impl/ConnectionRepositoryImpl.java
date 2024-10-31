/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.repository.impl;

import com.google.cloud.firestore.Firestore;
import com.tvh.pojo.Comment;
import com.tvh.pojo.Notification;
import com.tvh.pojo.User;
import com.tvh.pojo.UserConnection;
import com.tvh.repository.ConnectionRepository;
import com.tvh.repository.UserReppository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hoangtrinh
 */
@Repository
@Transactional
public class ConnectionRepositoryImpl implements ConnectionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserReppository userRepo;

    @Override
    public List<UserConnection> getConnection(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        javax.persistence.Query query = session.createQuery("FROM UserConnection WHERE userId.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public boolean addConnection(int connectionId) {
        Session session = this.factory.getObject().getCurrentSession();
        UserConnection userConnection = new UserConnection();
        Notification noti = new Notification();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User u = this.userRepo.getUserByUsername(authentication.getName());
            User c = this.userRepo.getUserById(connectionId);
            userConnection.setUserId(u);
            userConnection.setCreatedDate(new Date());
            userConnection.setConnectionId(c);
            userConnection.setConnectStatus("REQUEST");

            session.save(userConnection);

            noti.setContent("Bạn nhận được 1 lời mời kết bạn");
            noti.setCreatedDate(new Date());
            noti.setUser(c);
            noti.setStatus("ACTIVE");
            session.save(noti);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public UserConnection getConnectionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        javax.persistence.Query q = session.createQuery("FROM UserConnection WHERE id=:id");
        q.setParameter("id", id);
        return (UserConnection) q.getSingleResult();
    }

    @Override
    public boolean acceptConnection(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        UserConnection connection = this.getConnectionById(id);
        try {
            connection.setConnectStatus("ACCEPT");
            session.update(connection);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    @Override
//    public boolean delConnection(int id) {
//        Session session = this.factory.getObject().getCurrentSession();
//        UserConnection connection = this.getConnectionById(id);
//        try {
//            connection.setConnectStatus("DENY");
//            session.update(connection);
//            return true;
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }

    @Override
    public boolean delConnection(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        // Lấy user hiện tại đang đăng nhập (authentication)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());

        try {
            // Tìm UserConnection với userId và connectionId
            Query<UserConnection> query = s.createQuery("FROM UserConnection WHERE ((userId.id = :userId AND connectionId.id = :connectionId) OR (userId.id = :connectionId AND connectionId.id = :userId)) AND (connectStatus = 'ACCEPT' OR connectStatus = 'REQUEST') ");
            query.setParameter("userId", u.getId());
            query.setParameter("connectionId", id);
            UserConnection connection = query.uniqueResult();
            if (connection != null) {
                connection.setConnectStatus("DENY");
                s.update(connection);
                return true;
            } else {
                return false;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserConnection> getRequestConnectionReceipt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());

        Session session = this.factory.getObject().getCurrentSession();

        javax.persistence.Query query = session.createQuery("FROM UserConnection WHERE connectionId.id = :id AND connectStatus ='REQUEST' ");
        query.setParameter("id", u.getId());
        return query.getResultList();
    }

    @Override
    public List<UserConnection> getRequestConnectionSend() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());
        Session session = this.factory.getObject().getCurrentSession();
        javax.persistence.Query query = session.createQuery("FROM UserConnection WHERE userId.id = :id AND connectStatus ='REQUEST' ");
        query.setParameter("id", u.getId());
        return query.getResultList();
    }

    @Override
    public List<UserConnection> getAcceptConnections() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());

        Session session = this.factory.getObject().getCurrentSession();

        javax.persistence.Query query = session.createQuery("SELECT DISTINCT u "
                + "FROM User u "
                + "JOIN UserConnection uc ON (uc.userId.id = u.id OR uc.connectionId.id = u.id) "
                + "WHERE (uc.userId.id = :userId OR uc.connectionId.id = :userId) "
                + "AND uc.connectStatus = 'ACCEPT' "
                + "AND u.id != :userId");
        query.setParameter("userId", u.getId());
        return query.getResultList();
    }

    @Override
    public List<UserConnection> getConnections(int userId) {
        User u = this.userRepo.getUserById(userId);
        Session session = this.factory.getObject().getCurrentSession();
        javax.persistence.Query query = session.createQuery("SELECT u, uc "
                + "FROM User u "
                + "JOIN UserConnection uc ON (u.id = uc.userId.id OR u.id = uc.connectionId.id) "
                + "WHERE (uc.userId.id = :userId OR uc.connectionId.id = :userId) "
                + "AND u.id != :userId");
        query.setParameter("userId", u.getId());
        return query.getResultList();
    }

    @Override
    public boolean deleteConnection(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        UserConnection connection = this.getConnectionById(id);
        try {
            s.delete(connection);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
