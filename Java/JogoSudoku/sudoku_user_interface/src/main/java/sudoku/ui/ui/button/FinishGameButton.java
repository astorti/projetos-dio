package sudoku.ui.ui.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class FinishGameButton extends JButton{

    public FinishGameButton(final ActionListener actionListener){
        this.setText("concluir");
        this.addActionListener(actionListener);
    }
}
