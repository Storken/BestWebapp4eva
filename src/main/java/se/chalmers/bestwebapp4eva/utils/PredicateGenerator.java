/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.utils;

import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import se.chalmers.bestwebapp4eva.entity.BasicEntity_;

/**
 *
 * @author simon
 */
public class PredicateGenerator {
    
    private static final String[] logicalOperators = new String[]{"<", ">", "<=", ">=", "="};
    private CriteriaBuilder cb;
    private Map<String, Object> filters;
    private Root root;
    
    public PredicateGenerator(CriteriaBuilder cb, Map<String, Object> filters, Root root) {
        this.cb = cb;
        this.filters = filters;
        this.root = root;
    }
    
    public Predicate getPredicate() {
        // Loop through all filter entries. For each filter, do CriteriaBuilder.and(x,y) to add condition to the filter.
        Predicate filterCondition = cb.conjunction();
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            if (!filter.getValue().equals("")) {
                Path<String> pathFilter = getStringPath(filter.getKey(), root);

                // If the attribute the filter is pointing to is a string (or enum). Filter by using SQL LIKE operator.      
                if (pathFilter != null) {
                        filterCondition = cb.and(filterCondition, cb.like(cb.lower(pathFilter), "%" + filter.getValue().toString().toLowerCase() + "%"));
                // If the attribute the filter is pointing to isn't a string (id, quantity, price etc). Filter by using SQL = operator. Exact filtering.
                } else {
                    Path<?> pathFilterNonString = getWildcardPath(filter.getKey(), root);
                    
                    String operator = checkForLogicalOperators(filter.getValue().toString());
                    
                    // If filter contains logical operators
                    if(operator != null) {
                        switch(operator) {
                            case "<":
                               if(pathFilterNonString.getClass().equals(Double.class))
                                   filterCondition = cb.and(filterCondition, cb.lt((Path<Double>)pathFilterNonString, Double.parseDouble(filter.getValue().toString().replaceAll("\\D+",""))));
                               else
                                   filterCondition = cb.and(filterCondition, cb.lt((Path<Long>)pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+",""))));
                                break;
                            case ">":
                                if(pathFilterNonString.getClass().equals(Double.class))
                                   filterCondition = cb.and(filterCondition, cb.gt((Path<Double>)pathFilterNonString, Double.parseDouble(filter.getValue().toString().replaceAll("\\D+",""))));
                               else
                                   filterCondition = cb.and(filterCondition, cb.gt((Path<Long>)pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+",""))));
                                break;
                            case "<=":
                                if(pathFilterNonString.getClass().equals(Double.class))
                                   filterCondition = cb.and(filterCondition, cb.le((Path<Double>)pathFilterNonString, Double.parseDouble(filter.getValue().toString().replaceAll("\\D+",""))));
                               else
                                   filterCondition = cb.and(filterCondition, cb.le((Path<Long>)pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+",""))));
                                break;
                            case ">=":
                                if(pathFilterNonString.getClass().equals(Double.class))
                                   filterCondition = cb.and(filterCondition, cb.ge((Path<Double>)pathFilterNonString, Double.parseDouble(filter.getValue().toString().replaceAll("\\D+",""))));
                               else
                                   filterCondition = cb.and(filterCondition, cb.ge((Path<Long>)pathFilterNonString, Long.parseLong(filter.getValue().toString().replaceAll("\\D+",""))));
                                break;
                            case "=":
                                // do nothing, jump to else block and perform equal.
                                break;
                        }
                    }else{
                    
                    filterCondition = cb.and(filterCondition, cb.equal(pathFilterNonString, filter.getValue()));
                }
            }
        }}
        
        return filterCondition;
    }
    
    // Method for getting a Path<?> (wildcard) to an attribute of BasicEntity
    public Path<?> getWildcardPath(String field, Root basicEntity) {
        
        Path<?> path = null;

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
                case "unit":
                    path = basicEntity.get(BasicEntity_.unit);
                    break;
            }
        }

        return path;
    }

    // Method for getting a Path to a String attribute of BasicEntity
    private Path<String> getStringPath(String field, Root basicEntity) {
        Path<String> path = null;

        if (field == null) {
            path = basicEntity.get(BasicEntity_.title);
        } else {
            switch (field) {
                case "title":
                    path = basicEntity.get(BasicEntity_.title);
                    break;
                case "unit":
                    path = basicEntity.get(BasicEntity_.unit);
                    break;
            }
        }

        return path;
    }
    
    private String checkForLogicalOperators(String filterValue) {
        String operator = null;
        for(int i = 0; i < logicalOperators.length; i++) {
            // TODO can't handle double operators, "between logic"
            if(filterValue.replaceAll("[0-9]","").equals(logicalOperators[i]) && operator == null)
                operator = logicalOperators[i];
        }
        
        return operator;
    }
}
