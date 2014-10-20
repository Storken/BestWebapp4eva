package se.chalmers.bestwebapp4eva.ctrl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Singleton
@Startup
public class EntityInitializer {

    @EJB
    IBasicEntityDAO basicEntityDAO;

    @EJB
    ICategoryDAO categoryDAO;

    @PostConstruct
    public void init() {
        if (categoryDAO.findAll().isEmpty() && categoryDAO.findAll().isEmpty()) {
            bulkAdd();
        }
    }

    private void bulkAdd() {

        List<Category> tmp = new ArrayList<>();
        tmp.add(new Category("Default", "Just a default category..."));
        tmp.add(new Category("Metal", "Things made out of metal."));
        tmp.add(new Category("Food", "Things you can eat."));
        tmp.add(new Category("Test Category", "Just a default category..."));
        tmp.add(new Category("iPhones", "Phones from Apple Inc."));
        tmp.add(new Category("Cars", "Cars, preferably Volvo."));
        tmp.add(new Category("Liquid", "Things that are liquid."));
        
        for(Category c : tmp) {
            categoryDAO.create(c);
        }
        
        basicEntityDAO.create(new BasicEntity("Screw", 25, 100, BasicEntity.Unit.pcs, tmp.get(1)));
        basicEntityDAO.create(new BasicEntity("Muppet", 1, 38, BasicEntity.Unit.kg, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Book", 32, 95, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Water", 0.1, 5679, BasicEntity.Unit.l, tmp.get(6)));
        basicEntityDAO.create(new BasicEntity("Pepsi", 1, 10, BasicEntity.Unit.l, tmp.get(6)));
        basicEntityDAO.create(new BasicEntity("iPhone 6", 99999, 4, BasicEntity.Unit.pcs, tmp.get(4)));
        basicEntityDAO.create(new BasicEntity("Volvo 740 (red)", 30000, 79, BasicEntity.Unit.pcs, tmp.get(5)));
        basicEntityDAO.create(new BasicEntity("Sakkurugame", 15, 178, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Milk", 2, 481, BasicEntity.Unit.l, tmp.get(6)));
        basicEntityDAO.create(new BasicEntity("Oil", 250, 18, BasicEntity.Unit.l, tmp.get(6)));
        basicEntityDAO.create(new BasicEntity("Hamburger meat", 58, 36, BasicEntity.Unit.kg, tmp.get(2)));
        basicEntityDAO.create(new BasicEntity("Teacher", 1, 1, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Apocalypse", 193, 670, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Keyboard", 58, 475, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Rice", 3, 150, BasicEntity.Unit.kg, tmp.get(2)));
        basicEntityDAO.create(new BasicEntity("iPhone 7", 999991, 78, BasicEntity.Unit.pcs, tmp.get(4)));
        basicEntityDAO.create(new BasicEntity("Volvo 240 DL (red)", 50000, 15, BasicEntity.Unit.pcs, tmp.get(5)));
        basicEntityDAO.create(new BasicEntity("Sakkurugame 2", 18, 6, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Juice", 4, 53, BasicEntity.Unit.l, tmp.get(6)));
        basicEntityDAO.create(new BasicEntity("Baby Powder", 679, 93, BasicEntity.Unit.l, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Hamburger bread", 58, 36, BasicEntity.Unit.kg, tmp.get(2)));
        basicEntityDAO.create(new BasicEntity("Pupil", 1, 1, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Thunderstorm", 193, 670, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Drums", 58, 475, BasicEntity.Unit.pcs, tmp.get(0)));
        basicEntityDAO.create(new BasicEntity("Sallad", 3, 150, BasicEntity.Unit.kg, tmp.get(2)));

    }
}
