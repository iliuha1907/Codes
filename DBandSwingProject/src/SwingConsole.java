import javax.swing.*;
import java.awt.*;

public class SwingConsole
{
    public static void run(JFrame frame, final int width, final int height)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                frame.setTitle(frame.getClass().getSimpleName());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setSize(width,height);
                frame.setVisible(true);
                //frame.setCursor(1);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }
}
