DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

DEPENDS += "xz-native bc-native"

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"

SRC_URI = "git://github.com/codesnake/linux-amlogic.git;branch=master \
           file://defconfig \
"

S = "${WORKDIR}/git"

LINUX_VERSION_EXTENSION ?= "amlogic"

PR = "r0"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "wetekplay"

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