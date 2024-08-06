package wordgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainGameFrame extends JFrame {
  private JPanel contentPane = new JPanel();
  private JTextField guessWordTextField;
  //THIS IS THE BUTTONN FOR MUTE THE MUSIC
  private JLabel musicPlay;
  private int characterInsertionCounter;
  int numberOfContent = 10;

  //SO THIS IS THE CHARACTERS THAT WILL BE INSERTED IN THE GRID
  char[][] guessWordCharacters = {
    {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D'},              // BLIZZARD
    {'C', 'R', 'Y', 'P', 'T', 'I', 'C'},                   // CRYPTIC
    {'M', 'Y', 'S', 'T', 'I', 'C', 'A', 'L'},              // MYSTICAL
    {'E', 'L', 'U', 'S', 'I', 'V', 'E'},                   // ELUSIVE
    {'P', 'U', 'Z', 'Z', 'L', 'I', 'N', 'G'},              // PUZZLING
    {'D', 'A', 'Z', 'Z', 'L', 'I', 'N', 'G'},              // DAZZLING
    {'C', 'H', 'A', 'O', 'T', 'I', 'C'},                   // CHAOTIC
    {'M', 'A', 'G', 'I', 'C', 'A', 'L'},                   // MAGICAL
    {'R', 'E', 'S', 'I', 'L', 'I', 'E', 'N', 'T'},         // RESILIENT
    {'M', 'Y', 'S', 'T', 'E', 'R', 'Y'},                   // MYSTERY
};

      //THIS WILL BE USE IN CHECKING THE GUESS OF THE USER
   String[] guessWord = {"BLIZZARD", "CRYPTIC", "MYSTICAL", "ELUSIVE", "PUZZLING", "DAZZLING", "CHAOTIC", "MAGICAL", "RESILIENT", "MYSTERY"};
   JLabel[] guessWordLabel;
    
	
    public MainGameFrame(JLabel musicPlay) {
        this.musicPlay = musicPlay;
	}


    //THIS IS THE MAIN GAME FRAME INTERFACE WHERE ALL THE METHODS IS BEING ADDED
	public JFrame mainGameFrameInterface(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 670);
		contentPane = new FramesBackground();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setFocusable(true);
		contentPane.setLayout(null);
		
		
		guessWordTextField = new JTextField();
		guessWordTextField.setHorizontalAlignment(SwingConstants.CENTER);
		guessWordTextField.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
		guessWordTextField.setBounds(10, 445, 466, 58);
		
		
		contentPane.add(guessWordTextField);
		guessWordTextField.setColumns(10);
		
		//CALLING THE GRID WHERE THE RANDOM LETTERS BELONG
		wordBox();
		
        //THIS WILL CHECK THE WORD IF THE USER ENTERED THE WORD
		guessWordTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					checkWord();
				}
			}
		});
        //THIS WILL SHOW THE WORD THAT THE USER NEED TO GUESS
        guessWordToBeShow();
		setResizable(false);
		setTitle("Find the Word");
		setFocusable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane.add(musicPlay);
		

		return this;
	}
	
	

	public void wordBox() {
		JPanel gridWordBox = new JPanel();
		gridWordBox.setBounds(10, 21, 466, 400);
		gridWordBox.setBackground(Color.CYAN);
		gridWordBox.setLayout(new GridLayout(14,14));
      
        //THIS WILL RANDOMLY INSERT THE CHARACTERS IN THE GRID
		//THIS IS A 14X14 GRID 
		for(int i = 0 ; i < 14 ; i++) {
			//THIS RANDOMLY SELECT THE POSITION OF THE CHARACTER TO BE INSERTED IN THE GRID
			//THIS RANDOM WILL GENERATE THE POSITION OF THE CHARACTERS THAT NEED TO BE GUESSED 
			//I SET THE BOUNDS OF THE RANDOMIZER TO 4 BECAUSE THE WORDS THAT NEED TO BE GUESS IS 10 ONLY SO THE RANDOMIZER WILL ONLY SELECT 4 CHARACTERS
			//THE HIGHEST POSSIBLE LETTER OF THE WORD THAT NEED TO BE GUESS IS 9
			//SO FOR EXAMPLE THE RANDOMIZER SELECT 5 THEN THE CHARACTER THAT NEED TO BE INSERT IN THE GRID IS 10 THEN IT WIL NOT FIT ANYMORE
			//AND IT WILL RESULT TO ERROR BECAUSE THE COLOUMN IS ONLY 14, SO I NEED TO SET THE BOUNDS OF THE RANDOMIZER TO 4
			Random generateRandomPosition = new Random();
			int letterPosition = generateRandomPosition.nextInt(4);
            
			//THIS RANDOMLY SELECT THE CHARACTER THAT WILL BE INSERTED IN THE GRID
			//THIS RANDOM WILL RANDOMLY SELECT FROM THE ARRAY OF CHARACTERS THAT NEED TO BE GUESSED
			Random generateCharHorizontally = new Random();
	     	int characterInsertionToGrid = generateCharHorizontally.nextInt(9);
			
			characterInsertionCounter = 0;

			//SO THIS FOR LOOP IS RESPONSIBLE FOR THE INSERTION OF THE CHARACTERS IN THE GRID
			//THE GRID IS 14X14 SO I NEED TO LOOP 14 TIMES
			for(int j = 0 ; j < 14  ; j++) {
		       //THIS WILL PUT THE CHARACTER TO GUESS IN THE GRID
			   if(letterPosition < j && characterInsertionCounter < guessWordCharacters[characterInsertionToGrid].length) {
              /*  JLabel wordBox = new JLabel(Character.toString(guessWordCharacters[characterInsertionToGrid][characterInsertionCounter]));
			   wordBox.setHorizontalAlignment(SwingConstants.CENTER);
			   wordBox.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
			   gridWordBox.add(wordBox);
			   */
			   setWordArrangement(letterPosition, j, characterInsertionToGrid, gridWordBox);
			   characterInsertionCounter++;

			   }

			   //OR ELSE IT WILL PUT RANDOM CHARACTERS IN THE GRID
			   else{
			   // THIS WILL RANDOMLY SELECT THE CHARACTERS THAT WILL BE INSERTED IN THE GRID
			   Random random = new Random();
			   //THE VALUE OF UPPER CASE A IS 65 RIGHT?
			   //SO I NEED TO ADD 26 SO THAT THE RANDOMIZER WILL SELECT THE CHARACTERS FROM A-Z
			   //FOR EXAMPLE THE RANDOMIZER SELECT 1 THEN THE VALUE OF 1 + 65 = 66 WHICH IS B
			   char randomChar = (char)(random.nextInt(26) + 'A');
			   JLabel wordBox = new JLabel(Character.toString(randomChar));
			   wordBox.setHorizontalAlignment(SwingConstants.CENTER);
			   wordBox.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
			   gridWordBox.add(wordBox);
			   
			   }
		
		}
		contentPane.add(gridWordBox);
	}
}
     
	//THIS WILL CHECK THE WORD IF THE USER ENTERED THE WORD
    public void checkWord(){  
		setSoundEffects();
		 //THIS WILL GET THE WORD THAT THE USER ENTERED
	  	 String userGuessWord = guessWordTextField.getText().toUpperCase();
		 boolean isWrong = false;
		 boolean isCorrect = false;
			for (int i = 0; i < guessWord.length; i++) {
					if (userGuessWord.equals(guessWord[i])) {
						System.out.println("Correct");
						guessWordLabel[i].setVisible(true);
						guessWordTextField.setText("");
						guessWord[i] = "";
						numberOfContent--;
						System.out.println("Number of content: " + numberOfContent);
						isCorrect = true;
							break;
						}  

						if(i == guessWord.length - 1){
							isWrong = true;
						}

						if (userGuessWord.equals("")) {
							JOptionPane.showMessageDialog(null, "Please enter a word", "EMPTY", JOptionPane.ERROR_MESSAGE);
							break;
						}
					
					
					}
                     
					while(isWrong){

	                    JOptionPane.showMessageDialog(null, "Wrong Answer", "WRONG", JOptionPane.ERROR_MESSAGE);
						break;
					}
					while(isCorrect){
						JOptionPane.showMessageDialog(null, "Correct Answer", "CORRECT", JOptionPane.INFORMATION_MESSAGE);
						break;
					}

					if (numberOfContent == 0) {
						JOptionPane.showMessageDialog(null, "Congratulations! You have guessed all the words", "CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					} 
					for(int j = 0 ; j < numberOfContent ; j++){
						System.out.println(guessWord[j]);
		  
					  }
			
			
		}



	public void guessWordToBeShow(){
		guessWordLabel = new JLabel[guessWordCharacters.length];
		int x = 77;
		int y = 475;

		  for(int i = 0 ; i < guessWord.length ; i++) {
              if( i % 5 != 5 && i < 5){
               	guessWordLabel[i] = new JLabel(guessWord[i]); 
				guessWordLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
				guessWordLabel[i].setForeground(Color.GREEN);
				guessWordLabel[i].setFont(new Font("Haettenschweiler", Font.PLAIN, 15));
				guessWordLabel[i].setBounds(x, y, 100, 100);
				guessWordLabel[i].setVisible(false);
				contentPane.add(guessWordLabel[i]);
				y+= 20;

				
			  }
			  else if (i >= 5){
				if (i == 5) {
					y = 475;
				}
				guessWordLabel[i] = new JLabel(guessWord[i]); 
				guessWordLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
				guessWordLabel[i].setForeground(Color.GREEN);
				guessWordLabel[i].setFont(new Font("Haettenschweiler", Font.PLAIN, 15));
				guessWordLabel[i].setBounds(x + 170 , y , 100, 100);
				guessWordLabel[i].setVisible(false);
				contentPane.add(guessWordLabel[i]);
				y+= 20;
			  }
		  }

		  contentPane.revalidate();
		  contentPane.repaint();
	}


	public void setWordArrangement(int letterPosition, int j, int characterInsertionToGrid , JPanel gridWordBox){
			if(letterPosition < j && characterInsertionCounter < guessWordCharacters[characterInsertionToGrid].length) {
               JLabel wordBox = new JLabel(Character.toString(guessWordCharacters[characterInsertionToGrid][characterInsertionCounter]));
			   wordBox.setHorizontalAlignment(SwingConstants.CENTER);
			   wordBox.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
			   gridWordBox.add(wordBox);
	}

	public void setSoundEffects(){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\Lenovo\\Downloads\\Gameboy-Startup-Sound.wav")));
			
			clip.addLineListener(new LineListener(){
				public void update(LineEvent event){
					if(event.getType() == LineEvent.Type.STOP){
						clip.close();
					}
				}
			});
			clip.start();
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR IN PLAYING THE MUSIC", JOptionPane.ERROR_MESSAGE);
		}
	}

 }

