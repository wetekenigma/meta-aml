DESCRIPTION = "Various DVB-S/T/ATSC devices firmware for wetek and USB devices"
HOMEPAGE = "http://bitbucket.org/amlinux"
LICENSE = "CLOSED"
PACKAGE_ARCH = "all"

PR = "r0"

SRC_URI = "file://*"

S = "${WORKDIR}"

do_compile() {
    :
}

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 644 ${S}/*.fw ${D}${base_libdir}/firmware/
}
PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"



