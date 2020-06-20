package Aplikacja.service;
 
import java.sql.Timestamp;
import java.util.Date;
 
import Aplikacja.dao.DAOFactory;
import Aplikacja.dao.VoteDAO;
import Aplikacja.model.Vote;
import Aplikacja.model.VoteType;
 
public class VoteService {
    public Vote addVote(long articleId, long userId, VoteType voteType) {
        Vote vote = new Vote();
        vote.setArticleId(articleId);
        vote.setUserId(userId);
        vote.setDate(new Timestamp(new Date().getTime()));
        vote.setVoteType(voteType);
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDao = factory.getVoteDAO();
        vote = voteDao.create(vote);
        return vote;
    }
     
    public Vote updateVote(long articleId, long userId, VoteType voteType) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDao = factory.getVoteDAO();
        Vote voteToUpdate = voteDao.getVoteByUserIdArticleId(userId, articleId);
        if(voteToUpdate != null) {
            voteToUpdate.setVoteType(voteType);
            voteDao.update(voteToUpdate);
        }
        return voteToUpdate;
    }
     
    public Vote addOrUpdateVote(long articleId, long userId, VoteType voteType) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDao = factory.getVoteDAO();
        Vote vote = voteDao.getVoteByUserIdArticleId(userId, articleId);
        Vote resultVote = null;
        if(vote == null) {
            resultVote = addVote(articleId, userId, voteType);
        } else {
            resultVote = updateVote(articleId, userId, voteType);
        }
        return resultVote;
    }
     
    public Vote getVoteByArticleUserId(long articleId, long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        VoteDAO voteDao = factory.getVoteDAO();
        Vote vote = voteDao.getVoteByUserIdArticleId(userId, articleId);
        return vote;
    }
}
