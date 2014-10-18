package se.chalmers.bestwebapp4eva.utils;

import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity_;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.entity.Category_;

/**
 * Helper class to BasicEntityCollection for 
 * @author simon
 */
public class PredicateGenerator {

    private static final String[] logicalOperators = new String[]{"<", ">", "<=", ">=", "="};
    private final CriteriaBuilder cb;
    private final Map<String, Object> filters;
    private final Root root;

    public PredicateGenerator(CriteriaBuilder cb, Map<String, Object> filters, Root root) {
        this.cb = cb;
        this.filters = filters;
        this.root = root;
    }

    public Predicate getPredicate() {
        Predicate filterCondition = cb.conjunction();
        // Join needed in case a filter on category is applied.
        Join<BasicEntity, Category> category = root.join("category");
        
        // Loop through all filter entries. For each filter, do CriteriaBuilder.and(x,y) to add condition to the filter.
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            // If current filter value isn't empty
            if (!filter.getValue().equals("")) {
                Path<String> pathFilter;
                
                pathFilter = getStringAttrPath(filter.getKey(), root, category);

                // If the attribute the filter is pointing to is a string (or enum). Filter by using SQL LIKE operator.      
                if (pathFilter != null) {
                    filterCondition = cb.and(filterCondition, cb.like(cb.lower(pathFilter), "%" + filter.getValue().toString().toLowerCase() + "%"));
                    // If the attribute the filter is pointing to isn't a string...
                } else {
                    // Srip out operators and convert the filter value to a long,
                    Long numberValue = Long.parseLong(filter.getValue().toString().replaceAll("\\D+", ""));
                    
                    Path<Long> pathFilterNonString = getLongAttrPath(filter.getKey(), root);

                    String operator = checkForLogicalOperators(filter.getValue().toString());

                    // If filter contains logical operators
                    if (operator != null) {
                        switch (operator) {
                            case "<":
                                filterCondition = cb.and(filterCondition, cb.lt(pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+", ""))));
                                break;
                            case ">":
                                filterCondition = cb.and(filterCondition, cb.gt(pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+", ""))));
                                break;
                            case "<=":
                                filterCondition = cb.and(filterCondition, cb.le(pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+", ""))));
                                break;
                            case ">=":
                                filterCondition = cb.and(filterCondition, cb.ge(pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+", ""))));
                                break;
                            case "=":
                                // do nothing, jump to else block and perform equal.
                                break;
                        }
                    } else {

                        filterCondition = cb.and(filterCondition, cb.equal(pathFilterNonString, filter.getValue()));
                    }
                }
            }
        }

        return filterCondition;
    }

    // Get path to a number (in this case long) attribute.
    private Path<Long> getLongAttrPath(String field, Root basicEntity) {
        Path<Long> path = null;

        if (field == null) {
            path = basicEntity.get(BasicEntity_.title);
        } else {
            switch (field) {
                case "id":
                    path = basicEntity.get(BasicEntity_.id);
                    break;
                case "title":
                    path = basicEntity.get(BasicEntity_.title);
                    break;
                case "price":
                    path = basicEntity.get(BasicEntity_.price);
                    break;
                case "quantity":
                    path = basicEntity.get(BasicEntity_.quantity);
                    break;
            }
        }

        return path;
    }

    // Method for getting a Path to a String attribute of BasicEntity
    private Path<String> getStringAttrPath(String field, Root root, Join join) {
        Path<String> path = null;

        if (field == null) {
            path = root.get(BasicEntity_.title);
        } else {
            switch (field) {
                case "title":
                    path = root.get(BasicEntity_.title);
                    break;
                case "unit":
                    path = root.get(BasicEntity_.unit);
                    break;
                case "category.name":
                    path = join.get(Category_.name);
            }
        }

        return path;
    }

    private String checkForLogicalOperators(String filterValue) {
        String operator = null;
        for (int i = 0; i < logicalOperators.length; i++) {
            // TODO can't handle double operators, "between logic"
            if (filterValue.replaceAll("[0-9]", "").trim().equals(logicalOperators[i]) && operator == null) {
                operator = logicalOperators[i];
            }
        }

        return operator;
    }
}
