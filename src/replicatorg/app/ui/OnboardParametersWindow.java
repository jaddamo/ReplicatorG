package replicatorg.app.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import replicatorg.drivers.Driver;
import replicatorg.drivers.OnboardParameters;
import replicatorg.machine.model.ToolModel;

public class OnboardParametersWindow extends JFrame {
	
	private final JTabbedPane paramsTabs;
	private final JButton cancelButton;
	
	private final OnboardParameters targetParams;
	private final Driver driver;
	
	public OnboardParametersWindow(OnboardParameters targetParams, Driver driver)
	{
		super("Update Machine Options.");
		
		this.targetParams = targetParams;
		this.driver = driver;
		
		paramsTabs = new JTabbedPane();
		add(paramsTabs, "wrap");

		paramsTabs.addTab("Motherboard", new MachineOnboardParameters(targetParams, driver));
		

		List<ToolModel> tools = driver.getMachine().getTools();
		
		for(ToolModel t : tools)
		{
			paramsTabs.addTab("Extruder " + t.getIndex(), new ExtruderOnboardParameters(targetParams, t));
			
		}

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OnboardParametersWindow.this.dispose();
			}
		});
		add(cancelButton, "tag ok");
		
		pack();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
	}

}
