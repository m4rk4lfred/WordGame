package wordgame;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class backroundMusicPlayer {
   private static backroundMusicPlayer musicPlayer;
   private Clip clip;
   private boolean isPlaying;
   
   public static backroundMusicPlayer getInstanceOfMusicPlayer() {
	   if(musicPlayer == null) {
		   musicPlayer = new backroundMusicPlayer();
	   }
	   
	   return musicPlayer;
   }
   
   public JLabel playBackgroundMusic(int x , int y , int height , int width) {
	      
   	try {
   		
           clip = AudioSystem.getClip();
           if(!clip.isRunning()) {
           clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Lenovo\\Downloads\\Kirby_s-Adventure-_NES_-Music-Vegetable-Valley-3.wav")));
           clip.start();    
       }
         
       } catch (Exception ev) {
           JOptionPane.showMessageDialog(null, ev.getMessage(), "ERROR IN PLAYING THE MUSIC", JOptionPane.ERROR_MESSAGE);
       }

       isPlaying = true;
       ImageIcon play = new ImageIcon("C:\\Users\\Lenovo\\Documents\\Logos\\playBackroundMusic.png");
       ImageIcon stop = new ImageIcon("C:\\Users\\Lenovo\\Documents\\Logos\\no-sound.png");
       play.setImage(play.getImage().getScaledInstance(39, 36, Image.SCALE_SMOOTH));
       stop.setImage(stop.getImage().getScaledInstance(34, 30, Image.SCALE_SMOOTH));
       JLabel backgroundMusicPlay = new JLabel(play);
       backgroundMusicPlay.setBounds(x, y, height, width);

       backgroundMusicPlay.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               isPlaying = !isPlaying;
               if (isPlaying) {
                   backgroundMusicPlay.setIcon(play);
                   clip.start();
               } else {
                   backgroundMusicPlay.setIcon(stop);
                   clip.stop();
               }
           }
       });

       
       
       return backgroundMusicPlay;
   }
   

   
}
