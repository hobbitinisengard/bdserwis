package Aplikacja.dao;
 
import Aplikacja.model.Vote;

public interface VoteDAO extends GenericDAO<Vote, Long> {
 
    public Vote getVoteByUserIdArticleId(long userId, long articleId);
}