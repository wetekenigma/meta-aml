SUMMARY = "Firmware files for use with Linux kernel"
SECTION = "kernel"
# Notes:
# This is kind of a mess. Each bit of firmware has their own license. Some free
# some not. Leaving this as Proprietary for now, but this recipe should be probably
# be rethought out a bit more around how it deals with licenses.

LICENSE = "Proprietary"


PE = "1"

SRC_URI = "file://ap6210-nvram.txt \
           file://fw_bcm40181a2.bin \
"

S = "${WORKDIR}/"

inherit allarch update-alternatives

do_compile() {
	:
}

do_install() {
	install -d  ${D}/lib/firmware/brcm
	cp -r ap6210-nvram.txt ${D}/lib/firmware/brcm
  cp -r fw_bcm40181a2.bin ${D}/lib/firmware/brcm
}