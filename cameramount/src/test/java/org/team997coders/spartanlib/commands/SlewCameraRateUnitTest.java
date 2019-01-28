package org.team997coders.spartanlib.commands;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.team997coders.spartanlib.commands.SlewCamera;
import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;
import org.team997coders.spartanlib.subsystems.CameraMount;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

/**
 * Units tests for the SlewCamera command to test
 * rate parameters are calculated correctly
 */
@RunWith(value = Parameterized.class)
public class SlewCameraRateUnitTest {

  private double slewRate;
  private double heartbeat;
  private double panRateFactor;
  private double tiltRateFactor;
  private int expectedPanAngleDelta;
  private int expectedTiltAngleDelta;

  // Inject test values via constructor
  public SlewCameraRateUnitTest(double slewRate, 
      double heartbeat, 
      double panRateFactor, 
      double tiltRateFactor, 
      int expectedPanAngleDelta, 
      int expectedTiltAngleDelta) {
    this.slewRate = slewRate;
    this.heartbeat = heartbeat;
    this.panRateFactor = panRateFactor;
    this.tiltRateFactor = tiltRateFactor;
    this.expectedPanAngleDelta = expectedPanAngleDelta;
    this.expectedTiltAngleDelta = expectedTiltAngleDelta;
  }

	// name attribute is optional, provide an unique name for test
	// multiple parameters, uses Collection<Object[]>
  @Parameters(name = "{index}")
  public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][]{
              {0.45, 20.0, 1.0, 1.0, 8, 8},
              {0.45, 20.0, 0.5, 0.25, 4, 2},
              {0.45, 20.0, -0.5, -0.25, -4, -2}
      });
  }

  @Test
  public void itCalculatesDegreesPerHeartbeatCorrectly() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    IJoystickValueProvider panRateProviderMock = mock(IJoystickValueProvider.class);
    when(panRateProviderMock.getValue()).thenReturn(panRateFactor);
    IJoystickValueProvider tiltRateProviderMock = mock(IJoystickValueProvider.class);
    when(tiltRateProviderMock.getValue()).thenReturn(tiltRateFactor);
    SlewCamera slewCamera = new SlewCamera(cameraMountMock, panRateProviderMock, tiltRateProviderMock, slewRate, heartbeat);

    //Act
    slewCamera.execute();

    //Assert
    verify(cameraMountMock, times(1)).panToAngle(expectedPanAngleDelta);
    verify(cameraMountMock, times(1)).tiltToAngle(expectedTiltAngleDelta);
  }
}