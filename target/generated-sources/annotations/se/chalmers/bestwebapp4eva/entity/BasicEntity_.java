package se.chalmers.bestwebapp4eva.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-10-13T17:37:02")
@StaticMetamodel(BasicEntity.class)
public class BasicEntity_ { 

    public static volatile SingularAttribute<BasicEntity, Unit> unit;
    public static volatile SingularAttribute<BasicEntity, Double> quantity;
    public static volatile SingularAttribute<BasicEntity, Double> price;
    public static volatile SingularAttribute<BasicEntity, Long> id;
    public static volatile SingularAttribute<BasicEntity, String> title;

}