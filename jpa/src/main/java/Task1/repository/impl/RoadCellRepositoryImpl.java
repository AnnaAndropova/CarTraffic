package Task1.repository.impl;

import Task1.domain.entity.Car;
import Task1.domain.entity.RoadCell;
import Task1.repository.RoadCellRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class RoadCellRepositoryImpl implements RoadCellRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public RoadCell get(Integer id) {
        RoadCell roadCell = em.find(RoadCell.class, id);
//        Session session = sessionFactory.openSession();
//        RoadCell res = session.get(RoadCell.class, id);
//        session.close();
        return roadCell;
    }

    @Override
    public List<RoadCell> getAll() {
        TypedQuery<RoadCell> roadCellTypedQuery = em.createQuery("select rc from RoadCell rc order by rc.id", RoadCell.class);
//        Session session = sessionFactory.openSession();
//        Query<RoadCell> query = session.createQuery("from RoadCell rc order by rc.id", RoadCell.class);
//        List<RoadCell> result = query.getResultList();
//        session.close();
        return roadCellTypedQuery.getResultList();
    }

    @Override
    public List<RoadCell> getAllByCar(Car car) {
        TypedQuery<RoadCell> roadCellTypedQuery = em.createQuery("select rc from RoadCell rc where rc.car = :car", RoadCell.class).setParameter("car", car);
        return roadCellTypedQuery.getResultList();
    }

    @Transactional
    @Override
    public void update(RoadCell entity) {
        em.merge(entity);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        session.update(entity);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void insert(RoadCell newEntity) {
        em.persist(newEntity);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
//        session.persist(newEntity);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void findAndClear(Car entity) {
        List<RoadCell> roadCells = getAllByCar(entity);
        if (!roadCells.isEmpty()) {
            for (RoadCell roadCell : roadCells) {
                roadCell.setCar(null);
                em.merge(roadCell);
            }
        }
//        Session session = sessionFactory.openSession();
//        Query<RoadCell> query = session.createQuery("from RoadCell rc where rc.car = :car", RoadCell.class);
//        query.setParameter("car", entity);
//        List<RoadCell> cells = query.getResultList();
//        if (!cells.isEmpty()) {
//            for (RoadCell cell : cells) {
//                Transaction transaction = session.beginTransaction();
//                cell.setCar();
//                update(cell);
//                transaction.commit();
//            }
//        }
//        session.close();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void clear() {
        List<RoadCell> roadCells = getAll();
        for (RoadCell roadCell : roadCells) {
            em.remove(roadCell);
        }
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createQuery("delete from RoadCell").executeUpdate();
//            transaction.commit();
//        } catch (SQLGrammarException ignored) {
//        }
    }
}
