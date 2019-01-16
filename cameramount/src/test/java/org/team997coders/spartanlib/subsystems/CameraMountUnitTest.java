package org.team997coders.spartanlib.subsystems;

import org.junit.*;

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
    CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, 100, 150);

    // Act
    cameraMount.tiltToAngle(120);

    // Assert
    assertEquals(120, cameraMount.getTiltAngleInDegrees());
    verify(tiltServoMock, times(1)).write(cameraMount.getTiltAngleInDegrees());
    cameraMount.close();
  }

  @Test
  public void itPansTheMount() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, 100, 150);

    // Act
    cameraMount.panToAngle(120);

    // Assert
    assertEquals(120, cameraMount.getPanAngleInDegrees());
    verify(panServoMock, times(1)).write(cameraMount.getPanAngleInDegrees());
    cameraMount.close();
  }
}
