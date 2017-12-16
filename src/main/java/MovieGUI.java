import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieGUI extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel profileTab;
    private JPanel favoritesTab;
    private JPanel watchedTab;
    private JPanel toWatchTab;
    private JPanel searchTab;
    private JTextField movieSearchQuery;
    private JButton movieSearchButton;
    private JPanel searchPanel;
    private JScrollPane searchScrollPane;
    private JScrollPane toWatchScrollPane;
    private JPanel toWatchPanel;
    private JLabel userNameLabel;
    private JLabel picLabel;
    private JButton searchItem;
    private JButton toWatchItem;
    Image img;
    ImageIcon icon;
    String defaultImageURL = "https://d30y9cdsu7xlg0.cloudfront.net/png/45447-200.png";
    ArrayList<JButton> searchButtonList;
    ActionListener actionListener;
    ArrayList<Movie> moviesSearchList;
    ArrayList<JButton> toWatchButtonList;
    ArrayList<Movie> toWatchList;
    CloudSQL cloudSQL;
    ActionListener actionListener2;

    MovieGUI(String userName) {
        searchScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setLayout(new GridLayout(0, 4));
        toWatchPanel.setLayout(new GridLayout(0, 4));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setVisible(true);
        setResizable(false);
        setSize(new Dimension(850, 650));
        setTitle("Movie Manager");
        userNameLabel.setText(userName);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 3) {
                    toWatchButtonList = new ArrayList<>();
                    try {
                        cloudSQL = new CloudSQL();
                    } catch (Exception exe) {
                        System.out.println("Cant connect to db");
                    }

                    toWatchList = cloudSQL.selectAllToWatch(userName);

                    for (int i = 0; i < toWatchList.size(); i++) {
                        toWatchList.get(i).setIcon(loadItem(toWatchList.get(i).getPictureURL(), toWatchList.get(i).getTitle(), toWatchList.get(i).getDate(), toWatchList.get(i).isAdult(), toWatchList.get(i).getId()));
                    }
                    toWatchPanel.updateUI();
                }
            }
        });
        movieSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPanel.removeAll();
                searchButtonList = new ArrayList<>();
                MovieAPI movieAPI = new MovieAPI(formatQuery(movieSearchQuery.getText()));
                moviesSearchList = null;
                try {
                    moviesSearchList = movieAPI.getMovieList();
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                for (int i = 0; i < moviesSearchList.size(); i++) {

                    moviesSearchList.get(i).setIcon(openItem(moviesSearchList.get(i).getPictureURL(), moviesSearchList.get(i).getTitle(), moviesSearchList.get(i).getDate(), moviesSearchList.get(i).isAdult(), moviesSearchList.get(i).getId()));
                }

                searchPanel.updateUI();

            }
        });


        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int count = 0;
                int count2 = 0;
                for (int i = 0; searchButtonList.size() > i; i++)
                    if (searchButtonList.get(i) == actionEvent.getSource()) {
                        System.out.println(searchButtonList.get(i).getName());
                        count2 = i;
                        while (moviesSearchList.get(count).getId() != Integer.parseInt(searchButtonList.get(i).getName())) {
                            count++;
                        }
                    }

                if (moviesSearchList.get(count).getId() == Integer.parseInt(searchButtonList.get(count2).getName())) {
                    System.out.println("You selected " + moviesSearchList.get(count).getTitle());
                    new IndividualMovieGUI(userName, moviesSearchList.get(count).getPictureURL(), moviesSearchList.get(count).getId(), moviesSearchList.get(count).getTitle(), moviesSearchList.get(count).isAdult(),
                            moviesSearchList.get(count).getDescription(), moviesSearchList.get(count).getDate(), moviesSearchList.get(count).getIcon(), moviesSearchList.get(count).getRating(),
                            moviesSearchList.get(count).getActors(), moviesSearchList.get(count).getYtLink(), moviesSearchList.get(count).getTagLine(), 0.0);


                }
            }
        };

        actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = 0;
                int count2 = 0;
                for (int i = 0; toWatchButtonList.size() > i; i++)
                    if (toWatchButtonList.get(i) == e.getSource()) {
                        System.out.println(toWatchButtonList.get(i).getName());
                        count2 = i;
                        while (toWatchList.get(count).getId() != Integer.parseInt(toWatchButtonList.get(i).getName())) {
                            count++;
                        }
                    }

                if (toWatchList.get(count).getId() == Integer.parseInt(toWatchButtonList.get(count2).getName())) {
                    System.out.println("You selected " + toWatchList.get(count).getTitle());
                    new IndividualMovieGUI(userName, toWatchList.get(count).getPictureURL(), toWatchList.get(count).getId(), toWatchList.get(count).getTitle(), toWatchList.get(count).isAdult(),
                            toWatchList.get(count).getDescription(), toWatchList.get(count).getDate(), toWatchList.get(count).getIcon(), toWatchList.get(count).getRating(),
                            moviesSearchList.get(count).getActors(), toWatchList.get(count).getYtLink(), toWatchList.get(count).getTagLine(), toWatchList.get(count).getUserRating());

                }
            }
        };
    }

        void openVideo (String ytUrl){
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + ytUrl);
            } catch (IOException ioe) {
                System.out.println("Video could not open.");
            }
        }

        ImageIcon openItem (String imageURL, String title, String year,boolean adult, int id){
            if (imageURL.equalsIgnoreCase("none")) {
                imageURL = defaultImageURL;
            }
            ImageIO.setUseCache(false);
            try {
                URL url = new URL(imageURL);
                img = ImageIO.read(url);
                Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);
                //img = ImageIO.read(url);
            } catch (MalformedURLException mal) {
                System.out.println("malformed URL");

            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());

            }

            String rating;
            if (adult) {
                rating = "Adult";
            } else {
                rating = "PG-13";
            }
            System.out.println(icon);

            searchItem = new JButton();
            searchItem.setName(Integer.toString(id));
            searchItem.setIcon(icon);
            searchItem.setBorderPainted(false);
            searchItem.setContentAreaFilled(false);
            searchItem.setMargin(new Insets(0, 0, 0, 0));
            searchItem.setSize(1000, 1000);
            searchItem.setBorder(new EmptyBorder(10, 0, 10, 0));
            String text = "<html> &ensp; %s<br> &ensp; %s<br> &ensp; %s</html>";
            String acText = String.format(text, title, "Release: " + year, "Rating: " + rating);
            JLabel desc = new JLabel(acText);

            searchItem.addActionListener(actionListener);
            searchButtonList.add(searchItem);
            searchPanel.add(searchItem);
            searchPanel.add(desc);

            return icon;


        }

        ImageIcon loadItem (String imageURL, String title, String year,boolean adult, int id){
            if (imageURL.equalsIgnoreCase("none")) {
                imageURL = defaultImageURL;
            }
            ImageIO.setUseCache(false);
            try {
                URL url = new URL(imageURL);
                img = ImageIO.read(url);
                Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newimg);
                //img = ImageIO.read(url);
            } catch (MalformedURLException mal) {
                System.out.println("malformed URL");

            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());

            }

            String rating;
            if (adult) {
                rating = "Adult";
            } else {
                rating = "PG-13";
            }
            System.out.println(icon);

            toWatchItem = new JButton();
            toWatchItem.setName(Integer.toString(id));
            toWatchItem.setIcon(icon);
            toWatchItem.setBorderPainted(false);
            toWatchItem.setContentAreaFilled(false);
            toWatchItem.setMargin(new Insets(0, 0, 0, 0));
            toWatchItem.setSize(1000, 1000);
            toWatchItem.setBorder(new EmptyBorder(10, 0, 10, 0));
            String text = "<html> &ensp; %s<br> &ensp; %s<br> &ensp; %s</html>";
            String acText = String.format(text, title, "Release: " + year, "Rating: " + rating);
            JLabel desc = new JLabel(acText);

            toWatchItem.addActionListener(actionListener2);
            toWatchButtonList.add(toWatchItem);
            toWatchPanel.add(toWatchItem);
            toWatchPanel.add(desc);

            return icon;


        }

        String formatQuery (String query){
            query = query.replaceAll("\\s", "%20");
            return query;
        }





}
