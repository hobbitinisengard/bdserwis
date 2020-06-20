package Aplikacja.controller;
 
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Aplikacja.model.Article;
import Aplikacja.service.ArticleService;
 
@WebServlet("")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveArticlesInRequest(request);
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }
 
    private void saveArticlesInRequest(HttpServletRequest request) {
        ArticleService articleService = new ArticleService();
        List<Article> allArticles = articleService.getAllArticles(new Comparator<Article>() {
            //more votes = higher
            @Override
            public int compare(Article d1, Article d2) {
                int d1Vote = d1.getUpVote() - d1.getDownVote();
                int d2Vote = d2.getUpVote() - d2.getDownVote();
                if(d1Vote < d2Vote) {
                    return 1;
                } else if(d1Vote > d2Vote) {
                    return -1;
                }
                return 0;
            }
        });
        request.setAttribute("articles", allArticles);
    }
}