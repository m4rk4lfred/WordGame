package wordgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartGame {
    private JFrame startGameFrame;
    private JPanel contentPane;
    // I USE SINGLETON PATTERN TO PLAY THE BACKGROUND MUSIC SO THAT
    // THE MUSIC WILL NOT BE INTERRUPTED WHEN THE FRAME IS CLOSED
    backroundMusicPlayer musicPlayer = backroundMusicPlayer.getInstanceOfMusicPlayer();
    JLabel musicBackroundButton = musicPlayer.playBackgroundMusic(5, 580, 39, 36);

    public StartGame() {
    }
    
    //THIS IS THE START GAME INTERFACE AND THIS WILL RETURN A JFRAME SO THAT I CAN EASILY CALL IT IN THE MAINFRAME OR MAIN CLASS
    public JFrame startGameInterface() {
        startGameFrame = new JFrame();
        startGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startGameFrame.setBounds(0, 0, 500, 670);
        startGameFrame.setResizable(false);
        startGameFrame.setTitle("Find the Word");
        startGameFrame.setFocusable(true);
        startGameFrame.setLocationRelativeTo(null);

        contentPane = new FramesBackground();
        contentPane.setLayout(null);
        startGameFrame.setContentPane(contentPane);
       
        contentPane.add(musicBackroundButton);
        
        JLabel startLabel = new JLabel("");
        startLabel.setBounds(50, 66, 385, 179);
        contentPane.add(startLabel);
        
        
        startGameFrame.setVisible(true);
        return startGameFrame;
    }



    public static void main(String[] args) {
        StartGame startGame = new StartGame();
        //THIS IS THE MAIN METHOD THAT WILL RUN THE GAME

        startGame.startGameInterface().addKeyListener(new KeyAdapter() {   //THIS WILL ADD A KEYLISTENER TO THE FRAME IF THE USER ENTER ANY KEY IT WILL EXECUTE THE MAINGAMEFRAME WHERE THE GAME IS LOCATED
            @Override
            public void keyPressed(KeyEvent e) {

                startGame.startGameFrame.dispose();
                //SO HERE I NEED TO PASS THE INSTANCE OF THE MUSIC BACKROUND SO THAT I CAN STOP THE MUSIC WHEN THE GAME IS OVER
                //I NEED ONLY ONE MUSIC BACKROUND TO BE PASS ALTHROUGHT THE GAME BECAUSE IF I DONT USE SINGLETON THE MUSING WILL BE EXECUTED TWICE
                MainGameFrame mainGameFrame = new MainGameFrame(startGame.musicBackroundButton);
                mainGameFrame.mainGameFrameInterface();
            }
        });
    }
}
