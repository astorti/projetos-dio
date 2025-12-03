package sudoku.ui.ui.panel;

import java.awt.Dimension;
import java.util.List;

import static java.awt.Color.black;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import sudoku.ui.input.NumberText;

public class SudokuSector extends JPanel{

    public SudokuSector(final List<NumberText> textFields){
        Dimension dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
        textFields.forEach(this::add);
    }

}

