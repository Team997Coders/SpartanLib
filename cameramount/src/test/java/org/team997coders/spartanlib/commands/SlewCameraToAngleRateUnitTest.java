package org.team997coders.spartanlib.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.team997coders.spartanlib.subsystems.CameraMount;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;

/**
 * Units tests for the SlewCameraToAngle command to test
 * rate parameters are calculated correctly
 */
@RunWith(value = Parameterized.class)
public class SlewCameraToAngleRateUnitTest {

  private double slewRate;
  private double heartbeat;
  private int panAngle;
  private int tiltAngle;
  private int expectedPanAngleDelta;
  private int expectedTiltAngleDelta;

  // Inject test values via constructor
  public SlewCameraToAngleRateUnitTest(double slewRate, 
      double heartbeat, 
      int panAngle, 
      int tiltAngle, 
      int expectedPanAngleDelta, 
      int expectedTiltAngleDelta) {
    this.slewRate = slewRate;
    this.heartbeat = heartbeat;
    this.panAngle = panAngle;
    this.tiltAngle = tiltAngle;
    this.expectedPanAngleDelta = expectedPanAngleDelta;
    this.expectedTiltAngleDelta = expectedTiltAngleDelta;
  }

	// name attribute is optional, provide an unique name for test
	// multiple parameters, uses Collection<Object[]>
  @Parameters(name = "{index}")
  public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][]{
              {0.45, 20.0, 90, 90, 8, 8},
              {0.45, 20.0, 90, 0, 8, 0},
              {0.45, 20.0, 0, 0, 0, 0}
      });
  }

  @Test
  public void itCalculatesDegreesPerHeartbeatCorrectly() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    SlewCameraToAngle slewCameraToAngle = new SlewCameraToAngle(cameraMountMock, panAngle, tiltAngle, slewRate, heartbeat);

    //Act
    slewCameraToAngle.execute();

    //Assert
    verify(cameraMountMock, times(1)).panToAngle(expectedPanAngleDelta);
    verify(cameraMountMock, times(1)).tiltToAngle(expectedTiltAngleDelta);
  }
}