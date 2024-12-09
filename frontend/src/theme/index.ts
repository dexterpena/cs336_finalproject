import { extendTheme } from "@chakra-ui/react";
import components from "./components";
import colors from "./colors";
import shadows from "./shadows";

export default extendTheme({
    components,
    colors,
    config: {
        initialColorMode: "dark",
    },
    fonts: {
        heading: "var(--font-inter)",
        banner: "var(--font-inter)",
        body: "var(--font-inter)",
    },
    shadows,
    styles: {
        global: {
            body: {
                bg: "bg.100",
            },

            "::-webkit-scrollbar": {
                w: "3",
                h: "3",
            },

            "::-webkit-scrollbar-track": {
                bg: "bg.80",
            },

            "::-webkit-scrollbar-thumb": {
                bg: "accent.10",
            },

            "::-webkit-scrollbar-thumb:hover": {
                bg: "accent.10",
            },
        },
    },
    textStyles: {
        paragraph: {
            sm: {
                fontSize: "14px",
                fontWeight: "normal",
            },
            md: {
                fontSize: "16px",
                fontWeight: "normal",
            },
            lg: {
                fontSize: "18px",
                fontWeight: "normal",
            },
        },
        label: {
            sm: {
                fontSize: "16px",
                fontWeight: "medium",
            },
            md: {
                fontSize: "20px",
                fontWeight: "medium",
            },
            lg: {
                fontSize: "24px",
                fontWeight: "medium",
            },
        },
        heading: {
            sm: {
                fontSize: "24px",
            },
            md: {
                fontSize: "36px",
            },
            lg: {
                fontSize: "48px",
            },
            xl: {
                fontSize: "64px",
            },
        },
        main: {
            sm: {
                fontFamily: "heading",
                fontWeight: "bold",
                fontSize: "32px",
            },
            md: {
                fontFamily: "heading",
                fontWeight: "bold",
                fontSize: "48px",
            },
            lg: {
                fontFamily: "heading",
                fontWeight: "bold",
                fontSize: "64px",
            },
        },
        banner: {
            xxxs: {
                fontFamily: "banner",
                fontSize: "12px",
            },
            xxs: {
                fontFamily: "banner",
                fontSize: "16px",
            },
            xs: {
                fontFamily: "banner",
                fontSize: "24px",
            },
            sm: {
                fontFamily: "banner",
                fontSize: "32px",
            },
            md: {
                fontFamily: "banner",
                fontSize: "48px",
            },
            lg: {
                fontFamily: "banner",
                fontSize: "64px",
            },
        },

    },
});