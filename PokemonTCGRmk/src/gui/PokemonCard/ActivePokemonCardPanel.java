package gui.PokemonCard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import arena.GameArena;
import cardAbstract.EnergyCard;
import energyCard.DoubleColorlessEnergy;
import energyCard.WaterEnergy;
import gui.JMultilineLabel;
import gui.MainGui;
import pokemonCard.ActivePokemonCard;

public class ActivePokemonCardPanel extends JPanel {
	
	private AdditionalPokemonCardInfo clicked;
	
	public ActivePokemonCardPanel(ActivePokemonCard apc, boolean isActive) {
		clicked = new AdditionalPokemonCardInfo(MainGui.MAIN_GUI, apc);
		onUpdate(apc, isActive);
	}

	public ActivePokemonCardPanel(JDialog parent, ActivePokemonCard apc, boolean isActive) {
		clicked = new AdditionalPokemonCardInfo(parent, apc);
		onUpdate(apc, isActive);
	}
	
	public void onUpdate(ActivePokemonCard apc, boolean isActive) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		((FlowLayout)this.getLayout()).setVgap(0);
		((FlowLayout)this.getLayout()).setHgap(0);
		JMultilineLabel info = new JMultilineLabel(apc.getName() + 
				"\nHP: " + (apc.getMaxHp() - apc.getDamage()) + "/" + apc.getMaxHp());
		this.add(info);
		info.setBorder(new LineBorder(Color.pink));
		info.addMouseListener(new ClickablePokemonInfoListener(apc, clicked));
		
		if (isActive) {
			JLabel actions = new JLabel("Perform action...");
			this.add(actions);
			actions.setBorder(new LineBorder(Color.black));
			actions.setPreferredSize(new Dimension(MainGui.ACTIVE_WIDTH, 20));
			actions.addMouseListener(new PerformActionListener());
		}
		
		JTextArea energies = new JTextArea();
		energies.setWrapStyleWord(true);
		energies.setLineWrap(true);
		energies.setOpaque(false);
		energies.setEditable(false);
		energies.setFocusable(false);
		String energy = "Energies:\n";
		for (EnergyCard ec : apc.getEnergyCards()) {
			energy += ec.energyType() + " ";
		}
		energies.setText(energy);
		energies.setBorder(new LineBorder(Color.green));
		JTextArea moves = new JTextArea();
		moves.setWrapStyleWord(true);
		moves.setLineWrap(true);
		moves.setOpaque(false);
		moves.setEditable(false);
		moves.setFocusable(false);
		String moveNames = "Moves:\n";
		if (apc.getMoves().length == 0) {
			moveNames += "Card has no moves.";
		} else {
			for (int i = 0 ; i < apc.getMoveNames().length; i++) {
				moveNames += apc.getMoveNames()[i] + "\n";
			}
		}
		moves.setText(moveNames);
		moves.setBorder(new LineBorder(Color.red));
		this.add(energies);
		this.add(moves);
		this.setBorder(new LineBorder(Color.blue));
	}
}
