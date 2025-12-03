package sudoku.ui.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces){
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces(){
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if(spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.getFixed() && Objects.nonNull(s.getActual()))){
            return GameStatusEnum.NOT_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> Objects.isNull(s.getActual())) ? GameStatusEnum.INCOMPLETE : GameStatusEnum.COMPLETE;
    }

    public boolean hasErrors(){
        if(getStatus() == GameStatusEnum.NOT_STARTED){
            return false;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> Objects.nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final Integer value){
        Space space = spaces.get(col).get(row);
        if (space.getFixed()){
            return false;
        }
        
        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        Space space = spaces.get(col).get(row);
        if (space.getFixed()){
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset(){    
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(GameStatusEnum.COMPLETE);
    }
}
