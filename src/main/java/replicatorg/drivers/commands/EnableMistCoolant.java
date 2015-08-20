package replicatorg.drivers.commands;

import replicatorg.drivers.Driver;

public class EnableMistCoolant implements DriverCommand {

  @Override
  public void run(Driver driver) {
    driver.enableMistCoolant();
  }
}
