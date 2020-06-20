package Aplikacja.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Aplikacja.model.Article;
import Aplikacja.model.User;
import Aplikacja.util.ConnectionProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ArticleDAOImpl implements ArticleDAO {
 
    private static final String CREATE_ARTICLE = 
      "INSERT INTO article(name, description, url, user_id, date, up_vote, down_vote) "
      + "VALUES(:name, :description, :url, :user_id, :date, :up_vote, :down_vote);";
    private static final String READ_ALL_ARTICLES = 
      "SELECT user.user_id, username, email, is_active, password, article_id, name, description, url, date, up_vote, down_vote "
      + "FROM article LEFT JOIN user ON article.user_id=user.user_id;";
    private static final String READ_ARTICLE = 
        "SELECT user.user_id, username, email, is_active, password, article_id, name, description, url, date, up_vote, down_vote "
        + "FROM article LEFT JOIN user ON article.user_id=user.user_id WHERE article_id=:article_id;";
    private static final String UPDATE_ARTICLE = 
        "UPDATE article SET name=:name, description=:description, url=:url, user_id=:user_id, date=:date, up_vote=:up_vote, down_vote=:down_vote "
        + "WHERE article_id=:article_id;";
 
    private NamedParameterJdbcTemplate template;
     
    public ArticleDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
 
    @Override
    public Article create(Article article) {
        Article resultArticle = new Article(article);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", article.getName());
        paramMap.put("description", article.getDescription());
        paramMap.put("url", article.getUrl());
        paramMap.put("user_id", article.getUser().getId());
        paramMap.put("date", article.getTimestamp());
        paramMap.put("up_vote", article.getUpVote());
        paramMap.put("down_vote", article.getDownVote());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CREATE_ARTICLE, paramSource, holder);
        if(update > 0) {
            resultArticle.setId((Long)holder.getKey());
        }
        return resultArticle;
    }
 
    @Override
    public Article read(Long primaryKey) {
        SqlParameterSource paramSource = new MapSqlParameterSource("article_id", primaryKey);
        Article article = template.queryForObject(READ_ARTICLE, paramSource, new ArticleRowMapper());
        return article;
    }
 
    @Override
    public boolean update(Article article) {
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("article_id", article.getId());
        paramMap.put("name", article.getName());
        paramMap.put("description", article.getDescription());
        paramMap.put("url", article.getUrl());
        paramMap.put("user_id", article.getUser().getId());
        paramMap.put("date", article.getTimestamp());
        paramMap.put("up_vote", article.getUpVote());
        paramMap.put("down_vote", article.getDownVote());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_ARTICLE, paramSource);
        if(update > 0) {
            result = true;
        }
        return result;
    }
 
    @Override
    public boolean delete(Long key) {
        return false;
    }
 
    @Override
    public List<Article> getAll() {
        List<Article> articles = template.query(READ_ALL_ARTICLES, new ArticleRowMapper());
        return articles;
    }
     
    private class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet resultSet, int row) throws SQLException {
            Article article = new Article();
            article.setId(resultSet.getLong("article_id"));
            article.setName(resultSet.getString("name"));
            article.setDescription(resultSet.getString("description"));
            article.setUrl(resultSet.getString("url"));
            article.setUpVote(resultSet.getInt("up_vote"));
            article.setDownVote(resultSet.getInt("down_vote"));
            article.setTimestamp(resultSet.getTimestamp("date"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            article.setUser(user);
            return article;
        }
    }
}