package leets.bookmark.domain.bookmark.domain.entity.enums;

import leets.bookmark.domain.bookmark.application.exception.InvalidDeviceTypeException;

public enum DeviceType {
    PC,
    MOBILE;

    public static DeviceType from(String platform) {
        return java.util.Arrays.stream(DeviceType.values())
            .filter(device -> device.name().equalsIgnoreCase(platform))
            .findFirst()
            .orElseThrow(InvalidDeviceTypeException::new);
    }
}
