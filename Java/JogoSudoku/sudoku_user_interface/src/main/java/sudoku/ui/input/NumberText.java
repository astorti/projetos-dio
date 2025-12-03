package sudoku.ui.input;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sudoku.ui.model.Space;
import sudoku.ui.service.EventEnum;
import sudoku.ui.service.EventListener;
import static sudoku.ui.service.EventEnum.CLEAR_SPACE;

public class NumberText extends JTextField implements EventListener{

    private final Space space;

    public NumberText(final Space space){
        this.space = space;

        Dimension dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.getFixed());
        if(space.getFixed()){
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {
            
            public void changeSpace(){
                if(getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }

            @Override
            public void insertUpdate(final DocumentEvent e){
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e){
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e){
                changeSpace();
            }
        });
    }

    @Override
    public void update(final EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}
