import { DataTable } from "@/components/DataTable";
import Head from "next/head";

const HomePage = () => {
  return (
    <>
      <Head>
        <title>CS336 Project</title>
        <meta property="og:title" content="CS336 Project" />
        <meta property="og:type" content="website" />
      </Head>

      <DataTable />

    </>
  );
};

export default HomePage;
