(*** This script will guide you through ***)
(*** calibrating the start position on  ***)
(***          your Replicator           ***)

G21 (Use Milimeters as Units)
G90 (Absolute Positioning)
M18 (Disable stepper motors)
M01 (Move the extruder carriage until it lies in the center of the build plate, then press yes to continue.)
G92 X0 Y0 Z0 A0 B0 (Declare the current position to be (0,0,0,0,0))
G161 Z F1000 (Home Z axis minimum; go until reaching the end stop.)
G92 Z0.0
G162 X Y F4000 (Home X and Y axis maximum; go until reaching the end stop.)
M131 X Y Z A B (record the current coordinates to the motherboard)

M01 (Your coordinates are now saved! To adjust them, use the 'Motherboard Onboard Preferences' dialog in the Machine menu to change the Axis Offsets.)
