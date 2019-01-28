package org.team997coders.spartanlib.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.team997coders.spartanlib.subsystems.CameraMount;

public class SlewCameraToAngleUnitTest {
  @Test
  public void itFinishesWhenAnglesAreReached() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(false);
    when(cameraMountMock.atTiltLimit()).thenReturn(false);
    when(cameraMountMock.getRoundedTiltAngleInDegrees()).thenReturn(45);
    when(cameraMountMock.getRoundedPanAngleInDegrees()).thenReturn(90);
    try(SlewCameraToAngle slewCameraToAngle = new SlewCameraToAngle(cameraMountMock, 90, 45, 0.45, 20.0)) {
      // Act
      boolean actual = slewCameraToAngle.isFinished();

      //Assert
      assertEquals(true, actual);
    }
  }

  @Test
  public void itDoesNotFinishWhenOnlyOneAngleIsReached() {
    // Assemble
    CameraMount cameraMountMock = mock(CameraMount.class);
    when(cameraMountMock.atPanLimit()).thenReturn(false);
    when(cameraMountMock.atTiltLimit()).thenReturn(false);
    when(cameraMountMock.getRoundedTiltAngleInDegrees()).thenReturn(45);
    when(cameraMountMock.getRoundedPanAngleInDegrees()).thenReturn(90);
    try(SlewCameraToAngle slewCamera = new SlewCameraToAngle(cameraMountMock, 90, 90, 0.45, 20.0)) {
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
    when(cameraMountMock.getRoundedTiltAngleInDegrees()).thenReturn(45);
    when(cameraMountMock.getRoundedPanAngleInDegrees()).thenReturn(90);
    try(SlewCameraToAngle slewCameraToAngle = new SlewCameraToAngle(cameraMountMock, 180, 180, 0.45, 20.0)) {
      // Act
      boolean actual = slewCameraToAngle.isFinished();

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
    when(cameraMountMock.getRoundedTiltAngleInDegrees()).thenReturn(45);
    when(cameraMountMock.getRoundedPanAngleInDegrees()).thenReturn(90);
    try(SlewCameraToAngle slewCameraToAngle = new SlewCameraToAngle(cameraMountMock, 180, 180, 0.45, 20.0)) {
      // Act
      boolean actual = slewCameraToAngle.isFinished();

      //Assert
      assertEquals(false, actual);
    }
  }
}