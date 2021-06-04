package Task1.repository.impl;

import Task1.domain.entity.Car;
import Task1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CarRepositoryImpl implements CarRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Car> get(Integer id) {
        Car car = em.find(Car.class, id);
//        Session session = sessionFactory.openSession();
//        Car res = session.get(Car.class, id);
//
//        session.close();
        return Optional.of(car);
    }

    @Override
    public List<Car> getAll() {
        TypedQuery<Car> carTypedQuery = em.createQuery("select c from Car c order by c.id", Car.class);
//        Session session = sessionFactory.openSession();
//        Query<Car> query = session.createQuery("from Car c order by c.id", Car.class);
//        List<Car> res = query.getResultList();
//
//        session.close();
        return carTypedQuery.getResultList();
    }

    @Override
    public Car getNext(Car entity) {
//        Session session = sessionFactory.openSession();
//        Query<Car> query = session.createQuery("from Car c where c.id > :id order by c.id", Car.class);
//        query.setParameter("id", entity.getId());
//        List<Car> res = query.getResultList();
//        return res.isEmpty() ? null : res.get(0);
        return null;
    }

    @Override
    public List<Car> getPrev(Car entity) {
//        Session session = sessionFactory.openSession();
//        Query<Car> query = session.createQuery("from Car c where c.id < :id order by c.id desc", Car.class);
//        query.setParameter("id", entity.getId());
//        return query.getResultList();
        return null;
    }

    @Transactional
    @Override
    public void update(Car car) {
        em.merge(car);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
//        session.merge(car);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void insert(Car newCar) {
        em.persist(newCar);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.getTransaction();
//        transaction.begin();
//        session.persist(newCar);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void remove(Car entity) {
        em.remove(entity);
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Car car = session.get(Car.class, entity.getId());
//        session.delete(car);
//        transaction.commit();
//        session.close();
    }

    @Transactional
    @Override
    public void clear() {
        List<Car> cars = getAll();
        for (Car car : cars) {
            em.remove(car);
        }
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            session.createQuery("DELETE from Car").executeUpdate();
//            transaction.commit();
//            session.close();
//        } catch (SQLGrammarException ignored) {
//        }
    }
}
