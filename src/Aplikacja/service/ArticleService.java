package Aplikacja.service;
 
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
 
import Aplikacja.dao.DAOFactory;
import Aplikacja.dao.ArticleDAO;
import Aplikacja.model.Article;
import Aplikacja.model.User;
 
public class ArticleService {
    public void addArticle(String name, String desc, String url, User user) {
        Article article = createArticleObject(name, desc, url, user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        ArticleDAO articleDao = factory.getArticleDAO();
        articleDao.create(article);
    }
     
    private Article createArticleObject(String name, String desc, String url, User user) {
        Article article = new Article();
        article.setName(name);
        article.setDescription(desc);
        article.setUrl(url);
        User userCopy = new User(user);
        article.setUser(userCopy);
        article.setTimestamp(new Timestamp(new Date().getTime()));
        return article;
    }
     
    public Article getArticleById(long articleId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        ArticleDAO articleDao = factory.getArticleDAO();
        Article article = articleDao.read(articleId);
        return article;
    }
     
    public boolean updateArticle(Article article) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        ArticleDAO articleDao = factory.getArticleDAO();
        boolean result = articleDao.update(article);
        return result;
    }
     
    public List<Article> getAllArticles() {
        return getAllArticles(null);
    }
     
    public List<Article> getAllArticles(Comparator<Article> comparator) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        ArticleDAO articleDao = factory.getArticleDAO();
        List<Article> articles = articleDao.getAll();
        if(comparator != null && articles != null) {
            articles.sort(comparator);
        }
        return articles;
    }
}