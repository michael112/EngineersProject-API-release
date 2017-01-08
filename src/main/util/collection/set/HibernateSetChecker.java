package main.util.collection.set;

import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;

public class HibernateSetChecker {

    public static boolean isNotHibernateCollection(Set set) {
        return !( set instanceof PersistentSet);
    }

}
