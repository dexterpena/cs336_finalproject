import { ChakraProvider } from "@chakra-ui/react";
import theme from "../theme";
import { Inter } from "next/font/google";

const inter = Inter({ subsets: ["latin"] });

const App = ({ Component, pageProps }) => {
  return (
    <ChakraProvider theme={theme}>
      <>
        <style jsx global>{`
          :root {
            --font-inter: ${inter.style.fontFamily};
          }
        `}</style>
        <Component {...pageProps} />
      </>
    </ChakraProvider>
  );
};

export default App;
