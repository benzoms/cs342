package Game;

import java.awt.*;
import javax.swing.*;

import Agents.ClientAgent;

public class GamePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct the game pane, creating the players and their hands.
	 */
	public GamePanel(){
		//init the board (done with swt designer)
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.setBackground(new Color(200, 196, 222));
		this.setBounds(0, 0, 200, 200);
		
		JTabbedPane OpponentsHands = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_OpponentsHands = new GridBagConstraints();
		gbc_OpponentsHands.insets = new Insets(0, 0, 5, 0);
		gbc_OpponentsHands.fill = GridBagConstraints.BOTH;
		gbc_OpponentsHands.gridx = 0;
		gbc_OpponentsHands.gridy = 0;
		add(OpponentsHands, gbc_OpponentsHands);
		
		JPanel Table = new JPanel();
		GridBagConstraints gbc_Table = new GridBagConstraints();
		gbc_Table.insets = new Insets(0, 0, 5, 0);
		gbc_Table.fill = GridBagConstraints.BOTH;
		gbc_Table.gridx = 0;
		gbc_Table.gridy = 1;
		add(Table, gbc_Table);
		
		JLabel lblNewLabel = new JLabel("Table of all other players");
		Table.add(lblNewLabel);
		
		JPanel MyTable = new JPanel();
		GridBagConstraints gbc_MyTable = new GridBagConstraints();
		gbc_MyTable.insets = new Insets(0, 0, 5, 0);
		gbc_MyTable.fill = GridBagConstraints.BOTH;
		gbc_MyTable.gridx = 0;
		gbc_MyTable.gridy = 2;
		add(MyTable, gbc_MyTable);
		
		JLabel lblNewLabel_1 = new JLabel("My Table");
		MyTable.add(lblNewLabel_1);
		
		JPanel LowerBox = new JPanel();
		GridBagConstraints gbc_LowerBox = new GridBagConstraints();
		gbc_LowerBox.fill = GridBagConstraints.BOTH;
		gbc_LowerBox.gridx = 0;
		gbc_LowerBox.gridy = 3;
		add(LowerBox, gbc_LowerBox);
		
		JPanel MyHand = new JPanel();
		LowerBox.add(MyHand);
		
		JPanel DeckDiscard = new JPanel();
		LowerBox.add(DeckDiscard);
		
		JLabel lblNewLabel_2 = new JLabel("Discard Pile and Draw Pile");
		DeckDiscard.add(lblNewLabel_2);
	}
	
	/*
	 * 
	 */
	public void setTable(){
		
	}
	
	/*
	 * 
	 */
	public void setMyHand(){
		
	}
	
	/*
	 * 
	 */
	public void setOthersInfo(){
		
	}

	/**
	 * @param agent , the agent that will observe events
	 * @param options , the list of options the observer wishes to listen to
	 */
	public void addObserver(ClientAgent agent, int[] options) {
		// TODO Auto-generated method stub
		
	}
}
