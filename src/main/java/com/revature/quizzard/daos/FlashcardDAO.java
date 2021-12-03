package com.revature.quizzard.daos;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.util.datasource.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class FlashcardDAO implements CrudDAO<Flashcard> {

    public static final Logger logger = LogManager.getLogger();

    private final ConnectionFactory connectionFactory;

    public FlashcardDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<Flashcard> findCardsByCreatorId(String creatorId) {

        logger.info("FlashcardDAO#findCardsByCreatorId invoked with argument {}", creatorId);

        Connection conn = connectionFactory.getConnection();
        List<Flashcard> cards = new LinkedList<>();

        try {

            String sql = "select * " +
                         "from flashcards f " +
                         "join app_users u " +
                         "on f.creator_id = u.user_id " +
                         "where u.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, creatorId);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        logger.info("FlashcardDAO#findCardsByCreatorId returning with value {}", cards);

        return cards;

    }

    @Override
    public Flashcard save(Flashcard newCard) {

        Connection conn = connectionFactory.getConnection();
        try {

            newCard.setId(UUID.randomUUID().toString());

            String sql = "insert into flashcards (card_id, question_text, answer_text, creator_id) values (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCard.getId());
            pstmt.setString(2, newCard.getQuestionText());
            pstmt.setString(3, newCard.getAnswerText());
            pstmt.setString(4, newCard.getCreator().getId());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                return newCard;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return null;

    }

    @Override
    public List<Flashcard> findAll() {

        Connection conn = connectionFactory.getConnection();

        List<Flashcard> cards = new LinkedList<>();

        try {
            String sql = "select * from flashcards f join app_users u on f.creator_id = u.user_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return cards;
    }

    @Override
    public Flashcard findById(String cardId) {

        Connection conn = connectionFactory.getConnection();
        Flashcard card = null;

        try {
            String sql = "select * from flashcards f join app_users u on f.creator_id = u.id where f.card_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cardId);
            ResultSet rs = pstmt.executeQuery();
            card = mapResultSet(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.releaseConnection(conn);
        }

        return card;
    }

    @Override
    public boolean update(Flashcard updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    private List<Flashcard> mapResultSet(ResultSet rs) throws SQLException {

        List<Flashcard> cards = new LinkedList<>();

        while (rs.next()) {
            Flashcard card = new Flashcard();
            AppUser cardCreator = new AppUser();
            card.setId(rs.getString("card_id"));
            card.setQuestionText(rs.getString("question_text"));
            card.setAnswerText(rs.getString("answer_text"));
            cardCreator.setId(rs.getString("user_id"));
            cardCreator.setFirstName(rs.getString("first_name"));
            cardCreator.setLastName(rs.getString("last_name"));
            cardCreator.setEmail(rs.getString("email"));
            cardCreator.setUsername(rs.getString("username"));
            card.setCreator(cardCreator);
            cards.add(card);
        }

        return cards;

    }

}
