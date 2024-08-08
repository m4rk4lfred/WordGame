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
  int counterPerLetter = 0;

  //SO THIS IS THE CHARACTERS THAT WILL BE INSERTED IN THE GRID
  char[][] guessWordCharacters = {
    {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D'},              // BLIZZARD
    {'C', 'R', 'Y', 'P', 'T', 'I', 'C'},                   // CRYPTIC
    {'M', 'Y', 'S', 'T', 'I', 'C', 'A', 'L'},              // MYSTICAL
    {'E', 'L', 'U', 'S', 'I', 'V', 'E'},                   // ELUSIVE
    {'P', 'U', 'Z', 'Z', 'L', 'I', 'N', 'G'},              // PUZZLING
    {'D', 'A', 'Z', 'Z', 'L', 'I', 'N', 'G'},              // DAZZLING
    {'C' , 'H' , 'A' , 'O' , 'T' , 'I' , 'C'},              // CHAOTIC
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
        JPanel gridWordBoxContainer = new JPanel();
        gridWordBoxContainer.setBounds(10, 21, 466, 400);
        gridWordBoxContainer.setBackground(Color.CYAN);
        gridWordBoxContainer.setLayout(new GridLayout(14, 14));
        //I WILL PUT ALL OF THE CHARACTERS IN THIS 2D ARRAY INCLUDING THE WORDS THAT I WANT TO PUT IN THE GRID
        char[][] gridBox = new char[14][14];
		Random random = new Random();
		
		boolean isInserted ;
        // THIS FOR EACH LOOP WILL ITERATE THE WORDS IN THE 2D ARRAY THAT I MADE
		//FOR EXAMPLE THE FIRST WORD IN THE ARRAY IS BLIZZARD IT WILL GOES LIKE THIS {'B','L','I','Z','Z','A','R','D'}
	    // IT WILL STORE THE CHARACTERS IN THE GRID BOX IT WILL START IN THE FIRST ROW AND THE WHOLE COLOUMN OF THE FIRST ROW
		for(char[] word : guessWordCharacters){
			//I PUT THE BOOLEAN TO FALSE SO THAT IT WILL ITERATE ALL THE WORDS THAT NEED TO BE ITERATE
			isInserted = false;

            while(!isInserted){
				//THIS WILL GENERATE RANDOM ROW AND COLUMN AND DIRECTION
				// I PUT IT INSIDE THE WHILE SO THAT IT WILL GENERATE RANDOM ROW AND COL WHENEVER THE LOOP RUNS
				int row = random.nextInt(14);
				int col = random.nextInt(14);
				int direction = random.nextInt(2);

				// I HAVE DIRECTION RIGHT? SO THAT DIRECTION WILL DECIDE IF THE WORD WILL BE INSERTED HORIZONTALLY OR VERTICALLY
				// IF THE DIRECTION IS 0 IT WILL BE HORIZONTAL AND IF THE DIRECTION IS 1 IT WILL BE VERTICAL

				//IF THE DIRECTION IS 0 IT WILL GO HERE AND COL + WORDLENGHT BECAUSE IT WILL CHECK IF THE WORD WILL FIT IN THE GRID
				// FOR EXAMPLE THE PROVIDED COL IS 5 AND THE WORD LENGTH IS 5 . 5 + 5 IS 10 SO IT WILL FIT IN THE GRID
				// OUR GRID IS 14 THATS WHY WE NEED TO CHECK IF THE WORD WILL FIT IN THE GRID
				if (direction == 0 && col + word.length < 14) {
					isInserted = true;
				    
					//THIS WILL CHECK IF THE CELL IS EMPTY OR NOT  SO WHEN THE CELL IS NOT EMPTY IT WILL NOT INSERT THE WORD
					//IT WILL EMMEDIATELY BREAK THE LOOP WHEN THE CELL IS NOT EMPTY
					// BUT WHEN THE CELL IS EMPTY IT WILL GO  TO THE LINE 146
				    for(int i = 0 ; i < word.length ; i++){
                           if (gridBox[row][col + i] !=  '\0') {
							   isInserted = false ;
							   break;
						   }
						}	
                        //THIS WILL INSERT THE WORD IN THE GRID
						//COL + I BECAUSE IT WILL INSERT THE WORD IN THE GRID HORIZONTALLY
						if(isInserted){
							for(int i = 0 ; i < word.length ; i++){
								gridBox[row][col + i] = word[i];
							}
							
						}
				}


				// SAME LOGIC HERE BUT THIS TIME IT WILL INSERT THE WORD VERTICALLY
				else if (direction == 1 && row + word.length < 14) {
					isInserted = true;
				
				    for(int i = 0 ; i < word.length ; i++){
						   if (gridBox[row + i][col] !=  '\0') {
							   isInserted = false ;
							   break;
						   }
						}	

						if(isInserted){
							for(int i = 0 ; i < word.length ; i++){
								gridBox[row + i][col] = word[i];
							}
							
						}

				}

				
				//THIS WILL BREAK THE LOOP IF THE WORD IS INSERTED
			} //while loop

			}
            
			//THEN THIS IS THE INSERTIONOF THE CHAR IN THE GRID AS A JLABEL
			for(int i = 0 ; i < 14 ; i++){

				for(int j = 0 ; j < 14 ; j++){
					//THIS WILL CHECK IF THE CELL IS EMPTY OR NOT THEN IF IT IS EMPTY IT WILL INSERT RANDOM CHARACTERS
					if(gridBox[i][j] == '\0'){
						gridBox[i][j] = (char)(random.nextInt(26) + 'A');
					}

					JLabel wordBox = new JLabel(Character.toString(gridBox[i][j]));
					wordBox.setForeground(Color.BLACK);
					wordBox.setHorizontalAlignment(SwingConstants.CENTER);
					wordBox.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
					gridWordBoxContainer.add(wordBox);
		
				}
			}
        
        contentPane.add(gridWordBoxContainer);
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
                        guessWordTextField.setText("");
	                    JOptionPane.showMessageDialog(null, "Wrong Answer", "WRONG", JOptionPane.ERROR_MESSAGE);
						break;
					}
					while(isCorrect){
						guessWordTextField.setText("");
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


	/*public void setHorizontalWord(int letterPosition, int j, int characterInsertionToGrid , JPanel gridWordBox  ){
		   if(characterInsertionCounter < guessWordCharacters[characterInsertionToGrid].length && characterInsertionToGrid < 9) {
               JLabel wordBox = new JLabel(Character.toString(guessWordCharacters[characterInsertionToGrid][characterInsertionCounter]));
			   wordBox.setForeground(Color.GREEN);
			   wordBox.setHorizontalAlignment(SwingConstants.CENTER);
			   wordBox.setFont(new Font("Haettenschweiler", Font.PLAIN, 28));
			   gridWordBox.add(wordBox);
		   }   
		}*/


	

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

