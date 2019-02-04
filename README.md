# SpartanLib Master Project

This project is the multi-project root for all FRC competition Spartan Robotics reusable code. Each project should be a standalone
library that can be included into a robot program or other standalone application. The assumption should be made that users of the
library will pull in their own wpilib dependencies since this is how GradleRio is expecting to work. Thus, you should use the 
compileOnly dependency configuration where referencing wpi artifacts.

## Naming Standards

1. All packages that provide things should be pluralized.  For example, use subsystems vs. subsystem.

## ToDos

1. ~~Create a fat jar builder task to bundle all libraries into one jar for root project.~~
2. ~~Add jitpack.io bits so that artifacts from this library can be referenced easily from external robot programs.~~

[![](https://jitpack.io/v/Team997Coders/SpartanLib.svg)](https://jitpack.io/#Team997Coders/SpartanLib)
