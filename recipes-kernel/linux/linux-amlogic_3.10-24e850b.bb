require recipes-kernel/linux/linux-amlogic.inc

LINUX_VERSION ?= "3.10-24e850b"
SRCREV="24e850b84f2cd21db4dacb29b2b02a40933a14fb"

SRC_URI += "file://aml_fe_set_property.patch \
            file://dvbwetek.patch \
            file://no_dev_console.patch \
            file://out-of-tree-build.patch \
            file://meson6_g18.dtd "

do_configure_prepend () {
    cp -f ${WORKDIR}/meson6_g18.dtd ${S}/arch/arm/boot/dts/amlogic/
}