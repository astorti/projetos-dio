package board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import board.dto.BoardColumnDTO;
import board.persistence.entity.BoardColumnEntity;
import board.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import static board.persistence.entity.BoardColumnKindEnum.findByName;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, board_column_order, kind, board_id) VALUES (?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            int i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getBoardColumnOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException{
        
        List<BoardColumnEntity> entities = new ArrayList<>();
        String sql = "SELECT id, name, board_column_order, kind FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY board_column_order";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                BoardColumnEntity entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setBoardColumnOrder(resultSet.getInt("board_column_order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }    
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException {
        List<BoardColumnDTO> dtos = new ArrayList<>();
        String sql =
                """
                SELECT bc.id,
                       bc.name,
                       bc.kind,
                       (SELECT COUNT(c.id)
                               FROM CARDS c
                              WHERE c.board_column_id = bc.id) cards_amount
                  FROM BOARDS_COLUMNS bc
                 WHERE board_id = ?
                 ORDER BY board_column_order;
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()){
                BoardColumnDTO dto = new BoardColumnDTO(
                        resultSet.getLong("bc.id"),
                        resultSet.getString("bc.name"),
                        findByName(resultSet.getString("bc.kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(dto);
            }
            return dtos;
        }
    }

    public Optional<BoardColumnEntity> findById(final Long boardId) throws SQLException{
        String sql =
        """
        SELECT bc.name,
               bc.kind,
               c.id,
               c.title,
               c.description
          FROM BOARDS_COLUMNS bc
          LEFT JOIN CARDS c
            ON c.board_column_id = bc.id
         WHERE bc.id = ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()){
                BoardColumnEntity entity = new BoardColumnEntity();
                entity.setName(resultSet.getString("bc.name"));
                entity.setKind(findByName(resultSet.getString("bc.kind")));
                do {
                    CardEntity card = new CardEntity();
                    if (isNull(resultSet.getString("c.title"))){
                        break;
                    }
                    card.setId(resultSet.getLong("c.id"));
                    card.setTitle(resultSet.getString("c.title"));
                    card.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(card);
                }while (resultSet.next());
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }
}
