package azul.view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class test
{
  JLabel text1;
  JLabel text2;
  JLabel text3;
  JLabel text4;
  
  
  test()
  {
	
	JFrame test = new JFrame("test");
	test.setBackground(Color.WHITE);
    test.setExtendedState(JFrame.MAXIMIZED_BOTH);
    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	int longueur = tailleMoniteur.width;
	int hauteur = tailleMoniteur.height;
	
	
	
	
	
	JPanel p = new JPanel();
	p.setBounds(0, 0, longueur/3, hauteur/2);
	p.setBackground(Color.WHITE);
	
	JPanel p2 = new JPanel();
	p2.setBounds(0, hauteur/2, longueur/3, hauteur/2);
	p2.setBackground(Color.WHITE);
	
	JPanel p3 = new JPanel();
	p3.setBounds(longueur*2/3, 0, longueur/3, hauteur/2);
	p3.setBackground(Color.WHITE);
	
	JPanel p4 = new JPanel();
	p4.setBounds(longueur*2/3, hauteur/2, longueur/3, hauteur/2);
	p4.setBackground(Color.WHITE);
	
	
	text1 = new JLabel();
    text1.setBounds(0,0,100,30);
    text1.setText("joueur 1");
    
    text2 = new JLabel();
    text2.setBounds(0,hauteur/2,100,30);
    text2.setText("joueur 2");
    
    
    text3 = new JLabel();
    text3.setBounds(longueur*2/3, 0, 100, 30 );
    text3.setText("joueur 3");
    
    
    text4 = new JLabel();
    text4.setBounds(longueur*2/3, hauteur/2, 100, 30);
    text4.setText("joueur 4");
	
	p.add(text1);
	p2.add(text2);
	p3.add(text3);
	p4.add(text4);
	
	
	test.add(p);
	test.add(p2);
	test.add(p3);
	test.add(p4);
	
	
	
	test.setLayout(null);
	test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	test.setVisible(true);
    
  }
  

  
  public static void main(String[] args) {
    new test();
  }
}