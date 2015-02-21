SUMMARY = "Enigma2 /proc/stb driver"
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPLv2+"

PR = "r0"
inherit module

PV = "experimental-git${SRCPV}"
SRC_URI = "git://github.com/wetekenigma/e2-procfs.git;user=git;protocol=ssh"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://${S}/LICENSE.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"



