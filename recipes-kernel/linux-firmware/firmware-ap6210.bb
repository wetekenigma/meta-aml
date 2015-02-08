SUMMARY = "Firmware files for use with Linux kernel"
SECTION = "kernel"

LICENSE = "CLOSED"


PE = "1"


SRC_URI = "file://ap6210-nvram.txt \
           file://fw_bcm40181a2.bin \
"

S = "${WORKDIR}/"

inherit allarch update-alternatives

do_compile() {
    :
}

FILES_${PN} = "/lib"

do_install() {
    install -d  ${D}/lib/firmware/brcm
    cp -r ap6210-nvram.txt ${D}/lib/firmware/brcm
    cp -r fw_bcm40181a2.bin ${D}/lib/firmware/brcm
}