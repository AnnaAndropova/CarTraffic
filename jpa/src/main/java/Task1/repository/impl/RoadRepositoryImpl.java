package Task1.repository.impl;

import Task1.domain.entity.Road;
import Task1.repository.RoadCellRepository;
import Task1.repository.RoadRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoadRepositoryImpl implements RoadRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Road> get(Long id) {
        Road road = em.find(Road.class, id);
//        Session session = sessionFactory.openSession();
//        Road res = session.get(Road.class, id);
//        session.close();
        return Optional.of(road);
    }

    @Override
    public List<Road> getAll() {
        TypedQuery<Road> roadTypedQuery = em.createQuery("select r from Road r", Road.class);
//        Session session = sessionFactory.openSession();
//        Query<Road> query = session.createQuery("from Road", Road.class);
//        List<Road> res = query.getResultList();
//        session.close();
        return roadTypedQuery.getResultList();
    }

    @Override
    public void save(Road entity) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(entity);
//        transaction.commit();
//        session.close();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(Road road) {
        em.merge(road);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        session.update(road);
//        transaction.commit();
//        session.close();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert(Road newRoad) {
        em.persist(newRoad);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
//        session.persist(newRoad);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void clear() {
        List<Road> roads = getAll();
        for (Road road : roads) {
            em.remove(road);
        }
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createQuery("delete from Road").executeUpdate();
//            transaction.commit();
//            session.close();
//        } catch (SQLGrammarException ignored) {
//        }
    }
}
