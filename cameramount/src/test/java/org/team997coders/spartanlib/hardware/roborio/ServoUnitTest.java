package org.team997coders.spartanlib.hardware.roborio;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServoUnitTest {
  @Test
  public void itAttaches() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);
    
    // Act

    // Assert
    assertEquals(1, servo.attached());
  }

  @Test
  public void itReportsBeingDetached() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);

    // Act
    servo.detach();

    // Assert
    assertEquals(0, servo.attached());
  }

  @Test
  public void itReattachesWhenWriting() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);
    servo.detach();

    // Act
    servo.write(0);

    // Assert
    assertEquals(1, servo.attached());
  }

  @Test
  public void itPreventsAttachingToAnotherPin() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    when(wpiServoMock.getChannel()).thenReturn(1);
    Servo servo = new Servo(wpiServoMock);

    // Act
    byte result = servo.attach(2);

    // Assert
    assertEquals(0, result);
  }

  @Test
  public void itReadsAngles() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    when(wpiServoMock.getAngle()).thenReturn(90D);
    Servo servo = new Servo(wpiServoMock);

    // Act
    int result = servo.read();

    // Assert
    assertEquals(90, result);
  }

  @Test
  public void itWritesAngles() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);

    // Act
    servo.write(90);

    // Assert
    verify(wpiServoMock, times(1)).setAngle(90D);
  }

  @Test(expected = IllegalArgumentException.class)
  public void itRejectsLargeAngles() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);

    // Act
    servo.write(181);
  }

  @Test(expected = IllegalArgumentException.class)
  public void itRejectsNegativeAngles() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);
    Servo servo = new Servo(wpiServoMock);

    // Act
    servo.write(-1);
  }

  @Test
  public void itSetsPWMLimitsUponConstruction() {
    // Assemble
    edu.wpi.first.wpilibj.Servo wpiServoMock = mock(edu.wpi.first.wpilibj.Servo.class);

    // Act
    Servo servo = new Servo(wpiServoMock, 544, 2333);

    //Assert
    verify(wpiServoMock, times(1)).setBounds(2.333, 0, 0, 0, 0.544);
  }
}