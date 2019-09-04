package com.fumiya.springboot;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@SuppressWarnings("rawtypes")
@Repository
public class MsgDataDaoImpl implements MsgDataDao<MsgDataDao> {
    private EntityManager entityManager;

    public MsgDataDaoImpl() {
        super();
    }

    public MsgDataDaoImpl(EntityManager manager) {
        this();
        entityManager = manager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MsgData> getAll() {
        return entityManager.createQuery("from MsgData").getResultList();
    }

    @Override
    public MsgData findById(long id) {
        return (MsgData)entityManager.createQuery("from MsgData where id = " + id).getSingleResult();
    }
}
