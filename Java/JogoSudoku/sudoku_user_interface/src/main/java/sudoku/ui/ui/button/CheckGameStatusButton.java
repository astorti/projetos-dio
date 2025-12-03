package sudoku.ui.ui.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton{

    public CheckGameStatusButton(final ActionListener actionListener){
        this.setText("verificar jogo");
        this.addActionListener(actionListener);
    }
}
