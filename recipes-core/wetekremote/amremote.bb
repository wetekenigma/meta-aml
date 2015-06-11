SUMMARY = "AML remote setup"
LICENSE = "GPLv2"
SECTION = "base"
PRIORITY = "required"

PR = "r0"

require conf/license/license-gplv2.inc


SRC_URI = "file://amremote.tar.xz  file://wetek.conf"

S = "${WORKDIR}/amremote-aa0a9e8"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/amremote
    install -m 0755 ${S}/remotecfg ${D}${bindir}/
    install -m 0644 ${WORKDIR}/wetek.conf ${D}${sysconfdir}/amremote/
}

FILES_${PN} = "${bindir} ${sysconfdir}"

do_rm_work() {
}
