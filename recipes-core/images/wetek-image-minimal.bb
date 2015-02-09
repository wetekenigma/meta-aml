SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash package-management ssh-server-dropbear"
LICENSE = "MIT"

inherit core-image


IMAGE_INSTALL += " yavta v4l-utils"
