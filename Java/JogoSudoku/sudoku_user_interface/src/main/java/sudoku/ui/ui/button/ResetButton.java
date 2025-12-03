package sudoku.ui.ui.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ResetButton extends JButton{

    public ResetButton(final ActionListener actionListener){
        this.setText("reiniciar jogo");
        this.addActionListener(actionListener);
    }
}
