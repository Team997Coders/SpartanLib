package org.team997coders.spartanlib.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;
import org.team997coders.spartanlib.subsystems.CameraMount;

public class SlewCameraUnitTest {
  @Test
  public void itFinishesWhenRateProvidersAreZero() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(false);
    when(cameraMountMock.atTiltLimit()).thenReturn(false);
    IJoystickValueProvider panRateProviderMock = mock(IJoystickValueProvider.class);
    when(panRateProviderMock.getValue()).thenReturn(0.0);
    IJoystickValueProvider tiltRateProviderMock = mock(IJoystickValueProvider.class);
    when(tiltRateProviderMock.getValue()).thenReturn(0.0);
    try(SlewCamera slewCamera = new SlewCamera(cameraMountMock, panRateProviderMock, tiltRateProviderMock, 0.45, 20.0)) {
      // Act
      boolean actual = slewCamera.isFinished();

      //Assert
      assertEquals(true, actual);
    }
  }

  @Test
  public void itDoesNotFinishWhenOnlyOneRateProviderIsZero() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(false);
    when(cameraMountMock.atTiltLimit()).thenReturn(false);
    IJoystickValueProvider panRateProviderMock = mock(IJoystickValueProvider.class);
    when(panRateProviderMock.getValue()).thenReturn(0.0);
    IJoystickValueProvider tiltRateProviderMock = mock(IJoystickValueProvider.class);
    when(tiltRateProviderMock.getValue()).thenReturn(1.0);
    try(SlewCamera slewCamera = new SlewCamera(cameraMountMock, panRateProviderMock, tiltRateProviderMock, 0.45, 20.0)) {
      // Act
      boolean actual = slewCamera.isFinished();

      //Assert
      assertEquals(false, actual);
    }
  }

  @Test
  public void itFinishesWhenLimitsAreReached() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(true);
    when(cameraMountMock.atTiltLimit()).thenReturn(true);
    IJoystickValueProvider panRateProviderMock = mock(IJoystickValueProvider.class);
    when(panRateProviderMock.getValue()).thenReturn(1.0);
    IJoystickValueProvider tiltRateProviderMock = mock(IJoystickValueProvider.class);
    when(tiltRateProviderMock.getValue()).thenReturn(1.0);
    try(SlewCamera slewCamera = new SlewCamera(cameraMountMock, panRateProviderMock, tiltRateProviderMock, 0.45, 20.0)) {
      // Act
      boolean actual = slewCamera.isFinished();

      //Assert
      assertEquals(true, actual);
    }
  }

  @Test
  public void itDoesNotFinishWhenOnlyOneLimitIsReached() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(true);
    when(cameraMountMock.atTiltLimit()).thenReturn(false);
    IJoystickValueProvider panRateProviderMock = mock(IJoystickValueProvider.class);
    when(panRateProviderMock.getValue()).thenReturn(1.0);
    IJoystickValueProvider tiltRateProviderMock = mock(IJoystickValueProvider.class);
    when(tiltRateProviderMock.getValue()).thenReturn(1.0);
    try(SlewCamera slewCamera = new SlewCamera(cameraMountMock, panRateProviderMock, tiltRateProviderMock, 0.45, 20.0)) {
      // Act
      boolean actual = slewCamera.isFinished();

      //Assert
      assertEquals(false, actual);
    }
  }
}