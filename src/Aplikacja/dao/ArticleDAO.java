package Aplikacja.dao;
 
import java.util.List;

import Aplikacja.model.Article;

public interface ArticleDAO extends GenericDAO<Article, Long>{
 
    List<Article> getAll();
     
}