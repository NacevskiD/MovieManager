import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class IndividualMovieGUI extends JFrame{
    private JPanel rootPanel;
    private JPanel picAndButtonsPanel;
    private JPanel picPanel;
    private JPanel buttonsPanel;
    private JButton trailerButton;
    private JButton favoritesButton;
    private JButton toWatchButton;
    private JPanel labelsPanel;
    private JLabel titleLabel;
    private JLabel tagLineLabel;
    private JLabel yearLabel;
    private JLabel ratingLabel;
    private JLabel imdbLabel;
    private JLabel actorsLabel;
    private JButton closeButton;
    private JLabel averageRatingLabel;
    private JTextField yourRatingEditText;

    IndividualMovieGUI(String userName,String imageURL,int id, String title, boolean adult, String desc, String date, ImageIcon imageIcon, String imdb, ArrayList<String> actors,String ytLink,String tagline,double userRating){



        trailerButton.setIcon(imageIcon);
        trailerButton.setBorderPainted(false);
        trailerButton.setContentAreaFilled(false);
        trailerButton.setMargin(new Insets(0, 0, 0, 0));
        trailerButton.setSize(1000,1000);
        trailerButton.setBorder(new EmptyBorder(10,0,10,0));
        titleLabel.setText(title);
        tagLineLabel.setText(tagline);
        yearLabel.setText(date);
        ratingLabel.setText(getRating(adult));
        imdbLabel.setText(imdb);
        yourRatingEditText.setText(Double.toString(userRating));
        String allActors = "";
        for (String actor: actors){
            allActors+=actor + ", ";
        }
        actorsLabel.setText(allActors);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setVisible(true);
        //setResizable(false);
        setSize(new Dimension(850,650));
        setTitle("Movie Manager");
        isAlwaysOnTop();

        trailerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVideo(ytLink);
            }
        });
        toWatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloudSQL cloudSQL = null;
                try {
                    cloudSQL = new CloudSQL();
                }catch (Exception exe) {
                    System.out.println("can't connect");
                }

                Double rating = Double.parseDouble(yourRatingEditText.getText());
                try {

                    cloudSQL.createToWatchTable(userName);
                }catch (Exception exe){
                    System.out.println(exe.getCause());
                    System.out.println(exe.getMessage());
                }
                cloudSQL.addMovieToDB(userName,id,title,date,adult,imageURL,Double.parseDouble(yourRatingEditText.getText()));
                JOptionPane.showMessageDialog(null,"Added to watch list");
                dispose();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    String getRating(boolean rating){
        if (rating){
            return "Adult";
        }
        else {
            return "PG-13";
        }
    }

    void openVideo(String ytUrl){
        Runtime rt = Runtime.getRuntime();
        try{
            rt.exec("rundll32 url.dll,FileProtocolHandler " + ytUrl);
        }catch (IOException ioe){
            System.out.println("Video could not open.");
        }
    }
}
