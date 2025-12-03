package board.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import board.persistence.entity.BoardColumnEntity;
import board.persistence.entity.BoardColumnKindEnum;
import board.persistence.entity.BoardEntity;
import board.service.BoardQueryService;
import board.service.BoardService;

import static board.persistence.config.ConnectionConfig.getConnection;
import static board.persistence.entity.BoardColumnKindEnum.CANCEL;
import static board.persistence.entity.BoardColumnKindEnum.FINAL;
import static board.persistence.entity.BoardColumnKindEnum.INITIAL;
import static board.persistence.entity.BoardColumnKindEnum.PENDING;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada");
        int option = -1;
        while (true){
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            switch (option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {
        BoardEntity entity = new BoardEntity();
        System.out.println("Informe o nome do seu board");
        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim informe quantas, senão digite '0'");
        int additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        String initialColumnName = scanner.next();
        BoardColumnEntity initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            String pendingColumnName = scanner.next();
            BoardColumnEntity pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final");
        String finalColumnName = scanner.next();
        BoardColumnEntity finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        String cancelColumnName = scanner.next();
        BoardColumnEntity cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(Connection connection = getConnection()){
            BoardService service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do board que deseja selecionar");
        long id = scanner.nextLong();
        try(Connection connection = getConnection()){
            BoardQueryService queryService = new BoardQueryService(connection);
            Optional<BoardEntity> optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o id do board que será excluido");
        long id = scanner.nextLong();
        try(Connection connection = getConnection()){
            BoardService service = new BoardService(connection);
            if (service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int boardColumnOrder){
        BoardColumnEntity boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setBoardColumnOrder(boardColumnOrder);
        return boardColumn;
    }
}
