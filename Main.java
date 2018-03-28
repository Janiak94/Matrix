/*
 * Just a simple test program to
 * invert a 5x5 matrix
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Main extends JFrame{
	
	private Matrix m1;
	private Matrix m2;
	private int m1Height = 5;
	private int m1Width = 5;
	private JPanel top = new JPanel(new GridLayout(m1Height,m1Width));
	private JPanel bottom = new JPanel(new GridLayout(m1Height, m1Width));
	private KeyAdapter ka = new KeyAdapter(){
		public void keyReleased(KeyEvent ke){
			Element e = (Element) ke.getSource();
			if(e.getText().equals(""))
				return;
			m1.setElement(e.i, e.j, Double.parseDouble(e.getText()));
			update();
		}
	};
	
	
	
	public Main(){
		setVisible(true);
		setTitle("Matrix inversion");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.m1 = new Matrix(m1Height, m1Width);
		this.m2 = new Matrix(m1Height, m1Width);
		setLayout(new GridLayout(2,1));
		
		add(top);
		add(bottom);
		for(int i = 0; i < m1Height*m1Width; i++){
			top.add(new Element(i/m1Height, i%m1Width));
			((JTextField)top.getComponent(i)).addKeyListener(ka);
			((JTextField)top.getComponent(i)).setSize(400/m1Height,400/m1Width);
			bottom.add(new Element(i/m1Height, i%m1Width));
			((JTextField)bottom.getComponent(i)).setEnabled(false);
			((JTextField)bottom.getComponent(i)).setSize(400/m1Height,400/m1Width);
		}
		setSize(400,800);
		
	}
	
	public void update(){
		try{
			m2 = m1.inverse();
		}catch(Exception e){
			return;
		}
		for(int i = 0; i < m1Height*m1Width; i++){
			if(m2.getElement(i/m1Height, i%m1Width) == 0)
				( (Element) bottom.getComponent(i) ).setText("" + 0);
			else
				( (Element) bottom.getComponent(i) ).setText("" + m2.getElement(i/m1Height, i%m1Width));
		}
	}
	
	public static void main(String[] args){
		new Main();
	}
}

class Element extends JTextField{
	
	int i;
	int j;
	
	public Element(int i, int j){
		this.i = i;
		this.j = j;
		this.setText("" + 0);
		this.setHorizontalAlignment(CENTER);
		this.setFont(new Font("Arial", 0, 40));
	}
	

}