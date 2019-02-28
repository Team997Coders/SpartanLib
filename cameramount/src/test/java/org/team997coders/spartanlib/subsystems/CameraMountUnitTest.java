package org.team997coders.spartanlib.subsystems;

import org.junit.Test;

import org.team997coders.spartanlib.helpers.TestLimitsHelper;
import org.team997coders.spartanlib.interfaces.IServo;
import org.team997coders.spartanlib.subsystems.CameraMount;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Units tests for the CameraMount subsystem
 */
public class CameraMountUnitTest {

  @Test
  public void itCanDetectWhenAtLowerPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 180);

    // Act
    helper.getCameraMount().panToAngle(4);

    // Assert
    assertTrue(helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenAtUpperPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 170);

    // Act
    helper.getCameraMount().panToAngle(180);

    // Assert
    assertTrue(helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenAtLowerTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 180);

    // Act
    helper.getCameraMount().tiltToAngle(4);

    // Assert
    assertTrue(helper.getCameraMount().atTiltLimit());
  }

  @Test
  public void itCanDetectWhenAtUpperTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 170, 5, 170);

    // Act
    helper.getCameraMount().tiltToAngle(180);

    // Assert
    assertTrue(helper.getCameraMount().atTiltLimit());
  }

  @Test
  public void itCanDetectWhenWithinPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 100, 150);

    // Act
    helper.getCameraMount().panToAngle(125);

    // Assert
    assertTrue(!helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenWithinTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 100, 150);

    // Act
    helper.getCameraMount().tiltToAngle(100);

    // Assert
    assertTrue(!helper.getCameraMount().atTiltLimit());
  }

  @Test
  public void itTiltsTheMount() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, 100, 150)) {

      // Act
      cameraMount.tiltToAngle(120);

      // Assert
      assertEquals(120, cameraMount.getRoundedTiltAngleInDegrees());
      verify(tiltServoMock, times(1)).write(cameraMount.getRoundedTiltAngleInDegrees());
    }
  }

  @Test
  public void itPansTheMount() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, 100, 150)) {

      // Act
      cameraMount.panToAngle(120);

      // Assert
      assertEquals(120, cameraMount.getRoundedPanAngleInDegrees());
      verify(panServoMock, times(1)).write(cameraMount.getRoundedPanAngleInDegrees());
    }
  }

  @Test
  public void itReversesThePanMount() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, false, 100, 150, true)) {

      // Act
      cameraMount.panToAngle(140);

      // Assert
      // We should get back the angle requested from the public interface,
      // but internally, the class should have written the 180 degree compliment.
      assertEquals(140, cameraMount.getRoundedPanAngleInDegrees());
      verify(panServoMock, times(1)).write(40);
    }
  }

  @Test
  public void itReversesThePanMountLimits() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, false, 100, 150, true)) {

      // Act
      cameraMount.panToAngle(90);

      // Assert
      // 100 to 150 limits would translate to 30 to 80 reversed limits
      // So a 90 degree pan would be a 180 - 90 = 90 reversed angle,
      // which would get limited to 80 degrees.
      assertEquals(80, cameraMount.getRoundedPanAngleInDegrees());
      verify(panServoMock, times(1)).write(80);
    }
  }

  @Test
  public void itReversesTheTiltMount() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, true, 100, 150, false)) {

      // Act
      cameraMount.tiltToAngle(20);

      // Assert
      // We should get back the angle requested from the public interface,
      // but internally, the class should have written the 180 degree compliment.
      assertEquals(20, cameraMount.getRoundedTiltAngleInDegrees());
      verify(tiltServoMock, times(1)).write(160);
    }
  }

  @Test
  public void itReversesTheTiltMountLimits() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    try(CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, true, 100, 150, false)) {

      // Act
      cameraMount.tiltToAngle(0);

      // Assert
      // 10 to 180 limits would translate to 0 to 170 reversed limits
      // So a 0 degree pan would be a 180 - 0 = 180 reversed angle,
      // which would get limited to 170 degrees.
      assertEquals(170, cameraMount.getRoundedTiltAngleInDegrees());
      verify(tiltServoMock, times(1)).write(170);
    }
  }
}
