package com.hc.library.zxing.camera;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import java.util.List;

public final class FlashlightManager {

    static {
    }

    private FlashlightManager() {
    }
    /**
     * 通过设置Camera打开闪光灯
     *
     * @param camera
     */
    public static boolean openFlashLight(Camera camera) {
        return toggleFlashlightEnabled(camera,true);
    }

    /**
     * 通过设置Camera关闭闪光灯
     *
     * @param camera
     * @return 闪光灯的状态
     */
    public static boolean closeFlashLight(Camera camera) {
        return toggleFlashlightEnabled(camera,false);
    }

    public static boolean ToggleFlashlightEnabled(Camera camera) {
        return toggleFlashlightEnabled(camera,null);
    }

    /**
     * 是否能打开闪光灯
     * */
    public static boolean isCanOpenFlashlight(Camera camera){
        if (camera == null) {
            return false;
        }
        Parameters parameters = camera.getParameters();
        if (parameters == null) {
            return false;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();

        // Check if camera flash exists
        if (flashModes == null) {
            return false;
        }

        return flashModes.contains(Parameters.FLASH_MODE_TORCH);
    }

    private static boolean toggleFlashlightEnabled(Camera camera,Boolean isEnabled) {
        if (camera == null) {
            return false;
        }
        Parameters parameters = camera.getParameters();
        if (parameters == null) {
            return false;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();

        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return false;
        }

        if (!Parameters.FLASH_MODE_OFF.equals(flashMode) && (isEnabled == null || !isEnabled)) {
            if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);

                return false;
            }
        } else if (!Parameters.FLASH_MODE_TORCH.equals(flashMode) && (isEnabled == null || isEnabled)) {
            if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);

                return true;
            }
        }

        return false;
    }
}
