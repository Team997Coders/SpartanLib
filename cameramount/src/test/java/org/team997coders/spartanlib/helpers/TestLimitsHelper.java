package org.team997coders.spartanlib.helpers;

import org.team997coders.spartanlib.interfaces.IServo;
import org.team997coders.spartanlib.subsystems.CameraMount;

import static org.mockito.Mockito.*;

/**
 * DRY up boilerplate for unit testing limits
 */
public class TestLimitsHelper {
  private final IServo m_panServoMock;
  private final IServo m_tiltServoMock;
  private final CameraMount m_cameraMount;

  public TestLimitsHelper(int tiltLowerLimitInDegrees, 
      int tiltUpperLimitInDegrees, 
      int panLowerLimitInDegrees, 
      int panUpperLimitInDegrees) {
    m_panServoMock = mock(IServo.class);
    m_tiltServoMock = mock(IServo.class);
    m_cameraMount = new CameraMount(m_panServoMock, m_tiltServoMock, 10, 180, 5, 180);
  }

  public CameraMount getCameraMount() {
    return m_cameraMount;
  }
}