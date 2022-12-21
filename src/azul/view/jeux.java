package azul.view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class jeux
{
  JLabel text;
  
  
  jeux()
  {
	
	Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
	int longueur = tailleMoniteur.width;
	int hauteur = tailleMoniteur.height;
	
	
    JFrame f = new JFrame("Accueil");
    f.setBackground(Color.WHITE);
    f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    
    text = new JLabel();
    text.setBounds(longueur/2-160,hauteur/2,320,30);
    text.setText("Veuillez choissir le nombre de joueurs");
    text.setFont(new Font("Serif", Font.BOLD, 20));
    
    
    JButton joueur2 = new JButton("2 joueurs");
    JButton joueur3 = new JButton("3 joueurs");
    JButton joueur4 = new JButton("4 joueurs");
    JButton regle = new JButton("Règle ");
    
    joueur2.setBounds(175,hauteur/5,longueur/5,30);
    joueur3.setBounds(175+longueur/5+100,hauteur/5,longueur/5,30);
    joueur4.setBounds(175+2*longueur/5+200,hauteur/5,longueur/5,30);
    regle.setBounds(100, hauteur*4/5,100,30);
    joueur2.setBackground(Color.WHITE);
    joueur3.setBackground(Color.WHITE);
    joueur4.setBackground(Color.WHITE);
    regle.setBackground(Color.WHITE);
    
    
    f.add(text);
    f.add(joueur2);
    f.add(joueur3);
    f.add(joueur4);
    f.add(regle);
    
    joueur2.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		f.dispose();
    		
    		JFrame jeux2 = new JFrame("jeux");
    		jeux2.setBackground(Color.WHITE);
    		jeux2.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	    
    		JLabel text1;
    		JLabel text2;	
    		
    		JPanel p = new JPanel();
    		p.setBounds(0, hauteur/2, longueur/2, hauteur/2);
    		p.setBackground(Color.WHITE);
    		
    		JPanel p2 = new JPanel();
    		p2.setBounds(longueur/2, hauteur/2, longueur/2, hauteur/2);
    		p2.setBackground(Color.WHITE);
    		
    			
    		
    		text1 = new JLabel();
    	    text1.setBounds(0,0,100,30);
    	    text1.setText("joueur 1");
    	    
    	    text2 = new JLabel();
    	    text2.setBounds(0,hauteur/2,100,30);
    	    text2.setText("joueur 2");
    	    
    	   
    		p.add(text1);
    		p2.add(text2);
    		
    		
    		jeux2.add(p);
    		jeux2.add(p2);
    		  		
    		jeux2.setLayout(null);
    		jeux2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		jeux2.setVisible(true);
    	}
    });
    joueur3.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
f.dispose();
    		
    		JFrame jeux2 = new JFrame("jeux");
    		jeux2.setBackground(Color.WHITE);
    		jeux2.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	    
    		JLabel text1;
    		JLabel text2;
    		JLabel text3;  			
    		
    		
    		
    		JPanel p = new JPanel();
    		p.setBounds(0, hauteur/2, longueur/3, hauteur/2);
    		p.setBackground(Color.WHITE);
    		
    		JPanel p2 = new JPanel();
    		p2.setBounds(longueur/3, hauteur/2, longueur/3, hauteur/2);
    		p2.setBackground(Color.WHITE);
    		
    		JPanel p3 = new JPanel();
    		p3.setBounds(longueur*2/3, hauteur/2, longueur/3, hauteur/2);
    		p3.setBackground(Color.WHITE);
    		
    		
    		
    		text1 = new JLabel();
    	    text1.setBounds(0,0,100,30);
    	    text1.setText("joueur 1");
    	    
    	    text2 = new JLabel();
    	    text2.setBounds(0,hauteur/2,100,30);
    	    text2.setText("joueur 2");
    	    
    	    
    	    text3 = new JLabel();
    	    text3.setBounds(longueur*2/3, 0, 100, 30 );
    	    text3.setText("joueur 3");
    	    
    	    
    	    
    		
    		p.add(text1);
    		p2.add(text2);
    		p3.add(text3);
    		
    		
    		
    		jeux2.add(p);
    		jeux2.add(p2);
    		jeux2.add(p3);
    		
    		
    		
    		
    		jeux2.setLayout(null);
    		jeux2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		jeux2.setVisible(true);
    	}
    });
    joueur4.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		
    		f.dispose();
    		
    		JFrame jeux4 = new JFrame("jeux");
    		jeux4.setBackground(Color.WHITE);
    		jeux4.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	    
    		JLabel text1;
    		JLabel text2;
    		JLabel text3;
    		JLabel text4;
    		
    		
    		
    		
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
    		
    		
    		jeux4.add(p);
    		jeux4.add(p2);
    		jeux4.add(p3);
    		jeux4.add(p4);
    		
    		
    		
    		jeux4.setLayout(null);
    		jeux4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		jeux4.setVisible(true);
    	}
    });
    
    regle.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		JFrame règle = new JFrame("règle");
    		
    		JLabel lien = new JLabel("https://www.regledujeu.fr/azul/");
    		
    		JButton retour = new JButton("retour");
    		retour.setBackground(Color.WHITE);
    		retour.setBounds(30,150,100,30);
    		
    		
    		lien.setForeground(Color.BLUE.darker());
    		lien.setBounds(0,100,200,30);
    		
    		règle.add(lien);
    		règle.add(retour);
    		
    		retour.addActionListener(new ActionListener()
    	    {
    	    	public void actionPerformed(ActionEvent e)
    	    	{
    	    		règle.dispose();
    	    	}
    	    });
    		
    		règle.setSize(250,250);
    		règle.setLayout(null);
    		règle.setVisible(true);
    	}
    });
    
    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    
  }
  

  
  public static void main(String[] args) {
    new jeux();
  }
}