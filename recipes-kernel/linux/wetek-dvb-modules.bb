SSUMMARY = "DVB driver for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PR = "r2"

RDEPENDS_${PN} = "firmware-dvb"

SRC_URI = "file://wetekdvb.ko"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module



do_compile() {
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -d ${D}/${sysconfdir}/modules-load.d
    echo "wetekdvb" > ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
    install -m 0755 ${WORKDIR}/wetekdvb.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"
