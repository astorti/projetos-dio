package board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import board.dto.BoardColumnInfoDTO;
import board.dto.CardDetailsDTO;
import board.exception.CardBlockedException;
import board.exception.CardFinishedException;
import board.exception.EntityNotFoundException;
import board.persistence.dao.BlockDAO;
import board.persistence.dao.CardDAO;
import board.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import static board.persistence.entity.BoardColumnKindEnum.CANCEL;
import static board.persistence.entity.BoardColumnKindEnum.FINAL;


@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity create(final CardEntity entity) throws SQLException {
        try {
            CardDAO dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try{
            CardDAO dao = new CardDAO(connection);
            Optional<CardDetailsDTO> optional = dao.findById(cardId);
            CardDetailsDTO dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );
            if (dto.blocked()){
                String message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
                throw new CardBlockedException(message);
            }
            BoardColumnInfoDTO currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            BoardColumnInfoDTO nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    public void cancel(final Long cardId, final Long cancelColumnId ,
                       final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try{
            CardDAO dao = new CardDAO(connection);
            Optional<CardDetailsDTO> optional = dao.findById(cardId);
            CardDetailsDTO dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );
            if (dto.blocked()){
                String message = "O card %s está bloqueado, é necesário desbloquea-lo para cancelar".formatted(cardId);
                throw new CardBlockedException(message);
            }
            BoardColumnInfoDTO currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try{
            CardDAO dao = new CardDAO(connection);
            Optional<CardDetailsDTO> optional = dao.findById(id);
            CardDetailsDTO dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (dto.blocked()){
                String message = "O card %s já está bloqueado".formatted(id);
                throw new CardBlockedException(message);
            }
            BoardColumnInfoDTO currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow();
            if (currentColumn.kind().equals(FINAL) || currentColumn.kind().equals(CANCEL)){
                String message = "O card está em uma coluna do tipo %s e não pode ser bloqueado"
                        .formatted(currentColumn.kind());
                throw new IllegalStateException(message);
            }
            BlockDAO blockDAO = new BlockDAO(connection);
            blockDAO.block(reason, id);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void unblock(final Long id, final String reason) throws SQLException {
        try{
            CardDAO dao = new CardDAO(connection);
            Optional<CardDetailsDTO> optional = dao.findById(id);
            CardDetailsDTO dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (!dto.blocked()){
                String message = "O card %s não está bloqueado".formatted(id);
                throw new CardBlockedException(message);
            }
            BlockDAO blockDAO = new BlockDAO(connection);
            blockDAO.unblock(reason, id);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

}
