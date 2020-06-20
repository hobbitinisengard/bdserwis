package Aplikacja.dao;
 
public class MysqlDAOFactory extends DAOFactory {
 
    @Override
    public ArticleDAO getArticleDAO() {
        return new ArticleDAOImpl();
    }
 
    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
 
    @Override
    public VoteDAO getVoteDAO() {
        return new VoteDAOImpl();
    }
 
}