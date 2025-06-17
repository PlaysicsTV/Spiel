import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UsernamePanel extends JPanel {
    public UsernamePanel(Consumer<String> onUsernameEntered) {
        setLayout(new GridBagLayout());
        JTextField usernameField = new JTextField(15);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String name = usernameField.getText().trim();
            if (!name.isEmpty()) onUsernameEntered.accept(name);
        });
        add(new JLabel("Benutzernamen eingeben:"));
        add(usernameField);
        add(okButton);
    }
}