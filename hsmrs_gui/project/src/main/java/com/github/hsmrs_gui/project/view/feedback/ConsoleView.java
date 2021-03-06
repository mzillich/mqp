/**********************************************************************
***	HSMRS MQP - Donald Bourque - Thomas DeSilva - Nicholas Otero	***
***																	***
***	ConsoleView.java												***
***		This class defines the Console module. The Console module	***
***		is it's own JPanel which contains a JTabbedPane. Each tab 	***
***		of the tab pane is a Channel of the Console module. Console	***
***		messages are filtered into their corresponding Channels.	***
***		Each Channel contains a JTextPane which is set to markup	***
***		text using HTML. The text pane's also exist within a scroll	***
***		pane which defaults to the bottom of the text pane.			*** 
**********************************************************************/

package src.main.java.com.github.hsmrs_gui.project.view.feedback;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.github.hsmrs_gui.project.GuiNode;

import src.main.java.com.github.hsmrs_gui.project.controller.ConsoleController;
import src.main.java.com.github.hsmrs_gui.project.model.robot.RobotListModel;
import src.main.java.com.github.hsmrs_gui.project.model.robot.RobotModel;
import net.miginfocom.swing.MigLayout;

public class ConsoleView extends JPanel implements 
ListDataListener{

	private JTabbedPane sourceTabbedPane;
	private JTextArea output;
	private Map<RobotModel, String> outputs;
	private String allOutput = "";

	/**
	 * The constructor for the ConsoleView class. This creates
	 * all of the GUI elements for the Console module.
	 */
	public ConsoleView() {
		setLayout(new MigLayout("fill, insets 0", "[]", "[]"));
		setBackground(Color.decode("0XC0BCB6"));

		sourceTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		output = new JTextArea("");
		output.setLineWrap(true);
		output.setEditable(false);

		ConsoleController cc = ConsoleController.getInstance();
		cc.setConsoleView(this);

		for (String channelName : cc.getChannelNames()) {
			JTextPane temp = new JTextPane();
			temp.setContentType("text/html");
			temp.setEditable(false);
			sourceTabbedPane.addTab(channelName, new JScrollPane(temp));
		}

		RobotListModel.getInstance().addListDataListener(this);
		add(sourceTabbedPane, "grow");
	}

	/**
	 * Adds a channel to the Console.
	 * @param name The name of the Channel.
	 */
	public void addChannel(String name) {
		System.out.println("Add Console Channel in Console View");
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false); 
		sourceTabbedPane.addTab(name, new JScrollPane(textPane));
	}

	/**
	 * Removes a channel from the Console.
	 * @param name The name of the channel to remove.
	 */
	public void removeChannel(String name) {
		for (int i = 0; i < sourceTabbedPane.getTabCount(); i++) {
			if (sourceTabbedPane.getTitleAt(i).equals(name)) {
				sourceTabbedPane.removeTabAt(i);
				return;
			}
		}
	}
	
	/**
	 * Removes a channel from the Console through an index.
	 * @param index The index of the channel to remove.
	 */
	public void removeChannel(int index) {
		sourceTabbedPane.remove(index + 2);
	}

	/**
	 * Sets the channel's log to the the given log represented as a String.
	 * @param channelName The name of the Channel whose text is to be set.
	 * @param logText The text to set the Channel's log to.
	 */
	public void setChannelLogText(String channelName, String logText) {
		//Get the text pane for the given channel.
		JTextPane target = extractTextPane(channelName);
		
		//Set the text pane's text while using HTML markup
		HTMLDocument doc = new HTMLDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit)target.getEditorKit();
		try{
			editorKit.insertHTML(doc, 0, logText, 0, 0, null);
			target.setDocument(doc);
		}catch(Exception e)
		{
			GuiNode.getLog().info(
					"Failed to set the log of channel: " + channelName + " to: "
					+ logText);
		}
		
		//Default the scroll to the bottom of the text pane.
		target.setCaretPosition(target.getDocument().getLength());
		return;

	}

	/**
	 * This method returns the text pane which displays the log of
	 * the channel whose name is given as an argument.
	 * @param channelName The name of the channel.
	 * @return The text pane of the named channel.
	 */
	private JTextPane extractTextPane(String channelName) {
		//Loop through each tab of the tabbed pane
		for (int i = 0; i < sourceTabbedPane.getTabCount(); i++) {
			//If the tab's title matches the channel name
			if (sourceTabbedPane.getTitleAt(i).equals(channelName)) {
				//Get the scroll pane container
				JScrollPane spContainer = (JScrollPane) sourceTabbedPane
						.getComponentAt(i);
				//The text pane is hidden in the viewport of the
				//scroll pane along with other hidden components.
				//It is necessary to search through these components
				//to find the text pane.
				JViewport vContainer = spContainer.getViewport();
				Component[] comps = vContainer.getComponents();
				for (int j = 0; j < comps.length; j++) {
					if (comps[j] instanceof JTextPane) {
						return ((JTextPane) comps[j]);
					}
				}
			}
		}
		//If no text pane is found, return null.
		return null;
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		//The console does not care if the contents are changed
		
	}

	//Listeners for changes in the ConsoleModel.
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		/*String newRobotName = ((RobotModel)
				((RobotListModel)
						e.getSource()).getElementAt(e.getIndex0())).getName();
		ConsoleController.getInstance().addConsoleChannel(newRobotName);
		*/
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		/*ConsoleController.getInstance().removeConsoleChannel(name);
		e.
		removeChannel(e.getIndex0());
		*/
	}

}
