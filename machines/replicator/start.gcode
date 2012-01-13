(**** beginning of start.gcode ****)
(This is start code for The Replicator running a single material print)
M73 P0 (enable build progress)
G21 (set units to mm)
G90 (set positioning to absolute)
G10 P1 X16.5 Y0 Z-0.3 (Designate T0 Offset)
G10 P2 X-16.5 Y0 Z-0.3 (Designate T1 Offset)
G55 (Recall offset cooridinate system for T1)
M109 S80 T0 (set HBP temperature)
M6 T0
M104 S225 T0 (set extruder temperature)
M109 S110 T0 (set HBP temperature)
(**** begin homing ****)
G161 Z F1100 (home Z axis to minimum)
G92 Z-5 (duck Z down 5)
G1 Z0.0 (duck Z down 5)
M18 Z
G162 X Y F2500 (home XY axes maximum)
G161 Z F100 (home Z axis minimum)
M132 X Y (Recall stored home offsets for XY axis)
G92 Z0 A0 B0 (Set Z, A, B to 0)
(**** end homing ****)
G1 X117 Y-70 Z10 F3300.0 (move to waiting position)
M6 T0 (wait for toolhead parts, nozzle, HBP, etc., to reach temperature)
M108 R3.0 T0
G0 X117 Y-70 (Position Nozzle)
G0 Z0.6     (Position Height)
M108 R4.0   (Set Extruder Speed)
M101        (Start Extruder)
G4 P1500    (Create Anchor)
(**** end of start.gcode ****)
