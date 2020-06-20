package Aplikacja.controller;
 
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Aplikacja.model.Article;
import Aplikacja.model.User;
import Aplikacja.model.Vote;
import Aplikacja.model.VoteType;
import Aplikacja.service.ArticleService;
import Aplikacja.service.VoteService;
 
/**
 * example URL /vote?article_id=3&vote=VOTE_UP
 */
@WebServlet("/vote")
public class VoteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");
        if(loggedUser != null) {
            VoteType voteType = VoteType.valueOf(request.getParameter("vote"));
            long userId  = loggedUser.getId();
            long articleId = Long.parseLong(request.getParameter("article_id"));
            updateVote(userId, articleId, voteType);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
 
    private void updateVote(long userId, long articleId, VoteType voteType) {
        VoteService voteService = new VoteService();
        Vote existingVote = voteService.getVoteByArticleUserId(articleId, userId);
        Vote updatedVote = voteService.addOrUpdateVote(articleId, userId, voteType);
        if(existingVote != updatedVote || !updatedVote.equals(existingVote)) {
            updateArticle(articleId, existingVote, updatedVote);
        }
    }
 
    private void updateArticle(long articleId, Vote oldVote, Vote newVote) {
        ArticleService articleService = new ArticleService();
        Article articleById = articleService.getArticleById(articleId);
        Article updatedArticle = null;
        if(oldVote == null && newVote != null) {
            updatedArticle = addArticleVote(articleById, newVote.getVoteType());
        } else if(oldVote != null && newVote != null) {
            updatedArticle = removeArticleVote(articleById, oldVote.getVoteType());
            updatedArticle = addArticleVote(updatedArticle, newVote.getVoteType());
        }
        articleService.updateArticle(updatedArticle);
    }
     
    private Article addArticleVote(Article article, VoteType voteType) {
        Article articleCopy = new Article(article);
        if(voteType == VoteType.VOTE_UP) {
            articleCopy.setUpVote(articleCopy.getUpVote() + 1);
        } else if(voteType == VoteType.VOTE_DOWN) {
            articleCopy.setDownVote(articleCopy.getDownVote() + 1);
        }
        return articleCopy;
    }
     
    private Article removeArticleVote(Article article, VoteType voteType) {
        Article articleCopy = new Article(article);
        if(voteType == VoteType.VOTE_UP) {
            articleCopy.setUpVote(articleCopy.getUpVote() - 1);
        } else if(voteType == VoteType.VOTE_DOWN) {
            articleCopy.setDownVote(articleCopy.getDownVote() - 1);
        }
        return articleCopy;
    }
}