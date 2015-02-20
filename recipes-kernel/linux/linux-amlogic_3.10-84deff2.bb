DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"


inherit kernel

S = "${WORKDIR}/linux-amlogic-${PV}"
#B = "${WORKDIR}/linux-amlogic-${PV}"

DEPENDS += " xz-native bc-native "

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"
LINUX_VERSION ?= "3.10-84deff2"
SRCREV="84deff28b859fb9c53e5f8fe1565228c9ea6d862"
LINUX_VERSION_EXTENSION ?= "amlogic"

PR = "r0"

COMPATIBLE_MACHINE = "wetekplay"

# for kernel
SRC_URI[md5sum] = "0a48fc9262c64b6fd229d26a2caca8b1"
SRC_URI[sha256sum] = "08fe2eb62ff14a02e93d85125d2b6adcf4cb1645eb18d67a717d2dcb2bf8ac6f"

SRC_URI = " \
    http://sources.openelec.tv/devel/linux-amlogic-3.10-84deff2.tar.xz \
    file://000-svn_rev.patch \
    file://10-arm-show-present-cpu-instead-of-online-cpu-in-proc-c.patch \
    file://20-wetek_dvb_code.patch \
    file://40-no_dev_console.patch \
    file://50-turn_power_led_into_standby_mode_after_poweroff.patch \
    file://60-xattr.patch \
    file://70-amports_ignore_fec_control.patch \
    file://70-remove-amvideocap-spam.patch \
    file://100-fix-23hzpixelclock.patch \
    file://110-add_wetekplay_led.patch \
    file://111-add-remote-control-ledtrigger.patch \
    file://130-switch_irq_to_CPU1.patch \
    file://wetekplay.dtd \ 
    file://defconfig \
"

do_configure_prepend () {
    cp -f ${WORKDIR}/wetekplay.dtd ${WORKDIR}/linux-amlogic-3.10-84deff2/arch/arm/boot/dts/amlogic/
}

# Put debugging files into dbg package
FILES_kernel-dbg += "/usr/src/kernel/drivers/amlogic/input/touchscreen/gslx680/.debug"

do_compile_prepend () {
       unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
       if test -n "${KERNEL_DEVICETREE}"; then
                for DTB in ${KERNEL_DEVICETREE}; do
                        if echo ${DTB} | grep -q '/dts/'; then
                                bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
                                DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
                        fi
                        oe_runmake ${DTB} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
                done
        # Create directory, this is needed for out of tree builds
        mkdir -p ${B}/arch/arm/boot/dts/amlogic/
        fi
}

do_install_append () {
        # removed binary stuff from Amlogic
        if [ -f ${D}/usr/src/kernel/mkbootimg ]
        then
            rm ${D}/usr/src/kernel/mkbootimg
        fi
        # This is x86 elf code...
        if [ -f ${D}/usr/src/kernel/arch/arm/boot/mkimage ]
        then
            rm ${D}/usr/src/kernel/arch/arm/boot/mkimage
        fi
        # remove *.z from installation path those are object files from amlogic for binary modules
        find ${D}/usr/src/kernel -type f -name "*.z" | xargs rm -f
}



do_rm_work() {
}

