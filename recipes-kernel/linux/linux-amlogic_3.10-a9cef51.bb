DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

DEPENDS += " xz-native bc-native "

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"
LINUX_VERSION ?= "3.10-a9cef51"
LINUX_VERSION_EXTENSION ?= "amlogic"

PR = "r9"
PV = "${LINUX_VERSION}"

COMPATIBLE_MACHINE = "wetekplay"

# for kernel
SRC_URI[md5sum] = "234171968c3fcea3b51e3972403fb713"
SRC_URI[sha256sum] = "e5a770065b213302ba8eab3b1a604a730c44dc403a8606af811932e7c846aa98"

SRC_URI = " \
    http://sources.openelec.tv/devel/linux-amlogic-3.10-a9cef51.tar.xz \
    file://defconfig \
    file://amports_tsdemux.patch \
    file://ac3_eac3.patch \
    file://000-svn_rev.patch \
    file://020-wetek_dvb_code.patch \
    file://040-no_dev_console.patch \
    file://050-turn_power_led_into_standby_mode_after_poweroff.patch \
    file://060-xattr.patch \
    file://070-amports_ignore_fec_control.patch \
    file://070-remove-amvideocap-spam.patch \
    file://100-fix-23hzpixelclock.patch \
    file://201-update_nand_partition_layout.patch \
    file://202-disable_ethernet_mac_update_from_nand.patch \
    file://linux-010_tivo_slide_pro.patch \
    file://wetekplay.dtd \
    file://dsp_data.TXT \
    file://osd_dev.c \
	file://display_vout.patch \
"


do_configure_prepend () {
    cp -f ${WORKDIR}/wetekplay.dtd ${WORKDIR}/linux-amlogic-3.10-a9cef51/arch/arm/boot/dts/amlogic/
    cp -f ${WORKDIR}/osd_dev.c ${WORKDIR}/linux-amlogic-3.10-a9cef51/drivers/amlogic/display/osd/
    cp -f ${WORKDIR}/dsp_data.TXT ${WORKDIR}/linux-amlogic-3.10-a9cef51/drivers/amlogic/audiodsp/dsp_data.z
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
        rm ${D}/usr/src/kernel/mkbootimg
        # This is x86 elf code...
        rm ${D}/usr/src/kernel/arch/arm/boot/mkimage

        # remove *.z from installation path those are object files from amlogic for binary modules
        find ${D}/usr/src/kernel -type f -name "*.z" | xargs rm -f
}



do_rm_work() {
}

