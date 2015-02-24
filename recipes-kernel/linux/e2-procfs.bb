SUMMARY = "Enigma2 /proc/stb driver"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PR = "r2"
inherit module

SRC_URI = "file://e2-procfs.ko"


do_compile() {
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${WORKDIR}/e2-procfs.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
}


